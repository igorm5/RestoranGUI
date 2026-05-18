package org.model;

import java.util.HashMap;
import java.util.Map;

// Abstract Class sebagai utama menu
public abstract class MenuItem implements Sellable {
    private String name;
    private double price;
    protected Map<String, Integer> recipe;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
        this.recipe = new HashMap<>();
    }

    public void addIngredient(String ingredient, int amount) {
        recipe.put(ingredient, amount);
    }

    public void setPrice(double price) { this.price = price; }
    
    @Override
    public double getPrice() { return price; }
    
    @Override
    public String getName() { return name; }

    @Override
    public void sell(Map<String, Integer> inventory) throws OutOfStockException {
        // Cek apakah stok cukup
        for (Map.Entry<String, Integer> entry : recipe.entrySet()) {
            int available = inventory.getOrDefault(entry.getKey(), 0);
            if (available < entry.getValue()) {
                throw new OutOfStockException("Bahan " + entry.getKey() + " habis untuk menu " + name);
            }
        }
        // Kurangi stok dari inventory
        for (Map.Entry<String, Integer> entry : recipe.entrySet()) {
            inventory.put(entry.getKey(), inventory.get(entry.getKey()) - entry.getValue());
        }
    }
}