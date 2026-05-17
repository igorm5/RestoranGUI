package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Restaurant {
    private double money;
    private int capacity;
    private Map<String, Integer> inventory;
    private List<Sellable> menuList;
    private List<Charm> activeCharms;

    public Restaurant(double initialMoney, int capacity) {
        this.money = initialMoney;
        this.capacity = capacity;
        this.inventory = new HashMap<>();
        this.menuList = new ArrayList<>();
        this.activeCharms = new ArrayList<>();
    }

    public void addMenu(Sellable menu) { menuList.add(menu); }
    public void addInventory(String item, int amount) { inventory.put(item, inventory.getOrDefault(item, 0) + amount); }
    public void addCharm(Charm charm) { activeCharms.add(charm); }
    public void addMoney(double amount) { this.money += amount; }
    
    public double getMoney() { return money; }
    public int getCapacity() { return capacity; }
    public List<Sellable> getMenu() { return menuList; }
    public Map<String, Integer> getInventory() { return inventory; }
    public List<Charm> getActiveCharms() { return activeCharms; }
    
    public void ratDisaster() {
        double preventChance = 0;
        for (Charm c : activeCharms) {
            if (c instanceof CleanerCharm) preventChance += c.getEffectPercentage();
        }
        
        if (new Random().nextDouble() * 100 > preventChance) {
            System.out.println("   [BENCANA!] Tikus lapar menyerang dapur!");
            for (String key : inventory.keySet()) {
                inventory.put(key, Math.max(0, inventory.get(key) - 2)); 
            }
        } else {
            System.out.println("   [BENCANA DICEGAH] Tikus datang, tapi Jimat Cleaner bekerja!");
        }
    }

    public void saveProgress(int day) {
        try (FileWriter writer = new FileWriter("savegame.txt")) {
            writer.write("Day: " + day + "\nMoney: " + money + "\nCapacity: " + capacity);
            System.out.println("[SYSTEM] Progress permainan berhasil disimpan ke savegame.txt");
        } catch (IOException e) {
            System.out.println("[ERROR] Gagal menyimpan file: " + e.getMessage());
        }
    }

    public void clearInventory() {
        inventory.clear(); 
    }
}