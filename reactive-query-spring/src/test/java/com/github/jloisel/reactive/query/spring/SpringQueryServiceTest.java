package com.github.jloisel.reactive.query.spring;

import static org.junit.Assert.assertNotNull;
import com.github.jloisel.reactive.query.spring.SpringQueryService;

import org.junit.Test;

import com.couchbase.client.java.view.Stale;
import com.couchbase.client.java.view.ViewQuery;
import com.google.common.testing.NullPointerTester;
import com.google.common.testing.NullPointerTester.Visibility;

public class SpringQueryServiceTest {

  @Test
  public void shouldPassNPETester() {
    new NullPointerTester().testConstructors(SpringQueryService.class, Visibility.PACKAGE);
  }

  @Test
  public void shouldReturnQuery() {
    final SpringQueryService service = new SpringQueryService(true, Stale.TRUE);
    final ViewQuery newQuery = service.newQuery("designDoc", "view");
    assertNotNull(newQuery);
  }
}
