package com.github.jloisel.reactive.conversion.jackson;

import static com.github.jloisel.reactive.conversion.jackson.JacksonDeserializer.END_OBJECT;
import static com.github.jloisel.reactive.conversion.jackson.JacksonDeserializer.START_OBJECT;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import com.couchbase.client.java.document.RawJsonDocument;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.jloisel.reactive.conversion.api.Serializer;
import com.github.jloisel.reactive.entity.api.ReactiveEntity;

@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class JacksonSerializer<T extends ReactiveEntity> implements Serializer<T> {
  @NonNull
  ObjectMapper mapper;
  @NonNull
  TypeReference<BeanWrapper<T>> reference;

  @Override
  public RawJsonDocument call(final T bean) {
    try {
      final ObjectWriter writer = mapper.writerFor(reference);
      final String json = writer.writeValueAsString(new BeanWrapper<T>(bean));
      final String unwrapped =
          json.substring(START_OBJECT.length(), json.length() - END_OBJECT.length());
      return RawJsonDocument.create(bean.getId(), unwrapped);
    } catch (final IOException e) {
      throw new JacksonConversionException("Error while serializing bean=" + bean, e);
    }
  }
}
