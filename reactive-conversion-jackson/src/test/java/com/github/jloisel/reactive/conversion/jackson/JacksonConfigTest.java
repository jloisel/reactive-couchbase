package com.github.jloisel.reactive.conversion.jackson;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JacksonConfig.class)
public class JacksonConfigTest {

  @Autowired
  private ObjectMapper mapper;

  @Test
  public void shouldAutowire() {
    assertNotNull(mapper);
  }
}
