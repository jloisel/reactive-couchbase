package com.github.jloisel.reactive.conversion.jackson;


final class JacksonConversionException extends RuntimeException {
  private static final long serialVersionUID = -4917703394134871581L;

  JacksonConversionException(String message, Throwable e) {
    super(e);
  }
}
