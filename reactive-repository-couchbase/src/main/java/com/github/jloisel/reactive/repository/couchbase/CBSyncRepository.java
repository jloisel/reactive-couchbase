package com.github.jloisel.reactive.repository.couchbase;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import rx.Observable;
import rx.observables.BlockingObservable;

import com.couchbase.client.java.view.ViewQuery;
import com.github.jloisel.reactive.entity.api.ReactiveEntity;
import com.github.jloisel.reactive.repository.api.AsyncRepository;
import com.github.jloisel.reactive.repository.api.SyncRepository;

@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class CBSyncRepository<T extends ReactiveEntity> implements SyncRepository<T> {
  @NonNull
  AsyncRepository<T> async;

  @Override
  public T save(final T entity) {
    return blocking(async.save(entity)).single();
  }

  @Override
  public Iterable<T> save(final Iterable<T> entities) {
    return blocking(async.save(entities).toList()).single();
  }

  @Override
  public boolean exists(final T entity) {
    return exists(entity.getId());
  }

  @Override
  public boolean exists(final String id) {
    return blocking(async.exists(id)).firstOrDefault(false);
  }

  @Override
  public T findOne(final String id) {
    return blocking(async.findOne(id)).firstOrDefault(null);
  }

  @Override
  public Iterable<T> findAll() {
    return blocking(async.findAll().toList()).single();
  }

  @Override
  public Iterable<T> findAll(final Iterable<String> ids) {
    return blocking(async.findAll(ids).toList()).single();
  }

  @Override
  public String delete(final T entity) {
    return blocking(async.delete(entity)).firstOrDefault(null);
  }

  @Override
  public String delete(final String id) {
    return blocking(async.delete(id)).firstOrDefault(null);
  }

  @Override
  public List<String> deleteAll() {
    return blocking(async.deleteAll().toList()).single();
  }

  @Override
  public List<T> queryView(final ViewQuery query) {
    return blocking(async.queryView(query).toList()).single();
  }

  private <S> BlockingObservable<S> blocking(final Observable<S> observable) {
    return observable.toBlocking();
  }

  @Override
  public AsyncRepository<T> async() {
    return async;
  }
}
