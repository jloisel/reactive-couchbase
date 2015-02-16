package com.github.jloisel.reactive.query.spring;

import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.couchbase.client.java.view.Stale;
import com.couchbase.client.java.view.ViewQuery;
import com.github.jloisel.reactive.query.api.ViewQueryService;

@Service
final class SpringQueryService implements ViewQueryService {

  boolean debug;
  Stale stale;

  @Autowired
  SpringQueryService(@Value("${couchbase.debug:true}") final boolean debug,
      @Value("${couchbase.stale:FALSE}") final Stale stale) {
    this.debug = checkNotNull(debug);
    this.stale = checkNotNull(stale);
  }

  @Override
  public ViewQuery newQuery(final String designDoc, final String view) {
    final ViewQuery query = ViewQuery.from(designDoc, view);
    query.debug(debug);
    query.stale(stale);
    return query;
  }
}
