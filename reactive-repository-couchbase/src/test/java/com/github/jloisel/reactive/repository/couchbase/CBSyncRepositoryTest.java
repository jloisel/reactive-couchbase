package com.github.jloisel.reactive.repository.couchbase;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.jloisel.reactive.entity.api.ReactiveEntity;
import com.github.jloisel.reactive.repository.api.AsyncRepository;
import com.github.jloisel.reactive.repository.api.SyncRepository;
import com.github.jloisel.reactive.repository.couchbase.CBSyncRepository;
import com.google.common.testing.NullPointerTester;

@RunWith(MockitoJUnitRunner.class)
public class CBSyncRepositoryTest {
  @Mock
  AsyncRepository<ReactiveEntity> async;

  private SyncRepository<ReactiveEntity> sync;

  @Before
  public void before() {
    sync = new CBSyncRepository<ReactiveEntity>(async);
  }

  @Test
  public void shouldPassNPETester() {
    new NullPointerTester().testConstructors(CBSyncRepository.class, PACKAGE);
  }

  @Test
  public void shouldReturnAsyncRepository() {
    assertSame(async, sync.async());
  }
}
