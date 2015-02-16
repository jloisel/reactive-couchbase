package com.github.jloisel.reactive.repository.couchbase.it;

import static com.google.common.base.Preconditions.checkNotNull;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jloisel.reactive.entity.api.ReactiveEntity;

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
