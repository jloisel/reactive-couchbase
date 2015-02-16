package com.github.jloisel.reactive.conversion.jackson;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import com.github.jloisel.reactive.conversion.jackson.BeanWrapper;
import com.github.jloisel.reactive.entity.api.ReactiveEntity;
import com.google.common.testing.NullPointerTester;

public class BeanWrapperTest {

  @Test
  public void shouldPassNPETester() {
    new NullPointerTester().testConstructors(BeanWrapper.class, PACKAGE);
  }

  @Test
  public void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(BeanWrapper.class).verify();
  }

  @Test
  public void shouldCreate() {
    final BeanWrapper<ReactiveEntity> beanWrapper =
        new BeanWrapper<ReactiveEntity>(mock(ReactiveEntity.class));
    assertNotNull(beanWrapper);
  }
}
