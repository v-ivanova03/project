package org.example.market.exception;

public class InsufficientQuantityException extends RuntimeException {
  public InsufficientQuantityException(String message) {
    super(message);
  }
}
