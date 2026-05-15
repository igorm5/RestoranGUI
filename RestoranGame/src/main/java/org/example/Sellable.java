package org.example;

import java.util.Map;

public interface Sellable {
    double getPrice();
    String getName();
    void sell(Map<String, Integer> inventory) throws OutOfStockException;
}