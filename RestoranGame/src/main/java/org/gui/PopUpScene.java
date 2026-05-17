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

public class PopUpScene {

    public static void tikus(Component parent) {

        // PANEL UTAMA
        JDialog dialog = new JDialog();

        dialog.setUndecorated(true);

        dialog.setBackground(new Color(0, 0, 0, 0));

        dialog.setSize(350, 250);

        dialog.setLocationRelativeTo(parent);

        // BACKGROUND
        JPanel panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                // BACKGROUND
                g2.setColor(new Color(0, 0, 0));

                g2.fillRoundRect(
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        40,
                        40
                );

                // OUTLINE
                g2.setColor(Color.WHITE);

                g2.setStroke(new BasicStroke(5));

                g2.drawRoundRect(
                        2,
                        2,
                        getWidth() - 5,
                        getHeight() - 5,
                        40,
                        40
                );
            }
        };

        panel.setOpaque(false);

        panel.setLayout(null);

        // GAMBAR
        ImageIcon icon = new ImageIcon(
                PopUpScene.class.getResource("/lainnya/tikus.png")
        );

        Image img = icon.getImage().getScaledInstance(
                250,
                180,
                Image.SCALE_SMOOTH
        );

        JLabel imageLabel = new JLabel(new ImageIcon(img));

        imageLabel.setBounds(115, 40, 120, 120);

        panel.add(imageLabel);

        // TEXT
        JLabel text = new JLabel(
                "Tikus Menyerang!",
                SwingConstants.CENTER
        );

        text.setForeground(Color.WHITE);

        text.setFont(loadFont(18f));

        text.setBounds(25, 160, 300, 40);

        panel.add(text);

        dialog.add(panel);

        dialog.setVisible(true);

        // AUTO CLOSE 3 DETIK
        Timer timer = new Timer(10000, e -> {
            dialog.dispose();
        });

        timer.setRepeats(false);

        timer.start();
    }
    
    public static void pelanggankabur(Component parent) {

        // PANEL UTAMA
        JDialog dialog = new JDialog();

        dialog.setUndecorated(true);

        dialog.setBackground(new Color(0, 0, 0, 0));

        dialog.setSize(350, 250);

        dialog.setLocationRelativeTo(parent);

        // BACKGROUND
        JPanel panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                // BACKGROUND
                g2.setColor(new Color(0, 0, 0));

                g2.fillRoundRect(
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        40,
                        40
                );

                // OUTLINE
                g2.setColor(Color.WHITE);

                g2.setStroke(new BasicStroke(5));

                g2.drawRoundRect(
                        2,
                        2,
                        getWidth() - 5,
                        getHeight() - 5,
                        40,
                        40
                );
            }
        };

        panel.setOpaque(false);

        panel.setLayout(null);
        
        // GAMBAR
        ImageIcon icon = new ImageIcon(
                PopUpScene.class.getResource("/lainnya/orang.png")
        );

        Image img = icon.getImage().getScaledInstance(
                250,
                250,
                Image.SCALE_SMOOTH
        );

        JLabel imageLabel = new JLabel(new ImageIcon(img));

        imageLabel.setBounds(115, 40, 120, 120);

        panel.add(imageLabel);


        // TEXT
        JLabel text = new JLabel(
                "PELANGGAN KABUR!",
                SwingConstants.CENTER
        );

        text.setForeground(Color.WHITE);

        text.setFont(loadFont(18f));

        text.setBounds(25, 160, 300, 40);

        panel.add(text);

        dialog.add(panel);

        dialog.setVisible(true);

        // AUTO CLOSE 3 DETIK
        Timer timer = new Timer(10000, e -> {
            dialog.dispose();
        });

        timer.setRepeats(false);

        timer.start();
    }

    public static void beliStok(Component parent) {

        // DIALOG
        JDialog dialog = new JDialog();

        dialog.setUndecorated(true);

        dialog.setBackground(new Color(0, 0, 0, 0));

        dialog.setSize(450, 250);

        dialog.setLocationRelativeTo(parent);

        // PANEL
        JPanel panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                // BACKGROUND
                g2.setColor(new Color(0, 0, 0));

                g2.fillRoundRect(
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        40,
                        40
                );

                // OUTLINE
                g2.setColor(Color.WHITE);

                g2.setStroke(new BasicStroke(5));

                g2.drawRoundRect(
                        2,
                        2,
                        getWidth() - 5,
                        getHeight() - 5,
                        40,
                        40
                );
            }
        };

        panel.setOpaque(false);

        panel.setLayout(null);

        // TEXT
        JLabel text = new JLabel(
                "BELI JIMAT?",
                SwingConstants.CENTER
        );

        text.setForeground(Color.WHITE);

        text.setFont(loadFont(18f));

        text.setBounds(40, 40, 370, 40);

        panel.add(text);

        JButton yesBtn = new JButton();

        setupImageButton(
                yesBtn,
                "/buttons/beli.png"
        );

        yesBtn.setBounds(70, 120, 130, 60);

        yesBtn.addActionListener(e -> {

            System.out.println("YES");

            dialog.dispose();
        });

        panel.add(yesBtn);

        JButton noBtn = new JButton();

        setupImageButton(
                noBtn,
                "/buttons/batal.png"
        );

        noBtn.setBounds(250, 120, 130, 60);

        noBtn.addActionListener(e -> {

            System.out.println("NO");

            dialog.dispose();
        });

        panel.add(noBtn);

        dialog.add(panel);

        dialog.setVisible(true);
    }

    

    private static void setupImageButton(
            JButton button,
            String path
    ) {

        ImageIcon originalIcon = new ImageIcon(
                PopUpScene.class.getResource(path)
        );

        int width = 130;
        int height = 60;

        // NORMAL
        Image normalImg = originalIcon.getImage()
                .getScaledInstance(
                        width,
                        height,
                        Image.SCALE_SMOOTH
                );

        // HOVER
        Image hoverImg = originalIcon.getImage()
                .getScaledInstance(
                        120,
                        55,
                        Image.SCALE_SMOOTH
                );

        ImageIcon normalIcon
                = new ImageIcon(normalImg);

        ImageIcon hoverIcon
                = new ImageIcon(hoverImg);

        button.setIcon(normalIcon);

        // STYLE
        button.setBorderPainted(false);

        button.setContentAreaFilled(false);

        button.setFocusPainted(false);

        button.setOpaque(false);

        // HOVER EFFECT
        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(
                    java.awt.event.MouseEvent evt
            ) {

                button.setIcon(hoverIcon);
            }

            @Override
            public void mouseExited(
                    java.awt.event.MouseEvent evt
            ) {

                button.setIcon(normalIcon);
            }
        });
    }

    // LOAD FONT
    private static Font loadFont(float size) {

        try {

            Font font = Font.createFont(
                    Font.TRUETYPE_FONT,
                    PopUpScene.class.getResourceAsStream(
                            "/fonts/PressStart.ttf"
                    )
            );

            return font.deriveFont(size);

        } catch (Exception e) {

            e.printStackTrace();

            return new Font(
                    "Arial",
                    Font.BOLD,
                    (int) size
            );
        }
    }

}
