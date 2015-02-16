package com.github.jloisel.reactive.conversion.jackson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.jloisel.reactive.conversion.api.Serializer;
import com.github.jloisel.reactive.conversion.jackson.BeanWrapper;
import com.github.jloisel.reactive.conversion.jackson.JacksonConversionException;
import com.github.jloisel.reactive.conversion.jackson.JacksonDeserializer;
import com.github.jloisel.reactive.conversion.jackson.JacksonSerializer;
import com.github.jloisel.reactive.entity.api.ReactiveEntity;
import com.google.common.testing.NullPointerTester;
import com.google.common.testing.NullPointerTester.Visibility;

@RunWith(MockitoJUnitRunner.class)
public class JacksonSerializerTest {

  @Mock
  private ObjectMapper mapper;
  @Mock
  private ObjectWriter writer;
  @Mock
  private ReactiveEntity entity;

  private Serializer<ReactiveEntity> serializer;

  private final TypeReference<BeanWrapper<ReactiveEntity>> reference =
      new TypeReference<BeanWrapper<ReactiveEntity>>() {};

  @Before
  public void before() throws IOException {
    when(entity.getId()).thenReturn("id");
    when(mapper.writerFor(reference)).thenReturn(writer);
    when(writer.writeValueAsString(any())).thenReturn(
        JacksonDeserializer.START_OBJECT + "{json}" + JacksonDeserializer.END_OBJECT);
    serializer = new JacksonSerializer<ReactiveEntity>(mapper, reference);
  }

  @Test(expected = JacksonConversionException.class)
  public void shouldNotSerialize() throws IOException {
    doThrow(JsonProcessingException.class).when(writer).writeValueAsString(any());
    final RawJsonDocument single = serializer.call(entity);
    assertNotNull(single);
  }

  @Test
  public void shouldSerialize() throws IOException {
    final RawJsonDocument serialized = serializer.call(entity);
    assertEquals(RawJsonDocument.class, serialized.getClass());
    verify(mapper).writerFor(reference);
    verify(writer).writeValueAsString(any());
  }

  @Test
  public void shouldPassNPETester() {
    new NullPointerTester().setDefault(TypeReference.class, reference).testConstructors(
        JacksonSerializer.class, Visibility.PACKAGE);
  }
}
