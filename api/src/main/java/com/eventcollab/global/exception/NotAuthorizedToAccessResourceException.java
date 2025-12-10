package com.eventcollab.global.exception;

public class NotAuthorizedToAccessResourceException extends RuntimeException {
  public NotAuthorizedToAccessResourceException(String resource) {
    super(String.format("You are not authorized to access %s", resource));
  }
}
