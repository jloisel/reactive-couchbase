package com.github.jloisel.reactive.conversion.jackson;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jloisel.reactive.conversion.api.ConversionFactory;
import com.github.jloisel.reactive.conversion.api.Deserializer;
import com.github.jloisel.reactive.conversion.api.Serializer;
import com.github.jloisel.reactive.entity.api.ReactiveEntity;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE, onConstructor = @__(@Autowired))
final class JacksonConversionFactory implements ConversionFactory {
  @NonNull
  ObjectMapper mapper;

  @Override
  public <T extends ReactiveEntity> Serializer<T> serializer() {
    return new JacksonSerializer<T>(mapper, new TypeReference<BeanWrapper<T>>() {
    });
  }

  @Override
  public <T extends ReactiveEntity> Deserializer<T> deserializer() {
    return new JacksonDeserializer<T>(mapper, new TypeReference<BeanWrapper<T>>() {
    });
  }

}
