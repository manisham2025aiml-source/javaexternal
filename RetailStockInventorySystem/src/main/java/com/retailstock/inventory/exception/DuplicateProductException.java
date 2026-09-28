package com.retailstock.inventory.exception;

public class DuplicateProductException extends RuntimeException {
    public DuplicateProductException(String message) { super(message); }
}
