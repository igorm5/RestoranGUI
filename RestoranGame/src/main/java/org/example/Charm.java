package org.example;

import java.util.Random;

public abstract class Charm {
    protected double effectPercentage;
    protected String name;

    public Charm(String name) {
        this.name = name;
        this.effectPercentage = new Random().nextDouble() * 50.0; // Persentase random max 50%
    }
    
    public String getName() { return name; }
    public double getEffectPercentage() { return effectPercentage; }
}