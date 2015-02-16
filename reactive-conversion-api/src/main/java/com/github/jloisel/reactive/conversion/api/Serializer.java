package com.github.jloisel.reactive.conversion.api;

import rx.functions.Func1;

import com.couchbase.client.java.document.RawJsonDocument;
import com.github.jloisel.reactive.entity.api.ReactiveEntity;

public interface Serializer<T extends ReactiveEntity> extends
 Func1<T, RawJsonDocument> {

}
