package org.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import org.controller.GameController;
import org.example.*;

public class DapurScene extends JPanel {

    private GameController ctrl;

    private JLabel   lblMoney;
    private JLabel   lblStok;
    private JLabel   lblLevel;
    private JTextArea logArea;

    private static final String[] BAHAN_NAMES = { "Kopi","Susu","Ayam","Beras","Kentang","Minyak","Jambu","Melon","Gula" };
    private static final int[]    BAHAN_HARGA = { 3000, 2000, 5000, 1500,  2500,   1000,   3000,   3000,   500 };
    private static final int      BELI_JUMLAH = 5;

    public DapurScene(GameController ctrl) {
        this.ctrl = ctrl;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(30, 20, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        buildUI();
    }

    private void buildUI() {
        // HEADER
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        header.setOpaque(false);

        lblLevel = makeLabel("Level: 1",  Color.YELLOW, 14);
        lblMoney = makeLabel("Uang: Rp0", Color.GREEN,  14);
        lblStok  = makeLabel("Stok: 0",   Color.WHITE,  14);
        header.add(lblLevel);
        header.add(lblMoney);
        header.add(lblStok);

        JButton btnJimat = makeButton("Toko Jimat", new Color(100, 60, 160));
        btnJimat.addActionListener(e -> ctrl.mainFrame().showPanel("jimat"));
        header.add(btnJimat);

        JButton btnUpgrade = makeButton("Upgrade Restoran", new Color(60, 120, 60));
        btnUpgrade.addActionListener(e -> handleUpgrade());
        header.add(btnUpgrade);

        add(header, BorderLayout.NORTH);

        // CENTER: grid beli bahan
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(20, 15, 8));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 8, 6, 8);
        gc.fill = GridBagConstraints.HORIZONTAL;

        gc.gridx=0; gc.gridy=0; center.add(makeLabel("Bahan Baku", Color.ORANGE, 13), gc);
        gc.gridx=1;             center.add(makeLabel("Harga/unit",  Color.ORANGE, 13), gc);
        gc.gridx=2;             center.add(makeLabel("Stok saat ini",Color.ORANGE, 13), gc);
        gc.gridx=3;             center.add(makeLabel("Beli x5",    Color.ORANGE, 13), gc);

        for (int i = 0; i < BAHAN_NAMES.length; i++) {
            final int idx = i;
            gc.gridy = i + 1;
            gc.gridx = 0; center.add(makeLabel(BAHAN_NAMES[i], Color.WHITE, 12), gc);
            gc.gridx = 1; center.add(makeLabel("Rp" + BAHAN_HARGA[i], new Color(200,200,200), 12), gc);
            gc.gridx = 2;
            JLabel stokLbl = makeLabel("0", Color.CYAN, 12);
            stokLbl.setName("stok_" + BAHAN_NAMES[i]);
            center.add(stokLbl, gc);
            gc.gridx = 3;
            JButton btn = makeButton("Beli", new Color(60, 100, 160));
            btn.addActionListener(e -> handleBeli(idx));
            center.add(btn, gc);
        }

        JScrollPane scroll = new JScrollPane(center);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(255,220,150)));
        add(scroll, BorderLayout.CENTER);

        // BOTTOM: log + tombol buka
        JPanel bottom = new JPanel(new BorderLayout(8, 8));
        bottom.setOpaque(false);

        logArea = new JTextArea(5, 40);
        logArea.setEditable(false);
        logArea.setBackground(new Color(10, 8, 4));
        logArea.setForeground(Color.WHITE);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(255,220,150)),
            "Log", 0, 0, new Font("Arial", Font.BOLD, 11), Color.ORANGE));
        bottom.add(logScroll, BorderLayout.CENTER);

        JButton btnBuka = makeButton("BUKA RESTORAN!", new Color(180, 60, 20));
        btnBuka.setFont(new Font("Arial", Font.BOLD, 15));
        btnBuka.setPreferredSize(new Dimension(220, 50));
        btnBuka.addActionListener(e -> ctrl.startSellingPhase());
        bottom.add(btnBuka, BorderLayout.EAST);

        add(bottom, BorderLayout.SOUTH);
    }

    private void handleBeli(int idx) {
        boolean ok = ctrl.beliStok(BAHAN_NAMES[idx], BELI_JUMLAH, BAHAN_HARGA[idx]);
        log(ok ? "Beli " + BELI_JUMLAH + "x " + BAHAN_NAMES[idx] + " (-Rp" + (BELI_JUMLAH * BAHAN_HARGA[idx]) + ")"
               : "Uang tidak cukup!");
    }

    private void handleUpgrade() {
        int[] cost = {0, 50000, 80000, 120000, 200000};
        int lv = ctrl.getCurrentLevel();
        if (lv >= 5) { log("Sudah level maksimal!"); return; }
        boolean ok = ctrl.upgradeLevel();
        log(ok ? "Upgrade ke Level " + ctrl.getCurrentLevel() + "!"
               : "Uang tidak cukup. Butuh Rp" + cost[lv]);
    }

    public void refresh(int uang, int stok, Map<String, Integer> inventory,
                        java.util.List<org.example.Sellable> menu) {
        lblMoney.setText("Uang: Rp" + uang);
        lblStok.setText("Stok: " + stok);
        lblLevel.setText("Level: " + ctrl.getCurrentLevel());
        updateStokLabels(inventory);
        repaint();
    }

    private void updateStokLabels(Map<String, Integer> inventory) {
        for (String bahan : BAHAN_NAMES) {
            findLabelByName("stok_" + bahan, this,
                lbl -> lbl.setText(String.valueOf(inventory.getOrDefault(bahan, 0))));
        }
    }

    private void findLabelByName(String name, Container c, java.util.function.Consumer<JLabel> action) {
        for (Component comp : c.getComponents()) {
            if (comp instanceof JLabel && name.equals(comp.getName())) action.accept((JLabel) comp);
            if (comp instanceof Container) findLabelByName(name, (Container) comp, action);
        }
    }

    public void log(String msg) {
        logArea.append(msg + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    private JLabel makeLabel(String t, Color c, int s) {
        JLabel l = new JLabel(t); l.setForeground(c); l.setFont(new Font("Arial", Font.BOLD, s)); return l;
    }

    private JButton makeButton(String t, Color bg) {
        JButton b = new JButton(t);
        b.setBackground(bg); b.setForeground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 12));
        b.setFocusPainted(false); b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }
}
