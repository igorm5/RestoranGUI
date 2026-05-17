package org.gui;

import javax.swing.*;
import java.awt.*;

public class Start extends JPanel {

    private Image backgroundImage;

    public Start(MainFrame frame) {

        setLayout(null);

        // BACKGROUND
        backgroundImage = new ImageIcon(
                getClass().getResource("/background/menupanel.jpeg")
        ).getImage();

        // TITLE
        JLabel title = new JLabel("RESTAURANT TYCOON");
        title.setForeground(Color.WHITE);
        title.setFont(loadFont(36f));
        title.setBounds(70, 180, 1000, 80);
        add(title);

        // START BUTTON
        JButton startBtn = new JButton();

        setupImageButton(startBtn, "/buttons/start.png");

        startBtn.setBounds(275, 250, 220, 80);

        startBtn.addActionListener(e -> {
            frame.getCtrl().newGame();
        });

        add(startBtn);

        // LOAD BUTTON
        JButton loadBtn = new JButton();

        setupImageButton(loadBtn, "/buttons/load.png");

        loadBtn.setBounds(275, 270, 220, 160);

        loadBtn.addActionListener(e -> {
            frame.getCtrl().loadGame();
        });

        add(loadBtn);
    }

    private void setupImageButton(JButton button, String path) {

        ImageIcon originalIcon = new ImageIcon(
                getClass().getResource(path)
        );

        int targetWidth = 220;

        int originalW = originalIcon.getIconWidth();
        int originalH = originalIcon.getIconHeight();

        int targetHeight =
                (int) ((double) originalH / originalW * targetWidth);

        // NORMAL IMAGE
        Image normalImg = originalIcon.getImage()
                .getScaledInstance(
                        targetWidth,
                        targetHeight,
                        Image.SCALE_SMOOTH
                );

        // HOVER IMAGE
        Image hoverImg = originalIcon.getImage()
                .getScaledInstance(
                        (int) (targetWidth * 0.9),
                        (int) (targetHeight * 0.9),
                        Image.SCALE_SMOOTH
                );

        ImageIcon normalIcon = new ImageIcon(normalImg);
        ImageIcon hoverIcon = new ImageIcon(hoverImg);

        button.setIcon(normalIcon);

        // STYLE BUTTON
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

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (backgroundImage != null) {

            g.drawImage(
                    backgroundImage,
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    this
            );
        }
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
}