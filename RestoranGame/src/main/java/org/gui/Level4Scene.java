package org.gui;

import javax.swing.*;
import java.awt.*;
import org.controller.GameController;

public class Level4Scene extends JPanel implements LevelScene {

    private GameController ctrl;
    private Image foodImage;
    private int level = 4;
    private int uang  = 0;
    private int stok  = 0;
    private Image levelBox, uangBox, stokBox;
    private String gameMessage = "Selamat datang di Level 4!";
    private String timerText   = "05:00";

    public Level4Scene(GameController ctrl) {
        this.ctrl = ctrl;
        setLayout(null);
        loadImages();
        buildButtons();
    }

    private void loadImages() {
        try {
            foodImage = new ImageIcon(getClass().getResource("/background/level4.png")).getImage();
            levelBox  = new ImageIcon(getClass().getResource("/status/level.png")).getImage();
            uangBox   = new ImageIcon(getClass().getResource("/status/uang.png")).getImage();
            stokBox   = new ImageIcon(getClass().getResource("/status/stok.png")).getImage();
        } catch (Exception e) { System.err.println("Gambar tidak ditemukan: " + e.getMessage()); }
    }

    private void buildButtons() {
        String[] menuImages = { "/menu/kopi.png", "/menu/nasiayam.png", "/menu/kentanggoreng.png", "/menu/susu.png" };
        JPanel menuPanel = new JPanel(new GridLayout(1, menuImages.length, 5, 15));
        menuPanel.setOpaque(false);
        menuPanel.setBounds(40, 355, 720, 100);
        for (String path : menuImages) {
            JButton btn = new JButton();
            setupMenuButton(btn, path);
            menuPanel.add(btn);
        }
        add(menuPanel);

        JButton btnSkip = makeButton("SKIP HARI INI", new Color(150, 80, 20));
        btnSkip.setBounds(550, 555, 200, 35);
        btnSkip.addActionListener(e -> ctrl.skipDay());
        add(btnSkip);
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(30, 20, 10));
        g2.fillRect(0, 0, getWidth(), getHeight());
        drawStatusBox(g2, levelBox, 60, 20, String.valueOf(level));
        drawStatusBox(g2, uangBox, 300, 20, "Rp" + uang);
        drawStatusBox(g2, stokBox, 540, 20, String.valueOf(stok));
        if (foodImage != null) g.drawImage(foodImage, 60, 60, 680, 280, this);
        g2.setColor(new Color(255, 220, 150));
        g2.fillRoundRect(80, 345, 640, 5, 10, 10);
        g2.setColor(new Color(255, 220, 80));
        g2.setFont(loadFont(18f));
        g2.drawString("Waktu: " + timerText, 340, 555);
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(40, 480, 720, 60, 20, 20);
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(40, 480, 720, 60, 20, 20);
        g2.setFont(loadFont(11f));
        g2.setColor(Color.WHITE);
        g2.drawString(gameMessage, 60, 510);
    }

    private void drawStatusBox(Graphics2D g2, Image img, int x, int y, String value) {
        if (img != null) g2.drawImage(img, x, y, 180, 30, this);
        g2.setFont(loadFont(14f));
        g2.setColor(Color.WHITE);
        g2.drawString(value, x + 90, y + 23);
    }

    private Font loadFont(float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT,
                getClass().getResourceAsStream("/fonts/PressStart.ttf")).deriveFont(size);
        } catch (Exception e) { return new Font("Arial", Font.BOLD, (int) size); }
    }

    private void setupMenuButton(JButton button, String path) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            Image normal = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            Image hover  = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            ImageIcon ni = new ImageIcon(normal), hi = new ImageIcon(hover);
            button.setIcon(ni);
            button.setBorderPainted(false); button.setContentAreaFilled(false);
            button.setFocusPainted(false);  button.setOpaque(false);
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) { button.setIcon(hi); }
                public void mouseExited(java.awt.event.MouseEvent e)  { button.setIcon(ni); }
            });
        } catch (Exception e) { button.setText(path); }
    }

    private JButton makeButton(String t, Color bg) {
        JButton b = new JButton(t); b.setBackground(bg); b.setForeground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 12)); b.setFocusPainted(false); b.setBorderPainted(false);
        return b;
    }

    @Override public void setGameMessage(String msg) { this.gameMessage = msg; repaint(); }
    @Override public void setUang(int uang)           { this.uang = uang; repaint(); }
    @Override public void setStok(int stok)           { this.stok = stok; repaint(); }
    @Override public void setTimerText(String t)      { this.timerText = t; repaint(); }
}
