package com.github.jloisel.reactive.repository.couchbase;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jloisel.reactive.entity.api.ReactiveEntity;
import com.github.jloisel.reactive.repository.api.AsyncRepository;
import com.github.jloisel.reactive.repository.api.AsyncRepositoryFactory;
import com.github.jloisel.reactive.repository.api.SyncRepository;
import com.github.jloisel.reactive.repository.api.SyncRepositoryFactory;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE, onConstructor = @__(@Autowired))
final class CBSyncRepositoryFactory implements SyncRepositoryFactory {
  @NonNull
  AsyncRepositoryFactory factory;

  @Override
  public <T extends ReactiveEntity> SyncRepository<T> create(final Class<T> ofType) {
    final AsyncRepository<T> async = factory.create(ofType);
    return new CBSyncRepository<T>(async);
  }
}
