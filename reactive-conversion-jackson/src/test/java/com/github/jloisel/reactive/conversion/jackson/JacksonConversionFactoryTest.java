package com.github.jloisel.reactive.conversion.jackson;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jloisel.reactive.conversion.api.ConversionFactory;
import com.github.jloisel.reactive.conversion.api.Deserializer;
import com.github.jloisel.reactive.conversion.api.Serializer;
import com.github.jloisel.reactive.conversion.jackson.JacksonConversionFactory;
import com.github.jloisel.reactive.conversion.jackson.JacksonDeserializer;
import com.github.jloisel.reactive.conversion.jackson.JacksonSerializer;
import com.github.jloisel.reactive.entity.api.ReactiveEntity;
import com.google.common.testing.NullPointerTester;
import com.google.common.testing.NullPointerTester.Visibility;

@RunWith(MockitoJUnitRunner.class)
public class JacksonConversionFactoryTest {

  @Mock
  private ObjectMapper mapper;

  private ConversionFactory factory;

  @Before
  public void before() {
    factory = new JacksonConversionFactory(mapper);
  }

  @Test
  public void shouldReturnDeSerializer() {
    final Deserializer<ReactiveEntity> deserializer = factory.deserializer();
    assertEquals(JacksonDeserializer.class, deserializer.getClass());
  }

  @Test
  public void shouldReturnSerializer() {
    final Serializer<ReactiveEntity> serializer = factory.serializer();
    assertEquals(JacksonSerializer.class, serializer.getClass());
  }

  @Test
  public void shouldPassNPETester() {
    new NullPointerTester().testConstructors(JacksonConversionFactory.class, Visibility.PACKAGE);
  }
}
