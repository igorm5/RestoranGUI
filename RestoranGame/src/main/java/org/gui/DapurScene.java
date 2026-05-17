package org.gui;

/**
 *
 * @author ASUS
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.gui.*;

public class DapurScene extends JPanel {
    
    String nama;

    JTable table;

    BufferedImage[] images;

    String[] namaBahan = {
        "Ayam",
        "Beras",
        "Galon",
        "Gula",
        "Jambu",
        "Kentang",
        "Kol",
        "Melon",
        "Minyak",
        "Paprika",
        "Sawi",
        "Susu",
        "Tomat",
        "Kopi"
    };

    String[] hargaBahan = {
        "Rp2.000",
        "Rp2.000",
        "Rp2.000",
        "Rp1.000",
        "Rp1.000",
        "Rp1.000",
        "Rp0.000",
        "Rp2.000",
        "Rp3.000",
        "Rp1.000",
        "Rp1.000",
        "Rp1.000",
        "Rp1.000",
        "Rp1.000"
    };

    JButton tokoButton;
    JButton mulaihari;
    JButton beli;
    String gameMessage = "Silakan pilih bahan yang tersedia di dapur.";

    public DapurScene() {

        setLayout(null);
        setBackground(new Color(30, 20, 10));

        images = new BufferedImage[14];

        try {

            images[0] = ImageIO.read(getClass().getResourceAsStream("/bahan/ayam.png"));
            images[1] = ImageIO.read(getClass().getResourceAsStream("/bahan/beras.png"));
            images[2] = ImageIO.read(getClass().getResourceAsStream("/bahan/galon.png"));
            images[3] = ImageIO.read(getClass().getResourceAsStream("/bahan/gula.png"));
            images[4] = ImageIO.read(getClass().getResourceAsStream("/bahan/jambu.png"));
            images[5] = ImageIO.read(getClass().getResourceAsStream("/bahan/kentang.png"));
            images[6] = ImageIO.read(getClass().getResourceAsStream("/bahan/kol.png"));
            images[7] = ImageIO.read(getClass().getResourceAsStream("/bahan/melon.png"));
            images[8] = ImageIO.read(getClass().getResourceAsStream("/bahan/minyak.png"));
            images[9] = ImageIO.read(getClass().getResourceAsStream("/bahan/paprika.png"));
            images[10] = ImageIO.read(getClass().getResourceAsStream("/bahan/sawi.png"));
            images[11] = ImageIO.read(getClass().getResourceAsStream("/bahan/susu.png"));
            images[12] = ImageIO.read(getClass().getResourceAsStream("/bahan/tomat.png"));
            images[13] = ImageIO.read(getClass().getResourceAsStream("/bahan/kopi.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel title = new JLabel("FASE PERSIAPAN");

        title.setFont(loadFont(30f));

        title.setForeground(Color.WHITE);

        title.setBounds(20, 20, 1000, 40);

        add(title);

        String[] column = {
            "Gambar",
            "Nama Bahan",
            "Harga (persepuluh)"
        };

        Object[][] data = new Object[14][3];

        for (int i = 0; i < 14; i++) {

            BufferedImage img = images[i];

            int newHeight = 30;

            int newWidth = (img.getWidth() * newHeight) / img.getHeight();

            data[i][0] = new ImageIcon(
                    img.getScaledInstance(
                            newWidth,
                            newHeight,
                            Image.SCALE_SMOOTH
                    )
            );

            data[i][1] = namaBahan[i];

            data[i][2] = hargaBahan[i];
        }

        DefaultTableModel model = new DefaultTableModel(data, column) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int column) {

                if (column == 0) {
                    return Icon.class;
                }

                return String.class;
            }
        };

        table = new JTable(model);

        table.setRowHeight(40);

        table.setFont(loadFont(14f));

        table.getTableHeader().setFont(loadFont(15f));

        table.getTableHeader().setBackground(new Color(50, 50, 50));

        table.getTableHeader().setForeground(Color.WHITE);

        table.setBackground(new Color(40, 40, 40));

        table.setForeground(Color.WHITE);

        table.setGridColor(Color.GRAY);

        table.setSelectionBackground(new Color(90, 90, 90));

        // CENTER TEXT
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();

        center.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(1).setCellRenderer(center);

        table.getColumnModel().getColumn(2).setCellRenderer(center);

        // CENTER IMAGE
        table.getColumnModel().getColumn(0).setCellRenderer(
                new TableCellRenderer() {

            JLabel label = new JLabel();

            @Override
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column
            ) {

                label.setHorizontalAlignment(SwingConstants.CENTER);

                label.setIcon((Icon) value);

                label.setOpaque(true);

                if (isSelected) {

                    label.setBackground(new Color(90, 90, 90));

                } else {

                    label.setBackground(new Color(40, 40, 40));
                }

                return label;
            }
        }
        );

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setBounds(90, 80, 620, 300);

        add(scrollPane);
        
        table.addMouseListener(new java.awt.event.MouseAdapter() {

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {

        int row = table.getSelectedRow();

        if (row != -1) {

            nama = namaBahan[row];
            String harga = hargaBahan[row];

            gameMessage =
                    nama + " dipilih dengan harga " + harga;

            repaint();
        }
    }
});

        tokoButton = new JButton("Ke Toko Jimat");

        tokoButton.setFont(loadFont(14f));

        tokoButton.setFocusPainted(false);

        tokoButton.setBackground(new Color(70, 70, 70));

        tokoButton.setForeground(Color.WHITE);

        tokoButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        tokoButton.setBounds(550, 500, 200, 40);

        add(tokoButton);

        mulaihari = new JButton();

        mulaihari.setBounds(280, 490, 220, 60);

        setupImageButton(
                mulaihari,
                "/buttons/mulaihari.png"
        );

// ACTION
        mulaihari.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    null,
                    "Hari Dimulai!"
            );

        });

        add(mulaihari);

    
    
    beli = new JButton();

        beli.setBounds(90, 490, 220, 60);

        setupImageButton(
                beli,
                "/buttons/beli.png"
        );

// ACTION
        beli.addActionListener(e -> {
            
            PopUpScene.beliStok(this);

            System.out.println("Membeli bahan " + nama);

        });

        add(beli);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(0, 0, 0, 180));

        g2.fillRoundRect(
                40,
                410,
                720,
                60,
                20,
                20
        );

        // BORDER
        g2.setColor(Color.WHITE);

        g2.drawRoundRect(
                40,
                410,
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
        445
);
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
}
