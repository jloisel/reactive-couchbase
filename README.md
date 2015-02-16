# reactive-couchbase

Asynchronous couchbase repository using RxJava and Couchbase Java SDK 2.

Pre-requisites:
- Maven
- Java JDK 8
- Lombok (see http://projectlombok.org/)

Observable Couchbase repository made easy:
```java
final AsyncRepositoryFactory factory = ...;
final AsyncRepository<Person> repository = factory.create(Person.class);

// findAll() returns Observable<Person>
repository.findAll().forEach(person -> System.out.println(person.getFirstname()));
```

As well as sequential repository:
```java
final SyncRepositoryFactory factory = ...;
final SyncRepository<Person> repository = factory.create(Person.class);

final List<Person> persons = repository.findAll();
```

Person entity using Jackson Json processor:
```java
@Value
@Builder
public class Person implements ReactiveEntity {
  @Wither
  String id;
  String firstname;
  String lastname;

  @JsonCreator
  Person(
      @JsonProperty("id") final String id,
      @JsonProperty("firstname") final String firstname,
      @JsonProperty("lastname") final String lastname) {
    super();
    this.id = checkNotNull(id);
    this.firstname = checkNotNull(firstname);
    this.lastname = checkNotNull(lastname);
  }
}
```

Spring Configuration:
```java
@Configuration
public class CouchbaseConfig {

  @Bean
  @Autowired
  ASyncRepository<Person> asyncPersonRepository(final ASyncRepositoryFactory factory) {
    return factory.create(Person.class);
  }
  
  @Bean
  @Autowired
  SyncRepository<Person> syncPersonRepository(final SyncRepositoryFactory factory) {
    return factory.create(Person.class);
  }
}
```
