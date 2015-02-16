package com.github.jloisel.reactive.repository.couchbase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.google.common.base.Splitter;

@Configuration
class CouchbaseConfig {
  private static final Splitter SPLITTER = Splitter.on(",").trimResults();


  @Bean(destroyMethod = "disconnect")
  @Autowired
  Cluster cluster(@Value("${database.host:127.0.0.1:8092}") final String nodes) {
    return CouchbaseCluster.create(SPLITTER.splitToList(nodes));
  }
  
  @Bean(destroyMethod = "close")
  @Autowired
  Bucket bucket(
      final Cluster cluster,
      @Value("${database.bucket.name:default}") final String name,
      @Value("${database.bucket.password:}") final String password) {
    return cluster.openBucket(name, password);
  }

  @Bean(destroyMethod = "close")
  @Autowired
  AsyncBucket asyncBucket(final Bucket bucket) {
    return bucket.async();
  }
}
