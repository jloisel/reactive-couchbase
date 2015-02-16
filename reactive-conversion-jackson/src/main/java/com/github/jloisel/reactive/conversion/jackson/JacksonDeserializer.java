package com.github.jloisel.reactive.conversion.jackson;

import static lombok.AccessLevel.PACKAGE;
import static org.apache.commons.lang3.StringUtils.join;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import com.couchbase.client.java.document.RawJsonDocument;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jloisel.reactive.conversion.api.Deserializer;
import com.github.jloisel.reactive.entity.api.ReactiveEntity;

@AllArgsConstructor(access = PACKAGE)
final class JacksonDeserializer<T extends ReactiveEntity> implements Deserializer<T> {
  static final String START_OBJECT = "{\"entity\":";
  static final String END_OBJECT = "}";

  @NonNull
  private final ObjectMapper mapper;
  @NonNull
  private final TypeReference<BeanWrapper<T>> reference;

  @Override
  public T call(final RawJsonDocument document) {
    try {
      final String wrapped = join(START_OBJECT, document.content(), END_OBJECT);
      final BeanWrapper<T> bean = mapper.reader(reference).readValue(wrapped);
      return bean.getEntity();
    } catch (final IOException e) {
      throw new JacksonConversionException("Error while deserializing document id=" + document.id(), e);
    }
  }

}
