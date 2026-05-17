package org.controller;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.example.*;
import org.gui.*;

/**
 * GameController: penghubung antara core logic (Restaurant) dan GUI.
 * Mengelola state game, fase permainan, dan komunikasi antar scene.
 */
public class GameController {

    // ─── State Game ───────────────────────────────────────────────────────────
    public enum Phase { PREPARATION, SELLING, RECAP }

    private Phase currentPhase = Phase.PREPARATION;
    private int currentDay    = 1;
    private int currentLevel  = 1;

    // ─── Core Logic ───────────────────────────────────────────────────────────
    private Restaurant restaurant;

    // ─── GUI References ───────────────────────────────────────────────────────
    private MainFrame mainFrame;

  
    private DapurScene dapurScene;
    private JimatScene jimatScene;
    private RekapScene rekapScene;

    private LevelScene activeLevelScene; // proxy ke Level1-5Scene

    // ─── Selling-phase state ───────────────────────────────────────────────────
    private Timer customerTimer;       // timer munculnya pelanggan
    private Timer countdownTimer;      // timer 5 menit
    private int secondsLeft   = 300; // 5 menit
    private int totalRevenue  = 0;
    private int totalLoss     = 0;
    private int customersServed = 0;
    private boolean disasterTriggered = false;

    // ─── Menu per level ────────────────────────────────────────────────────────
    // Tiap level membuka lebih banyak menu
    private static final String[][] LEVEL_MENUS = {
        { "/menu/kopi.png" },
        { "/menu/kopi.png", "/menu/nasiayam.png" },
        { "/menu/kopi.png", "/menu/nasiayam.png", "/menu/kentanggoreng.png" },
        { "/menu/kopi.png", "/menu/nasiayam.png", "/menu/kentanggoreng.png", "/menu/susu.png" },
        { "/menu/kopi.png", "/menu/nasiayam.png", "/menu/kentanggoreng.png", "/menu/susu.png",
          "/menu/jusjambu.png", "/menu/jusmelon.png" }
    };

    private static final String[] MENU_NAMES = {
        "Es Kopi Susu", "Nasi Ayam", "Kentang Goreng", "Susu Segar", "Jus Jambu", "Jus Melon"
    };

    private static final int[] MENU_PRICES = {
        15000, 25000, 12000, 10000, 18000, 18000
    };

    private static final String[][] MENU_INGREDIENTS = {
        { "Kopi", "Susu" },
        { "Ayam", "Beras" },
        { "Kentang", "Minyak" },
        { "Susu" },
        { "Jambu", "Gula" },
        { "Melon", "Gula" }
    };

    // ─── Rekap harian ─────────────────────────────────────────────────────────
    private List<String> dailyLog = new ArrayList<>();

    // ─── Upgrade cost per level ────────────────────────────────────────────────
    private static final int[] UPGRADE_COST = { 0, 50000, 80000, 120000, 200000 };
    private static final int[] LEVEL_CAPACITY = {10, 15, 20, 25, 30};

    public GameController(MainFrame frame) {
        // mainFrame() getter added below

        this.mainFrame  = frame;
        this.restaurant = new Restaurant(100000, 10);
        setupMenuForLevel(currentLevel);
    }

    /** Mulai New Game — reset state dan masuk fase persiapan */
    public void newGame() {
        this.currentDay = 1;
        this.currentLevel = 1;
        this.restaurant = new Restaurant(100000, 10);
        setupMenuForLevel(currentLevel);
        dailyLog.clear();
        dailyLog.add("=== HARI KE-" + currentDay + " ===");
        refreshHUD();
        mainFrame.showPanel("dapur");
    }

    /** Muat game dari file savegame.txt (jika ada) dan lanjutkan ke fase persiapan */
    public void loadGame() {
        int day = 1;
        double money = 100000;
        int capacity = 10;
        int level = 1;
        Map<String, Integer> savedInventory = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("savegame.txt"))) {
            String line;
            boolean readingInventory = false;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Day:")) day = Integer.parseInt(line.split(":")[1].trim());
                else if (line.startsWith("Money:")) money = Double.parseDouble(line.split(":")[1].trim());
                else if (line.startsWith("Capacity:")) capacity = Integer.parseInt(line.split(":")[1].trim());
                else if (line.startsWith("Level:")) level = Integer.parseInt(line.split(":")[1].trim());
                else if (line.startsWith("Inventory:")) readingInventory = true;
                else if (readingInventory && line.contains("=")) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        String item = parts[0].trim();
                        int qty = Integer.parseInt(parts[1].trim());
                        savedInventory.put(item, qty);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("[LOAD] Tidak ada savegame, memulai game baru.");
        }

        this.currentDay = day;
        this.currentLevel = Math.min(Math.max(level, 1), 5);
        this.restaurant = new Restaurant(money, capacity);
        setupMenuForLevel(currentLevel);
        this.restaurant.getInventory().clear();
        for (Map.Entry<String, Integer> entry : savedInventory.entrySet()) {
            restaurant.addInventory(entry.getKey(), entry.getValue());
        }
        dailyLog.clear();
        dailyLog.add("=== DILANJUTKAN DARI SAVE: Hari ke-" + currentDay + " ===");
        refreshHUD();
        mainFrame.showPanel("dapur");
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  SETUP
    // ══════════════════════════════════════════════════════════════════════════

    /** Registrasi scene dari MainFrame setelah scene dibuat */
    public void registerScenes(DapurScene dapur, JimatScene jimat, RekapScene rekap) {
        this.dapurScene  = dapur;
        this.jimatScene  = jimat;
        this.rekapScene  = rekap;
    }

    public void setActiveLevelScene(LevelScene scene) {
        this.activeLevelScene = scene;
    }

    /** Bangun menu yang tersedia sesuai level */
    private void setupMenuForLevel(int level) {
        restaurant.getMenu().clear();
        int count = LEVEL_MENUS[level - 1].length;
        for (int i = 0; i < count; i++) {
            String[] ingr = MENU_INGREDIENTS[i];
            if (i < 4) { // Food / Drink berdasarkan tipe
                Food f = new Food(MENU_NAMES[i], MENU_PRICES[i]);
                for (String ing : ingr) f.addIngredient(ing, 1);
                restaurant.addMenu(f);
            } else {
                Drink d = new Drink(MENU_NAMES[i], MENU_PRICES[i], "fruit based");
                for (String ing : ingr) d.addIngredient(ing, 1);
                restaurant.addMenu(d);
            }
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  GETTERS UNTUK GUI
    // ══════════════════════════════════════════════════════════════════════════

    public int getCurrentLevel() { return currentLevel; }
    public int getCurrentDay() { return currentDay; }
    public double getMoney() { return restaurant.getMoney(); }
    public int getStok() { return getTotalStock(); }
    public Phase getCurrentPhase() { return currentPhase; }
    public List<String> getDailyLog() { return dailyLog; }
    public int getSecondsLeft() { return secondsLeft; }

    /** Hitung total stok semua bahan */
    public int getTotalStock() {
        return restaurant.getInventory().values().stream().mapToInt(Integer::intValue).sum();
    }

    public String[] getMenuImagesForCurrentLevel() {
        return LEVEL_MENUS[currentLevel - 1];
    }

    public List<Sellable> getCurrentMenu() {
        return new ArrayList<>(restaurant.getMenu());
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  FASE PERSIAPAN – aksi pemain
    // ══════════════════════════════════════════════════════════════════════════

    /** Pemain membeli bahan baku di DapurScene / JimatScene */
    public boolean beliStok(String bahan, int jumlah, int hargaPerUnit) {
        double total = (double) jumlah * hargaPerUnit;
        if (restaurant.getMoney() < total) return false;
        restaurant.addInventory(bahan, jumlah);
        restaurant.addMoney(-total);
        refreshHUD();
        return true;
    }

    /** Pemain membeli jimat */
    public boolean beliJimat(String tipeJimat) {
        int harga = 30000;
        if (restaurant.getMoney() < harga) return false;
        restaurant.addMoney(-harga);
        Charm charm;
        switch (tipeJimat) {
            case "Charming": charm = new CharmingCharm(); break;
            case "Security": charm = new SecurityCharm(); break;
            default:         charm = new CleanerCharm();  break;
        }
        restaurant.addCharm(charm);
        dailyLog.add("[JIMAT] " + charm.getName() + " dibeli (efek " +
                String.format("%.1f", charm.getEffectPercentage()) + "%)");
        refreshHUD();
        return true;
    }

    /** Pemain mengubah harga menu */
    public void setMenuPrice(int menuIndex, double harga) {
        if (menuIndex < restaurant.getMenu().size()) {
            ((MenuItem) restaurant.getMenu().get(menuIndex)).setPrice(harga);
        }
    }

    /** Upgrade level restoran */
    public boolean upgradeLevel() {
        if (currentLevel >= 5) return false;
        int cost = UPGRADE_COST[currentLevel];
        if (restaurant.getMoney() < cost) return false;
        restaurant.addMoney(-cost);
        currentLevel++;
        restaurant.setCapacity(LEVEL_CAPACITY[currentLevel - 1]);
        setupMenuForLevel(currentLevel);
        dailyLog.add("[UPGRADE] Restoran naik ke Level " + currentLevel + "! (-Rp" + cost + ")");
        refreshHUD();
        return true;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  FASE PENJUALAN
    // ══════════════════════════════════════════════════════════════════════════

    /** Dipanggil saat pemain tekan "Buka Restoran" */
    public void startSellingPhase() {
        currentPhase    = Phase.SELLING;
        secondsLeft     = 300;
        totalRevenue    = 0;
        totalLoss       = 0;
        customersServed = 0;
        disasterTriggered = false;
        dailyLog.add("=== HARI KE-" + currentDay + " DIMULAI ===");

        // Pastikan stok ada (beli otomatis basic jika lupa)
        ensureBasicStock();

        // Tampilkan level scene
        mainFrame.showLevelScene(currentLevel);

        // Timer pelanggan: setiap 8 detik ada pelanggan datang
        customerTimer = new Timer(8000, e -> spawnCustomer());
        customerTimer.start();

        // Timer countdown 5 menit (tick tiap detik)
        countdownTimer = new Timer(1000, e -> {
            secondsLeft--;
            updateTimerDisplay();

            // Bencana: trigger sekali antara detik 60-240
            if (!disasterTriggered && secondsLeft <= 240 && secondsLeft >= 60) {
                double baseChance = 0.008 * currentLevel; // makin tinggi level, makin sering
                if (new Random().nextDouble() < baseChance) {
                    triggerDisaster();
                    disasterTriggered = true;
                }
            }

            if (secondsLeft <= 0) endSellingPhase(false);
        });
        countdownTimer.start();
    }

    /** Pemain tekan tombol Skip */
    public void skipDay() {
        if (currentPhase != Phase.SELLING) return;
        // Hitung sisa pelanggan secara instan
        int remainingCustomers = secondsLeft / 8;
        for (int i = 0; i < remainingCustomers; i++) spawnCustomerSilent();
        endSellingPhase(true);
    }

    /** Munculkan satu pelanggan (dengan update GUI) */
    private void spawnCustomer() {
        if (restaurant.getMenu().isEmpty()) return;
        Random rand = new Random();
        Sellable menu = restaurant.getMenu().get(rand.nextInt(restaurant.getMenu().size()));

        String logLine;
        try {
            menu.sell(restaurant.getInventory());

            // Cek kabur (dipengaruhi SecurityCharm)
            double secBonus = getCharmBonus("Security");
            if (rand.nextDouble() * 100 < (15 - secBonus)) {
                // Kabur!
                totalLoss += (int) menu.getPrice();
                logLine = "❌ Pelanggan kabur setelah memesan " + menu.getName() + "!";
                mainFrame.showPopupKabur();
            } else {
                // Tips (CharmingCharm)
                double tipsBonus = getCharmBonus("Charming");
                int tips = 0;
                if (rand.nextDouble() * 100 < tipsBonus) {
                    tips = (int)(menu.getPrice() * 0.1);
                }
                int earned = (int) menu.getPrice() + tips;
                restaurant.addMoney(earned);
                totalRevenue += earned;
                customersServed++;
                logLine = "✅ Pelanggan memesan " + menu.getName() +
                          " (+Rp" + (int)menu.getPrice() + (tips > 0 ? " +tips Rp"+tips : "") + ")";
            }
        } catch (OutOfStockException e) {
            logLine = "⚠️ Bahan habis untuk " + menu.getName() + " — pelanggan kecewa.";
        }

        dailyLog.add(logLine);
        if (activeLevelScene != null) activeLevelScene.setGameMessage(logLine);
        refreshHUD();
    }

    /** Versi silent untuk skip */
    private void spawnCustomerSilent() {
        if (restaurant.getMenu().isEmpty()) return;
        Random rand = new Random();
        Sellable menu = restaurant.getMenu().get(rand.nextInt(restaurant.getMenu().size()));
        try {
            menu.sell(restaurant.getInventory());
            double secBonus = getCharmBonus("Security");
            if (rand.nextDouble() * 100 < (15 - secBonus)) {
                totalLoss += (int) menu.getPrice();
            } else {
                restaurant.addMoney(menu.getPrice());
                totalRevenue += (int) menu.getPrice();
                customersServed++;
            }
        } catch (OutOfStockException ignored) {}
    }

    /** Trigger bencana acak */
    private void triggerDisaster() {
        Random rand = new Random();
        if (rand.nextBoolean()) {
            // Tikus
            double cleanBonus = getCharmBonus("Cleaner");
            if (rand.nextDouble() * 100 > cleanBonus) {
                restaurant.ratDisaster();
                int kerugian = 20000;
                totalLoss += kerugian;
                dailyLog.add("🐀 BENCANA! Tikus menyerang dapur! Stok berkurang.");
                if (activeLevelScene != null) activeLevelScene.setGameMessage("🐀 TIKUS MENYERANG! Stok berkurang!");
                mainFrame.showPopupTikus();
            } else {
                dailyLog.add("🐀 Tikus datang tapi Jimat Cleaner mencegahnya!");
            }
        } else {
            // Pembeli kabur extra
            dailyLog.add("💨 Pembeli kabur mendadak!");
            if (activeLevelScene != null) activeLevelScene.setGameMessage("💨 Ada pembeli yang kabur!");
            mainFrame.showPopupKabur();
        }
        refreshHUD();
    }

    /** Helper: total efek persentase charm berdasarkan tipe */
    private double getCharmBonus(String tipe) {
        double bonus = 0;
        for (Charm c : restaurant.getActiveCharms()) {
            if (tipe.equals("Security") && c instanceof SecurityCharm) bonus += c.getEffectPercentage();
            if (tipe.equals("Charming") && c instanceof CharmingCharm) bonus += c.getEffectPercentage();
            if (tipe.equals("Cleaner")  && c instanceof CleanerCharm)  bonus += c.getEffectPercentage();
        }
        return Math.min(bonus, 90); // cap 90%
    }

    /** Hentikan fase penjualan dan pindah ke rekap */
    private void endSellingPhase(boolean skipped) {
        if (customerTimer  != null) customerTimer.stop();
        if (countdownTimer != null) countdownTimer.stop();

        // Kerugian bahan basi
        int basiLoss = 0;
        for (int val : restaurant.getInventory().values()) basiLoss += val * 1000;
        totalLoss += basiLoss;
        dailyLog.add("🗑️ Bahan sisa dibuang (kerugian Rp" + basiLoss + ")");

        restaurant.clearInventory();
        restaurant.getActiveCharms().clear(); // charm habis per hari
        restaurant.saveProgress(currentDay, currentLevel);

        currentPhase = Phase.RECAP;
        showRecap(skipped, basiLoss);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  REKAP
    // ══════════════════════════════════════════════════════════════════════════

    private void showRecap(boolean skipped, int basiLoss) {
        if (rekapScene != null) {
            rekapScene.update(currentDay, totalRevenue, totalLoss, basiLoss,
                              (int) restaurant.getMoney(), dailyLog);
            mainFrame.showPanel("rekap");
        }
    }

    /** Lanjut ke hari berikutnya */
    public void nextDay() {
        currentDay++;
        currentPhase = Phase.PREPARATION;
        dailyLog.clear();
        dailyLog.add("=== HARI KE-" + currentDay + " ===");
        mainFrame.showPanel("dapur");
        refreshHUD();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  HELPERS
    // ══════════════════════════════════════════════════════════════════════════

    /** Pastikan bahan dasar ada agar game tidak langsung crash */
    private void ensureBasicStock() {
        String[][] basics = {
            {"Kopi","Susu"},{"Ayam","Beras"},{"Kentang","Minyak"},
            {"Susu"},{"Jambu","Gula"},{"Melon","Gula"}
        };
        int menuCount = restaurant.getMenu().size();
        for (int i = 0; i < menuCount; i++) {
            for (String ing : basics[i]) {
                if (restaurant.getInventory().getOrDefault(ing, 0) == 0) {
                    restaurant.addInventory(ing, 5);
                }
            }
        }
    }

    private void updateTimerDisplay() {
        if (activeLevelScene != null) {
            int mnt = secondsLeft / 60;
            int dtk = secondsLeft % 60;
            activeLevelScene.setTimerText(String.format("%02d:%02d", mnt, dtk));
        }
    }

    public void refreshHUD() {
        if (activeLevelScene != null) {
            activeLevelScene.setUang((int) restaurant.getMoney());
            activeLevelScene.setStok(getTotalStock());
        }
        if (dapurScene != null) {
            dapurScene.refresh((int) restaurant.getMoney(), getTotalStock(),
                               restaurant.getInventory(), restaurant.getMenu());
        }
        if (jimatScene != null) {
            jimatScene.refresh((int) restaurant.getMoney(), restaurant.getActiveCharms());
        }
    }
    public MainFrame mainFrame() { return mainFrame; }
}
