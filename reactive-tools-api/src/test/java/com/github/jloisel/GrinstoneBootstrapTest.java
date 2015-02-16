package com.github.jloisel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.jloisel.SpringBootstrap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringBootstrap.class)
public class GrinstoneBootstrapTest {

  @Autowired
  private TestService service;

  @Value("${property.key:default}")
  private String propertyValue;

  @Test
  public void shouldAutowire() {
    assertNotNull(service);
    assertEquals("default", propertyValue);
  }
}
