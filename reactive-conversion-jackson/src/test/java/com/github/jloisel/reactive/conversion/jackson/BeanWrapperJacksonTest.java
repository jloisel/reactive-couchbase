package com.github.jloisel.reactive.conversion.jackson;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import lombok.Value;
import lombok.experimental.Wither;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jloisel.reactive.conversion.jackson.BeanWrapper;
import com.github.jloisel.reactive.entity.api.ReactiveEntity;

/**
 * Tests {@link BeanWrapper}.
 * 
 * @author jerome
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=JacksonConfig.class)
public class BeanWrapperJacksonTest {

  @Autowired
  private ObjectMapper mapper;

  private final TypeReference<BeanWrapper<Person>> reference =
      new TypeReference<BeanWrapper<Person>>() {};

  @Test
  public void shouldJacksonSerializeCorrectly() throws IOException {
    final Person person = new Person("id", "John", "Smith");
    final BeanWrapper<Person> instance = new BeanWrapper<Person>(person);
    final String json = mapper.writerFor(reference).writeValueAsString(instance);
    final BeanWrapper<Person> fromJson = mapper.reader(reference).readValue(json);
    assertEquals(instance, fromJson);
  }

  @Value
  public static class Person implements ReactiveEntity {
    @Wither
    String id;
    String firstname;
    String lastname;

    @JsonCreator
    public Person(@JsonProperty("id") final String id,
        @JsonProperty("firstname") final String firstname,
        @JsonProperty("lastname") final String lastname) {
      super();
      this.id = checkNotNull(id);
      this.firstname = checkNotNull(firstname);
      this.lastname = checkNotNull(lastname);
    }
  }
}
