package com.github.jloisel.reactive.conversion.api;

import com.github.jloisel.reactive.entity.api.ReactiveEntity;

/**
 * Provides {@link Serializer} and {@link Deserializer} instances.
 * 
 * @author jerome
 *
 */
public interface ConversionFactory {

  /**
   * Provides a {@link Serializer} instance.
   * 
   * @param clazz type of bean to serialize
   * @return serializer instance
   */
  <T extends ReactiveEntity> Serializer<T> serializer();

  /**
   * Provides a {@link Deserializer} instance.
   * 
   * @param clazz type of bean to serialize
   * @return serializer instance
   */
  <T extends ReactiveEntity> Deserializer<T> deserializer();
}
