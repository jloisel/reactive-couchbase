package com.github.jloisel.reactive.repository.couchbase;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;

import org.junit.Test;

import com.github.jloisel.reactive.repository.couchbase.CBSyncRepositoryFactory;
import com.google.common.testing.NullPointerTester;

public class CBSyncRepositoryFactoryTest {

  @Test
  public void shouldPassNPETester() {
    new NullPointerTester().testConstructors(CBSyncRepositoryFactory.class, PACKAGE);
  }
}
