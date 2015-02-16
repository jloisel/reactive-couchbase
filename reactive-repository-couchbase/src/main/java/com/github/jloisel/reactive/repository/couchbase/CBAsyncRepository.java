package com.github.jloisel.reactive.repository.couchbase;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import rx.Observable;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.view.AsyncViewResult;
import com.couchbase.client.java.view.ViewQuery;
import com.github.jloisel.reactive.conversion.api.Deserializer;
import com.github.jloisel.reactive.conversion.api.Serializer;
import com.github.jloisel.reactive.entity.api.ReactiveEntity;
import com.github.jloisel.reactive.query.api.ViewQueryService;
import com.github.jloisel.reactive.repository.api.AsyncRepository;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class CBAsyncRepository<T extends ReactiveEntity> implements AsyncRepository<T> {
  @NonNull
  Serializer<T> serializer;
  @NonNull
  Deserializer<T> deserializer;
  @NonNull
  AsyncBucket bucket;
  @NonNull
  ViewQueryService queries;
  @NonNull
  Class<T> clazz;
  @NonNull
  Long timeout;
  @NonNull
  TimeUnit unit;
    
  @Override
  public Observable<T> save(final T entity) {
    return save(ImmutableList.of(entity));
  }

  @Override
  public Observable<T> save(final Iterable<T> entities) {
    return Observable.from(entities)
        .map(serializer)
        .flatMap(bucket::upsert)
        .map(deserializer)
        .timeout(timeout, unit);
  }

  @Override
  public Observable<Boolean> exists(final String id) {
    return findOne(id).isEmpty().map(isEmpty -> !isEmpty);
  }

  @Override
  public Observable<T> findOne(final String id) {
    return findAll(ImmutableSet.of(id));
  }

  @Override
  public Observable<String> deleteAll() {
    return findAll().flatMap(this::delete);
  }

  @Override
  public Observable<T> findAll() {
    final ViewQuery query = queries.newQuery(clazz.getSimpleName().toLowerCase(), "all");
    query.reduce(false);
    return queryView(query);
  }

  @Override
  public Observable<T> findAll(final Iterable<String> ids) {
    return Observable.from(ids)
      .flatMap(id -> bucket.get(id, RawJsonDocument.class))
      .map(deserializer)
      .timeout(timeout, unit);
  }

  @Override
  public Observable<T> queryView(final ViewQuery query) {
    return Observable.just(query)
      .flatMap(bucket::query)
      .flatMap(AsyncViewResult::rows)
      .flatMap(row -> row.document(RawJsonDocument.class))
      .map(deserializer)
      .timeout(timeout, unit);
  }

  @Override
  public Observable<String> delete(final T entity) {
    return Observable.just(entity)
        .map(serializer)
        .flatMap(bucket::remove)
        .map(RawJsonDocument::id)
        .timeout(timeout, unit);
  }

  @Override
  public Observable<String> delete(final String id) {
    return Observable.just(id)
      .flatMap(identifier -> bucket.remove(identifier, RawJsonDocument.class))
      .map(RawJsonDocument::id)
      .timeout(timeout, unit);
  }

}
