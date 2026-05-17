package org.example;

import java.util.Random;

public class TycoonGame {
    public static void main(String[] args) {
        System.out.println("=== MEMULAI RESTAURANT TYCOON ===\n");
        Restaurant myResto = new Restaurant(100000, 10);

        // Setup Menu
        Food nasiGoreng = new Food("Nasi Goreng", 25000);
        nasiGoreng.addIngredient("Beras", 2);
        nasiGoreng.addIngredient("Telur", 1);
        
        Drink esKopi = new Drink("Es Kopi Susu", 15000, "coffee based");
        esKopi.addIngredient("Kopi", 1);
        esKopi.addIngredient("Susu", 1);

        myResto.addMenu(nasiGoreng);
        myResto.addMenu(esKopi);

        // Run Simulasi
        simulateDay(myResto, 1);
        //myResto.saveProgress(1);
    }

    private static void simulateDay(Restaurant resto, int day) {
        System.out.println("--- HARI KE-" + day + " ---");
        System.out.println("Kas Awal: Rp" + resto.getMoney());

        // Fase Persiapan
        System.out.println("\n[FASE PERSIAPAN]");
        resto.addInventory("Beras", 10);
        resto.addInventory("Telur", 5);
        resto.addInventory("Kopi", 5);
        resto.addInventory("Susu", 5);
        resto.addMoney(-30000);
        
        SecurityCharm security = new SecurityCharm();
        resto.addCharm(security);
        System.out.println("Membeli bahan baku dan mendapatkan " + security.getName() + " (Efek cegah maling: " + String.format("%.1f", security.getEffectPercentage()) + "%)");

        // Fase Berjualan
        System.out.println("\n[FASE BERJUALAN]");
        Random rand = new Random();
        int customerCount = rand.nextInt(resto.getCapacity()) + 3; 
        System.out.println("Pelanggan datang hari ini: " + customerCount + " orang.");

        for (int i = 1; i <= customerCount; i++) {
            Sellable orderedMenu = resto.getMenu().get(rand.nextInt(resto.getMenu().size()));
            System.out.print("Pelanggan " + i + " memesan " + orderedMenu.getName() + "... ");
            
            try {
                orderedMenu.sell(resto.getInventory());
                
                if (rand.nextDouble() * 100 < 20 - security.getEffectPercentage()) {
                    System.out.println("\n   [BENCANA!] Pembeli kabur! Bahan terpakai, uang tidak masuk.");
                } else {
                    resto.addMoney(orderedMenu.getPrice());
                    System.out.println("Sukses! (+Rp" + orderedMenu.getPrice() + ")");
                }
            } catch (OutOfStockException e) {
                System.out.println("Gagal! (" + e.getMessage() + ") -> Pelanggan pergi.");
            }
        }

        resto.ratDisaster();

        // Fase Akhir Hari
        System.out.println("\n[AKHIR HARI KE-" + day + "]");
        System.out.println("Bahan baku tersisa basi dibuang.");
        resto.clearInventory(); 
        System.out.println("Kas Akhir: Rp" + resto.getMoney() + "\n");
    }
}