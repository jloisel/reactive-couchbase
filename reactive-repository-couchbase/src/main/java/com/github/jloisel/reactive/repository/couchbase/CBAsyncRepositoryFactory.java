package com.github.jloisel.reactive.repository.couchbase;

import static java.util.concurrent.TimeUnit.SECONDS;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.couchbase.client.java.AsyncBucket;
import com.github.jloisel.reactive.conversion.api.ConversionFactory;
import com.github.jloisel.reactive.conversion.api.Deserializer;
import com.github.jloisel.reactive.conversion.api.Serializer;
import com.github.jloisel.reactive.entity.api.ReactiveEntity;
import com.github.jloisel.reactive.query.api.ViewQueryService;
import com.github.jloisel.reactive.repository.api.AsyncRepository;
import com.github.jloisel.reactive.repository.api.AsyncRepositoryFactory;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE, onConstructor = @__(@Autowired))
final class CBAsyncRepositoryFactory implements AsyncRepositoryFactory {
  @NonNull
  ConversionFactory factory;
  @NonNull
  AsyncBucket bucket;
  @NonNull
  ViewQueryService queries;
  @NonNull
  Environment env;

  @Override
  public <T extends ReactiveEntity> AsyncRepository<T> create(final Class<T> ofType) {
    final Serializer<T> serializer = factory.serializer();
    final Deserializer<T> deserializer = factory.deserializer();
    final Long timeout = env.getProperty("couchbase.query.timeout", Long.class, 30L);
    final TimeUnit unit = env.getProperty("couchbase.query.timeout.unit", TimeUnit.class, SECONDS);
    return new CBAsyncRepository<T>(serializer, deserializer, bucket, queries, ofType, timeout, unit);
  }
}
