function (doc, meta) {
	if (doc._class == "com.github.jloisel.reactive.repository.couchbase.it.Person") {
		emit(null, null);
	}
}