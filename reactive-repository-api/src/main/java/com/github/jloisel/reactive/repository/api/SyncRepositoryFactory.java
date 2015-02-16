package com.github.jloisel.reactive.repository.api;

import com.github.jloisel.reactive.entity.api.ReactiveEntity;

/**
 * Creates {@link SyncRepository} instances.
 * 
 * @author jerome
 *
 */
public interface SyncRepositoryFactory {

  /**
   * Creates a new instance of a {@link SyncRepository} of a given type.
   * 
   * @param ofType type of the objects to store.
   * @return
   */
  <T extends ReactiveEntity> SyncRepository<T> create(Class<T> ofType);
}
