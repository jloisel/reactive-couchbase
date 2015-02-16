package com.github.jloisel.reactive.conversion.jackson;

import static com.google.common.base.Preconditions.checkNotNull;
import lombok.Value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.github.jloisel.reactive.entity.api.ReactiveEntity;

@Value
class BeanWrapper<T extends ReactiveEntity> {
  @JsonTypeInfo(use = Id.CLASS, include = As.PROPERTY, property = "_class")
  T entity;

  @JsonCreator
  BeanWrapper(@JsonProperty("entity") final T entity) {
    super();
    this.entity = checkNotNull(entity);
  }
}
