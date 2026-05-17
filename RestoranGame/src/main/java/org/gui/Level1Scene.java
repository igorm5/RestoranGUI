/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.gui;

/**
 *
 * @author ASUS
 */


import javax.swing.*;
import java.awt.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Level1Scene extends JPanel {

    private Image foodImage;

    // VALUE GAME
    private int level = 1;
    private int uang = 5;
    private int stok = 5;

    // IMAGE UI ATAS
    private Image levelBox;
    private Image uangBox;
    private Image stokBox;
    
    private String gameMessage =
        "Selamat datang di Restaurant Tycoon!";

    public Level1Scene() {

        setLayout(null);

        // FOTO
        foodImage = new ImageIcon(
                getClass().getResource("/background/level1.png")
        ).getImage();

        // BOX STATUS PNG
        levelBox = new ImageIcon(
                getClass().getResource("/status/level.png")
        ).getImage();

        uangBox = new ImageIcon(
                getClass().getResource("/status/uang.png")
        ).getImage();

        stokBox = new ImageIcon(
                getClass().getResource("/status/stok.png")
        ).getImage();

       
        JPanel menuPanel = new JPanel();

        menuPanel.setLayout(new GridLayout(1, 6, 15, 15));
        menuPanel.setOpaque(false);

        menuPanel.setBounds(40, 355, 720, 100);

        // ARRAY IMAGE BUTTON
        String[] menuImages = {
                "/menu/kopi.png",
        };

        for (String path : menuImages) {

            JButton btn = new JButton();

            setupMenuButton(btn, path);

            menuPanel.add(btn);
        }

        add(menuPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // BACKGROUND
        g2.setColor(new Color(30, 20, 10));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // STATUS BOX ATAS

        drawStatusBox(g2, levelBox, 60, 20, String.valueOf(level));

        drawStatusBox(g2, uangBox, 300, 20, String.valueOf(uang));

        drawStatusBox(g2, stokBox, 540, 20, String.valueOf(stok));

        // FOTO

        if (foodImage != null) {

            g.drawImage(
                    foodImage,
                    60,
                    60,
                    680,
                    280,
                    this
            );
        }

        // GARIS
        g2.setColor(new Color(255, 220, 150));

        g2.fillRoundRect(
                80,
                345,
                640,
                5,
                10,
                10
        );
        
        // BOX
g2.setColor(new Color(0, 0, 0, 180));

g2.fillRoundRect(
        40,   // x
        480,  // y
        720,  // width
        60,   // height
        20,   // arc width
        20    // arc height
);

// BORDER
g2.setColor(Color.WHITE);

g2.drawRoundRect(
        40,
        480,
        720,
        60,
        20,
        20
);

// TEXT
g2.setFont(loadFont(14f));

g2.drawString(
        gameMessage,
        60,
        510
);
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
                this
        );
    }

    // VALUE
    g2.setFont(loadFont(16f));
    g2.setColor(new Color(30, 20, 10));

    g2.drawString(value, x + 40, y + 23);
}

    private Font loadFont(float size) {

        try {

            Font font = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fonts/PressStart.ttf")
            );

            return font.deriveFont(size);

        } catch (Exception e) {

            e.printStackTrace();

            return new Font("Arial", Font.BOLD, (int) size);
        }
    }
    
    private void setupMenuButton(JButton button, String path) {

    ImageIcon originalIcon = new ImageIcon(
            getClass().getResource(path)
    );

    int size = 60;

    // NORMAL
    Image normalImg = originalIcon.getImage()
            .getScaledInstance(
                    size,
                    size,
                    Image.SCALE_SMOOTH
            );

    // HOVER
    Image hoverImg = originalIcon.getImage()
            .getScaledInstance(
                    50,
                    50,
                    Image.SCALE_SMOOTH
            );

    ImageIcon normalIcon = new ImageIcon(normalImg);
    ImageIcon hoverIcon = new ImageIcon(hoverImg);

    button.setIcon(normalIcon);

    // STYLE
    button.setBorderPainted(false);
    button.setContentAreaFilled(false);
    button.setFocusPainted(false);
    button.setOpaque(false);

    // HOVER EFFECT
    button.addMouseListener(new java.awt.event.MouseAdapter() {

        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            button.setIcon(hoverIcon);
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            button.setIcon(normalIcon);
        }
    });
}
    
   public void setGameMessage(String message) {

    this.gameMessage = message;

    repaint();
} 
    
}