package com.github.jloisel.reactive.repository.api;

import rx.Observable;

import com.couchbase.client.java.view.ViewQuery;
import com.github.jloisel.reactive.entity.api.ReactiveEntity;

/**
 * An {@link Observable} repository.
 * 
 * @author jerome
 *
 * @param <T> type of the persisted entity
 */
public interface AsyncRepository<T extends ReactiveEntity> {

  /**
   * Returns whether an entity with the given id exists.
   * 
   * @param id must not be {@literal null}.
   * @return true if an entity with the given id exists, {@literal false} otherwise
   * @throws IllegalArgumentException if {@code id} is {@literal null}
   */
  Observable<Boolean> exists(String id);

  /**
   * Finds an element by its id.
   * 
   * @param id identifier
   * @return an {@link Observable} emitting the element if found
   */
  Observable<T> findOne(String id);

  /**
   * Saves an entity into the repository.
   * 
   * @param entity entity to save
   * @return an {@link Observable} emitting the saved element
   */
  Observable<T> save(T entity);

  /**
   * Saves an element into the repository.
   * 
   * @param entities entities to save
   * @return an {@link Observable} emitting the saved element
   */
  Observable<T> save(Iterable<T> entities);

  /**
   * Finds all the elements.
   * 
   * @return an {@link Observable} which emits the elements found
   */
  Observable<T> findAll();

  /**
   * Finds all the elements with ids within the given list of ids.
   * 
   * @param ids list of ids to retrieve
   * @return an {@link Observable} which emits the elements found
   */
  Observable<T> findAll(Iterable<String> ids);

  /**
   * Query a database view.
   * 
   * @param query query to execute
   * @return an {@link Observable} which emits the results
   */
  Observable<T> queryView(ViewQuery query);

  /**
   * Delete a document by its id.
   * 
   * @param id id of the document to delete
   * @return an {@link Observable} emitting the id of the deleted document
   */
  Observable<String> delete(String id);

  /**
   * Delete a document by its id.
   * 
   * @param id id of the document to delete
   * @return an {@link Observable} emitting the id of the deleted document
   */
  Observable<String> delete(T entity);

  /**
   * @return an {@link Observable} emitting all the ids of the deleted documents
   */
  Observable<String> deleteAll();
}
