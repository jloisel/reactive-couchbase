package com.github.jloisel.reactive.repository.couchbase;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.couchbase.client.java.AsyncBucket;
import com.github.jloisel.SpringBootstrap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringBootstrap.class)
public class SpringConfigTest {

  @Autowired
  private AsyncBucket bucket;

  @Test
  public void shouldAutowire() {
    assertNotNull(bucket);
  }
}
