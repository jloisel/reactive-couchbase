package com.github.jloisel.reactive.repository.api;

import com.github.jloisel.reactive.entity.api.ReactiveEntity;

/**
 * Creates {@link AsyncRepository} instances.
 * 
 * @author jerome
 *
 */
public interface AsyncRepositoryFactory {

  /**
   * Creates a new instance of a {@link AsyncRepository} of a given type.
   * 
   * @param ofType type of the objects to store.
   * @return
   */
  <T extends ReactiveEntity> AsyncRepository<T> create(Class<T> ofType);
}
