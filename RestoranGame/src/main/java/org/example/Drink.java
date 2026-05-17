package org.example;

public class Drink extends MenuItem {
    private String type; // coffee based, milk based, fruit based

    public Drink(String name, double price, String type) {
        super(name, price);
        this.type = type;
    }
}