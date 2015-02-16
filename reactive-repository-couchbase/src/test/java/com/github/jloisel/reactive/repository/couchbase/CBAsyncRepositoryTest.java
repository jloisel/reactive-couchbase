package com.github.jloisel.reactive.repository.couchbase;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;

import org.junit.Test;

import com.github.jloisel.reactive.repository.couchbase.CBAsyncRepository;
import com.google.common.testing.NullPointerTester;

public class CBAsyncRepositoryTest {

  @Test
  public void shouldPassNPETester() {
    new NullPointerTester().testConstructors(CBAsyncRepository.class, PACKAGE);
  }
}
