package org.gui;

/**
 *
 * @author ASUS
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.controller.GameController;
import org.example.Charm;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class JimatScene extends JPanel {

        private GameController ctrl;
        private JLabel lblMoney;
        private JLabel lblCharms;

        public JimatScene(GameController ctrl) {
                this.ctrl = ctrl;

                setLayout(null);
                setBackground(new Color(30, 25, 40));

                JLabel imageLabel = new JLabel();

                ImageIcon img = new ImageIcon(
                                getClass().getResource("/lainnya/bedeng.png"));

                Image scaled = img.getImage()
                                .getScaledInstance(400, 150, Image.SCALE_SMOOTH);

                imageLabel.setIcon(new ImageIcon(scaled));
                imageLabel.setBounds(110, 20, 400, 150);

                add(imageLabel);

                lblMoney = createInfoLabel("Uang: Rp0", 520, 50);
                lblCharms = createInfoLabel("Jimat aktif: 0", 520, 80);
                add(lblMoney);
                add(lblCharms);

                JPanel buttonRow = new JPanel();
                buttonRow.setLayout(new GridLayout(1, 3, 20, 0));
                buttonRow.setBounds(30, 180, 600, 180);
                buttonRow.setOpaque(false);

                JButton b1 = createJimatCard(
                                "CHARMING",
                                "Rp 15.000",
                                "/lainnya/charming.png");

                JButton b2 = createJimatCard(
                                "SECURITY",
                                "Rp 20.000",
                                "/lainnya/security.png");

                JButton b3 = createJimatCard(
                                "CLEANER",
                                "Rp 30.000",
                                "/lainnya/cleaner.png");

                b1.addActionListener(e -> buyCharm("Charming"));
                b2.addActionListener(e -> buyCharm("Security"));
                b3.addActionListener(e -> buyCharm("Cleaner"));

                buttonRow.add(b1);
                buttonRow.add(b2);
                buttonRow.add(b3);

                add(buttonRow);

                JButton backBtn = new JButton();
                backBtn.setBounds(120, 360, 180, 80);
                setupImageButton(backBtn, "/buttons/batal.png");
                backBtn.addActionListener(e -> ctrl.mainFrame().showPanel("dapur"));
                add(backBtn);

                JButton nextBtn = new JButton();
                nextBtn.setBounds(370, 360, 180, 80);
                setupImageButton(nextBtn, "/buttons/next.png");
                nextBtn.addActionListener(e -> ctrl.mainFrame().showPanel("dapur"));
                add(nextBtn);

                add(nextBtn);
        }

        private void setupImageButton(JButton button, String path) {

                ImageIcon originalIcon = new ImageIcon(
                                getClass().getResource(path));

                int targetWidth = 220;

                int originalW = originalIcon.getIconWidth();
                int originalH = originalIcon.getIconHeight();

                int targetHeight = (int) ((double) originalH / originalW * targetWidth);

                // NORMAL IMAGE
                Image normalImg = originalIcon.getImage()
                                .getScaledInstance(
                                                targetWidth,
                                                targetHeight,
                                                Image.SCALE_SMOOTH);

                // HOVER IMAGE
                Image hoverImg = originalIcon.getImage()
                                .getScaledInstance(
                                                (int) (targetWidth * 0.9),
                                                (int) (targetHeight * 0.9),
                                                Image.SCALE_SMOOTH);

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
                        String path) {

                JButton button = new JButton();
                button.setLayout(new BorderLayout());

                // WARNA CARD
                button.setBackground(new Color(60, 40, 80));

                // OUTLINE KUNING
                button.setBorder(BorderFactory.createLineBorder(
                                new Color(255, 200, 80), 4));

                button.setFocusPainted(false);

                ImageIcon icon = new ImageIcon(
                                getClass().getResource(path));

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
                                                + "</center></html>");

                textLabel.setHorizontalAlignment(SwingConstants.CENTER);

                textLabel.setForeground(Color.WHITE);

                textLabel.setFont(new Font(
                                "Monospaced",
                                Font.BOLD,
                                16));

                button.add(imageLabel, BorderLayout.CENTER);
                button.add(textLabel, BorderLayout.SOUTH);
                textLabel.setBorder(
                                BorderFactory.createEmptyBorder(10, 0, 20, 0));

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

        private JLabel createInfoLabel(String text, int x, int y) {
                JLabel label = new JLabel(text);
                label.setForeground(Color.WHITE);
                label.setFont(FontUtil.loadFont((float) 14));
                label.setBounds(x, y, 240, 30);
                return label;
        }

        private void buyCharm(String type) {
                if (ctrl.beliJimat(type)) {
                        JOptionPane.showMessageDialog(this, "Berhasil membeli Jimat " + type + "!");
                        // PopUpScene.beliJimat(this);
                } else {
                        JOptionPane.showMessageDialog(this, "Uang tidak cukup untuk membeli Jimat " + type + ".");
                }
                ctrl.refreshHUD();
        }

        public void refresh(int uang, List<org.example.Charm> charms) {
                lblMoney.setText("Uang: Rp" + uang);
                lblCharms.setText("Jimat aktif: " + charms.size());
                repaint();
        }
}
