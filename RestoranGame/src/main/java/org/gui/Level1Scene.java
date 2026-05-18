package org.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.controller.GameController;

public class Level1Scene extends JPanel implements LevelScene {

    private GameController ctrl;
    private Image foodImage;

    private int level = 1;
    private int uang = 0;
    private int stok = 0;

    private Image levelBox, uangBox, stokBox;

    private String gameMessage = "Selamat datang di Restaurant Tycoon!";
    private String timerText = "05:00";

    public Level1Scene(GameController ctrl) {
        this.ctrl = ctrl;
        setLayout(null);
        loadImages();
        buildButtons();
    }

    private void loadImages() {
        try {
            foodImage = new ImageIcon(getClass().getResource("/background/level1.png")).getImage();
            levelBox = new ImageIcon(getClass().getResource("/status/level.png")).getImage();
            uangBox = new ImageIcon(getClass().getResource("/status/uang.png")).getImage();
            stokBox = new ImageIcon(getClass().getResource("/status/stok.png")).getImage();
        } catch (Exception e) {
            System.err.println("Gambar tidak ditemukan: " + e.getMessage());
        }
    }

    private void buildButtons() {
        JPanel menuPanel = new JPanel(new GridLayout(1, 1, 15, 15));
        menuPanel.setOpaque(false);
        menuPanel.setBounds(-20, 240, 720, 100);

        String[] menuImages = { "/menu/kopi.png" };
        for (String path : menuImages) {
            JButton btn = new JButton();
            setupMenuButton(btn, path);
            menuPanel.add(btn);
        }
        add(menuPanel);

        // Tombol Skip
        JButton btnSkip = makeButton("SKIP HARI INI", new Color(150, 80, 20));
        btnSkip.setBounds(450, 410, 200, 35);
        btnSkip.addActionListener(e -> ctrl.skipDay());
        add(btnSkip);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(30, 20, 10));
        g2.fillRect(0, 0, getWidth(), getHeight());
        drawStatusBox(g2, levelBox, 30, 10, String.valueOf(level));
        drawStatusBox(g2, uangBox, 230, 10, "Rp" + uang);
        drawStatusBox(g2, stokBox, 430, 10, String.valueOf(stok));
        if (foodImage != null)
            g.drawImage(foodImage, 30, 40, 600, 200, this);
        g2.setColor(new Color(255, 220, 150));
        g2.fillRoundRect(-30, 250, 1000, 5, 10, 10);
        // Timer
        g2.setColor(new Color(255, 220, 80));
        g2.setFont(loadFont(18f));
        g2.drawString("Waktu: " + timerText, 20, 450);
        // Message box
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(20, 340, 600, 60, 20, 20);
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(20, 340, 600, 60, 20, 20);
        g2.setFont(loadFont(11f));
        g2.setColor(Color.WHITE);
        g2.drawString(gameMessage, 40, 370);
    }

    private void drawStatusBox(
            Graphics2D g2,
            Image img,
            int x,
            int y,
            String value

    ) {

        // DRAW PNG
        if (img != null) {

            g2.drawImage(
                    img,
                    x,
                    y,
                    180,
                    30,
                    this);
        }

        // VALUE
        g2.setFont(loadFont(16f));
        g2.setColor(new Color(30, 20, 10));

        g2.drawString(value, x + 40, y + 23);
    }

    private Font loadFont(float size) {
        return FontUtil.loadFont(size);
    }

    private void setupMenuButton(JButton button, String path) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            Image normal = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            Image hover = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            ImageIcon ni = new ImageIcon(normal), hi = new ImageIcon(hover);
            button.setIcon(ni);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setOpaque(false);
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    button.setIcon(hi);
                }

                public void mouseExited(java.awt.event.MouseEvent e) {
                    button.setIcon(ni);
                }
            });
        } catch (Exception e) {
            button.setText(path);
        }
    }

    private JButton makeButton(String t, Color bg) {
        JButton b = new JButton(t);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(FontUtil.loadFont((float) 12));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        return b;
    }

    @Override
    public void setGameMessage(String msg) {
        this.gameMessage = msg;
        repaint();
    }

    @Override
    public void setUang(int uang) {
        this.uang = uang;
        repaint();
    }

    @Override
    public void setStok(int stok) {
        this.stok = stok;
        repaint();
    }

    @Override
    public void setTimerText(String t) {
        this.timerText = t;
        repaint();
    }
}
