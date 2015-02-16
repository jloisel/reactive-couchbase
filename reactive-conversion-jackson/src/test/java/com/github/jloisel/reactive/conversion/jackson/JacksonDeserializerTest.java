package com.github.jloisel.reactive.conversion.jackson;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.couchbase.client.java.document.RawJsonDocument;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.github.jloisel.reactive.conversion.api.Deserializer;
import com.github.jloisel.reactive.conversion.jackson.BeanWrapper;
import com.github.jloisel.reactive.conversion.jackson.JacksonConversionException;
import com.github.jloisel.reactive.conversion.jackson.JacksonDeserializer;
import com.github.jloisel.reactive.entity.api.ReactiveEntity;
import com.google.common.testing.NullPointerTester;
import com.google.common.testing.NullPointerTester.Visibility;

@RunWith(MockitoJUnitRunner.class)
public class JacksonDeserializerTest {

  @Mock
  private ObjectMapper mapper;
  @Mock
  private ObjectReader reader;
  @Mock
  private RawJsonDocument serializedEntity;
  @Mock
  private ReactiveEntity entity;
  private BeanWrapper<ReactiveEntity> document;

  private Deserializer<ReactiveEntity> deserializer;

  private final TypeReference<BeanWrapper<ReactiveEntity>> reference =
      new TypeReference<BeanWrapper<ReactiveEntity>>() {};

  @Before
  public void before() throws IOException {
    document = new BeanWrapper<ReactiveEntity>(entity);
    when(serializedEntity.content()).thenReturn("{json}");
    when(mapper.reader(reference)).thenReturn(reader);
    when(reader.with(any(InjectableValues.Std.class))).thenReturn(reader);
    when(reader.readValue(anyString())).thenReturn(document);
    deserializer = new JacksonDeserializer<ReactiveEntity>(mapper, reference);
  }

  @Test(expected = JacksonConversionException.class)
  public void shouldNotDeserialize() throws JsonProcessingException, IOException {
    doThrow(new IOException()).when(reader).readValue(anyString());
    final ReactiveEntity single = deserializer.call(serializedEntity);
    assertSame(single, entity);
  }

  @Test
  public void shouldDeserialize() throws IOException {
    final ReactiveEntity single = deserializer.call(serializedEntity);
    assertSame(single, entity);
    verify(mapper).reader(reference);
    verify(reader)
        .readValue(
        JacksonDeserializer.START_OBJECT + serializedEntity.content()
                + JacksonDeserializer.END_OBJECT);
  }

  @Test
  public void shouldPassNPETester() {
    new NullPointerTester().setDefault(TypeReference.class, new TypeReference<Object>() {})
        .testConstructors(JacksonDeserializer.class, Visibility.PACKAGE);
  }
}
