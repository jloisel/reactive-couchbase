package com.github.jloisel.reactive.repository.couchbase.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.bucket.BucketManager;
import com.couchbase.client.java.error.DocumentDoesNotExistException;
import com.couchbase.client.java.view.DefaultView;
import com.couchbase.client.java.view.DesignDocument;
import com.couchbase.client.java.view.Stale;
import com.couchbase.client.java.view.View;
import com.couchbase.client.java.view.ViewQuery;
import com.github.jloisel.SpringBootstrap;
import com.github.jloisel.reactive.repository.api.SyncRepository;
import com.github.jloisel.reactive.repository.api.SyncRepositoryFactory;
import com.google.common.collect.ImmutableList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringBootstrap.class)
public class SyncRepositoryTest {

  private final Person person = Person.builder().id("jsmith").firstname("John").lastname("Smith")
      .build();

  @Autowired
  private Bucket bucket;
  @Autowired
  private SyncRepositoryFactory factory;
  @Autowired
  private Cluster cluster;

  private SyncRepository<Person> repository;

  @Before
  public void before() {
    repository = factory.create(Person.class);
  }

  @Test
  public void shouldAutowire() {
    assertNotNull(bucket);
    assertNotNull(factory);
  }

  @Test
  public void shouldSubscribe() {
    repository.save(person);
    repository.async().findAll().subscribe(repository::exists);
    assertTrue(repository.exists(person));
  }

  @Test
  public void shouldNotDeleteAll() throws IOException {
    final BucketManager bucketManager = bucket.bucketManager();
    final View view =
        DefaultView.create("all", IOUtils.toString(getClass().getResourceAsStream("/all.js")));
    final DesignDocument document = DesignDocument.create("person", ImmutableList.of(view));
    try {
      bucketManager.upsertDesignDocument(document);

      repository.deleteAll();
      final Iterable<Person> found = repository.findAll();
      assertEquals(ImmutableList.of(), found);
    } finally {
      bucketManager.removeDesignDocument(document.name());
    }
  }

  @Test
  public void shouldDeleteAll() throws IOException {
    final BucketManager bucketManager = bucket.bucketManager();
    final View view =
        DefaultView.create("all", IOUtils.toString(getClass().getResourceAsStream("/all.js")));
    final DesignDocument document = DesignDocument.create("person", ImmutableList.of(view));
    try {
      bucketManager.upsertDesignDocument(document);

      repository.save(person);
      repository.deleteAll();
      final Iterable<Person> found = repository.findAll();
      assertEquals(ImmutableList.of(), found);
    } finally {
      bucketManager.removeDesignDocument(document.name());
    }
  }

  @Test
  public void shouldFindAll() throws IOException {
    final BucketManager bucketManager = bucket.bucketManager();
    final View view =
        DefaultView.create("all", IOUtils.toString(getClass().getResourceAsStream("/all.js")));
    final DesignDocument document = DesignDocument.create("person", ImmutableList.of(view));
    try {
      bucketManager.upsertDesignDocument(document);

      repository.save(person);
      final Iterable<Person> found = repository.findAll();
      assertEquals(ImmutableList.of(person), found);
    } finally {
      bucketManager.removeDesignDocument(document.name());
    }
  }

  @Test
  public void shouldQueryView() throws IOException {
    final BucketManager bucketManager = bucket.bucketManager();
    final View view =
        DefaultView.create("byFirstname",
            IOUtils.toString(getClass().getResourceAsStream("/byfirstname.js")));
    final DesignDocument document = DesignDocument.create("person", ImmutableList.of(view));
    try {
      bucketManager.upsertDesignDocument(document);

      repository.save(person);
      final ViewQuery query = ViewQuery.from(document.name(), view.name());
      query.stale(Stale.FALSE);
      query.debug();
      query.key(person.getFirstname());

      final Iterable<Person> found = repository.queryView(query);
      assertEquals(ImmutableList.of(person), found);
    } finally {
      bucketManager.removeDesignDocument(document.name());
    }
  }

  @Test
  public void shouldNotSaveAll() {
    final Iterable<Person> saved = repository.save(ImmutableList.of());
    assertEquals(ImmutableList.of(), saved);
    assertNull(repository.findOne(person.getId()));
  }

  @Test
  public void shouldSaveAll() {
    final Iterable<Person> saved = repository.save(ImmutableList.of(person));
    assertEquals(ImmutableList.of(person), saved);
    assertEquals(person, repository.findOne(person.getId()));
  }

  @Test
  public void shouldNotExistsById() {
    assertFalse(repository.exists(person.getId()));
  }

  @Test
  public void shouldNotExists() {
    assertFalse(repository.exists(person));
  }

  @Test
  public void shouldExistsById() {
    repository.save(person);
    assertTrue(repository.exists(person.getId()));
  }

  @Test
  public void shouldExists() {
    repository.save(person);
    assertTrue(repository.exists(person));
  }

  @Test
  public void shouldSave() {
    final Person saved = repository.save(person);
    assertEquals(person, saved);
  }

  @Test
  public void shouldNotFindOne() {
    assertNull(repository.findOne(person.getId()));
  }

  @Test
  public void shouldFindOne() {
    repository.save(person);
    final Person findOne = repository.findOne(person.getId());
    assertEquals(person, findOne);
  }

  @Test
  public void shouldNotFindAllWithIds() {
    repository.save(person);
    final Iterable<Person> findAll = repository.findAll(ImmutableList.of());
    assertEquals(ImmutableList.of(), findAll);
  }

  @Test
  public void shouldFindAllWithIds() {
    repository.save(person);
    final Iterable<Person> findAll = repository.findAll(ImmutableList.of(person.getId()));
    assertEquals(ImmutableList.of(person), findAll);
  }

  @Test(expected = DocumentDoesNotExistException.class)
  public void shouldNotDelete() {
    repository.delete(person);
  }

  @Test(expected = DocumentDoesNotExistException.class)
  public void shouldNotDeleteById() {
    assertEquals(person.getId(), repository.delete(person.getId()));
  }

  @Test
  public void shouldDeleteById() {
    repository.save(person);
    assertEquals(person.getId(), repository.delete(person.getId()));
    assertFalse(repository.exists(person.getId()));
  }

  @Test
  public void shouldDelete() {
    repository.save(person);
    final Person findOne = repository.findOne(person.getId());
    assertEquals(person, findOne);
    assertEquals(person.getId(), repository.delete(person));
    assertNull(repository.findOne(person.getId()));
  }

  @After
  public void after() {
    if (repository.exists(person)) {
      repository.delete(person);
    }
  }
}
