package org.gui;

/**
 *
 * @author ASUS
 */
import javax.swing.*;
import java.awt.*;

public class JimatScene extends JPanel {

    public JimatScene() {

        setLayout(null);
        setBackground(new Color(30, 25, 40));

        JLabel imageLabel = new JLabel();

        ImageIcon img = new ImageIcon(
                getClass().getResource("/lainnya/bedeng.png")
        );

        Image scaled = img.getImage()
                .getScaledInstance(600, 200, Image.SCALE_SMOOTH);

        imageLabel.setIcon(new ImageIcon(scaled));
        imageLabel.setBounds(100, 20, 600, 200);

        add(imageLabel);

        JPanel buttonRow = new JPanel();
        buttonRow.setLayout(new GridLayout(1, 3, 20, 0));
        buttonRow.setBounds(60, 240, 680, 180);
        buttonRow.setOpaque(false);

        JButton b1 = createJimatCard(
                "CHARMING",
                "Rp 15.000",
                "/lainnya/charming.png"
        );

        JButton b2 = createJimatCard(
                "SECURITY",
                "Rp 20.000",
                "/lainnya/security.png"
        );

        JButton b3 = createJimatCard(
                "CLEANER",
                "Rp 30.000",
                "/lainnya/cleaner.png"
        );

        b1.addActionListener(e -> {

            PopUpScene.beliJimat(this);

            System.out.println("Membeli Charming Jimat!");

        });

        b2.addActionListener(e -> {

            PopUpScene.beliJimat(this);

            System.out.println("Membeli Security Jimat!");

        });

        b3.addActionListener(e -> {

            PopUpScene.beliJimat(this);

            System.out.println("Membeli Cleaner Jimat!");

        });

        buttonRow.add(b1);
        buttonRow.add(b2);
        buttonRow.add(b3);

        add(buttonRow);

        JButton beliBtn = new JButton();
        beliBtn.setBounds(180, 430, 180, 80);

        JButton nextBtn = new JButton();
        nextBtn.setBounds(430, 430, 180, 80);

        setupImageButton(nextBtn, "/buttons/next.png");

        nextBtn.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    null,
                    "Next"
            );

        });

        add(nextBtn);
    }

    private void setupImageButton(JButton button, String path) {

        ImageIcon originalIcon = new ImageIcon(
                getClass().getResource(path)
        );

        int targetWidth = 220;

        int originalW = originalIcon.getIconWidth();
        int originalH = originalIcon.getIconHeight();

        int targetHeight
                = (int) ((double) originalH / originalW * targetWidth);

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

    private JButton createJimatCard(
            String nama,
            String harga,
            String path
    ) {

        JButton button = new JButton();
        button.setLayout(new BorderLayout());

        // WARNA CARD
        button.setBackground(new Color(60, 40, 80));

        // OUTLINE KUNING
        button.setBorder(BorderFactory.createLineBorder(
                new Color(255, 200, 80), 4));

        button.setFocusPainted(false);

        ImageIcon icon = new ImageIcon(
                getClass().getResource(path)
        );

        Image normalImg = icon.getImage()
                .getScaledInstance(90, 90, Image.SCALE_SMOOTH);

        Image hoverImg = icon.getImage()
                .getScaledInstance(80, 80, Image.SCALE_SMOOTH);

        ImageIcon normalIcon = new ImageIcon(normalImg);
        ImageIcon hoverIcon = new ImageIcon(hoverImg);

        JLabel imageLabel = new JLabel(normalIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel textLabel = new JLabel(
                "<html><center>"
                + nama
                + "<br>"
                + harga
                + "</center></html>"
        );

        textLabel.setHorizontalAlignment(SwingConstants.CENTER);

        textLabel.setForeground(Color.WHITE);

        textLabel.setFont(new Font(
                "Monospaced",
                Font.BOLD,
                16
        ));

        button.add(imageLabel, BorderLayout.CENTER);
        button.add(textLabel, BorderLayout.SOUTH);
        textLabel.setBorder(
                BorderFactory.createEmptyBorder(10, 0, 20, 0)
        );

        button.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                imageLabel.setIcon(hoverIcon);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                imageLabel.setIcon(normalIcon);
            }
        });

        return button;
    }
}
