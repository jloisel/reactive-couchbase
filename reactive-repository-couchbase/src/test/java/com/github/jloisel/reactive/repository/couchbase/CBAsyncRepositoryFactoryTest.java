package com.github.jloisel.reactive.repository.couchbase;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;

import org.junit.Test;

import com.github.jloisel.reactive.repository.couchbase.CBAsyncRepositoryFactory;
import com.google.common.testing.NullPointerTester;

public class CBAsyncRepositoryFactoryTest {

  @Test
  public void shouldPassNPETester() {
    new NullPointerTester().testConstructors(CBAsyncRepositoryFactory.class, PACKAGE);
  }
}
