package org.example;

// Custom Exception bahan habis
public class OutOfStockException extends Exception {
    public OutOfStockException(String message) {
        super(message);
    }
}