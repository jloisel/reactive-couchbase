package com.github.jloisel.reactive.repository.api;

import java.util.List;

import com.couchbase.client.java.error.DocumentDoesNotExistException;
import com.couchbase.client.java.view.ViewQuery;
import com.github.jloisel.reactive.entity.api.ReactiveEntity;

public interface SyncRepository<T extends ReactiveEntity> {

  /**
   * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
   * entity instance completely.
   * 
   * @param entity
   * @return the saved entity
   */
  T save(T entity);

  /**
   * Saves all given entities.
   * 
   * @param entities
   * @return the saved entities
   * @throws IllegalArgumentException in case the given entity is (@literal null}.
   */
  Iterable<T> save(Iterable<T> entities);

  /**
   * Retrieves an entity by its id.
   * 
   * @param id must not be {@literal null}.
   * @return the entity with the given id or {@literal null} if none found
   * @throws IllegalArgumentException if {@code id} is {@literal null}
   */
  T findOne(String id);

  /**
   * Returns whether an entity with the given id exists.
   * 
   * @param id must not be {@literal null}.
   * @return true if an entity with the given id exists, {@literal false} otherwise
   * @throws IllegalArgumentException if {@code id} is {@literal null}
   */
  boolean exists(String id);

  /**
   * Returns whether an entity exists.
   * 
   * @param entity must not be {@code null}
   * @return true if an entity exists, {@literal false} otherwise
   * @throws IllegalArgumentException if {@code id} is {@literal null}
   */
  boolean exists(T entity);

  /**
   * Returns all instances.
   * 
   * @return
   */
  Iterable<T> findAll();

  /**
   * Returns all instances of the type with the given IDs.
   * 
   * @param ids
   * @return
   */
  Iterable<T> findAll(Iterable<String> ids);

  /**
   * Deletes the entity with the given id.
   * 
   * @param id must not be {@literal null}.
   * @return the deleted entity id
   * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
   * @throws DocumentDoesNotExistException when there is no document with this id
   */
  String delete(String id);

  /**
   * Deletes a given entity.
   * 
   * @param entity
   * @return the deleted entity id
   * @throws IllegalArgumentException in case the given entity is (@literal null}.
   */
  String delete(T entity);

  /**
   * Deletes all the elements. Beware this operation is extremely destructive and memory consuming.
   * Should be used with extreme caution.
   * 
   * @return all the deleted document ids
   */
  List<String> deleteAll();

  /**
   * Query view.
   * 
   * @param query query to execute
   * @return entities found
   */
  List<T> queryView(ViewQuery query);

  /**
   * @return underlying asynchronous repository
   */
  AsyncRepository<T> async();
}
