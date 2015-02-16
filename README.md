# reactive-couchbase

Asynchronous couchbase repository using RxJava and Couchbase Java SDK 2.

Pre-requisites:
Maven
Java JDK 8
Lombok (see http://projectlombok.org/)

Observable Couchbase repository made easy:
```java
final AsyncRepositoryFactory factory = ...;
final AsyncRepository<Person> repository = factory.create(Person.class);

// findAll() returns Observable<Person>
repository.findAll().forEach(person -> System.out.println(person.getFirstname()));
```

Person entity:
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
