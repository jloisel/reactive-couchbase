package com.github.jloisel.reactive.query.api;

import com.couchbase.client.java.view.ViewQuery;

public interface ViewQueryService {

  ViewQuery newQuery(String designDoc, String view);
}
