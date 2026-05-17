package org.gui;

/**
 *
 * @author ASUS
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.controller.GameController;
import org.example.Sellable;

public class DapurScene extends JPanel {

    private GameController ctrl;
    private JLabel lblMoney;
    private JLabel lblStock;
    private JLabel lblSelectedIngredient;

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

    public DapurScene(GameController ctrl) {
        this.ctrl = ctrl;

        setLayout(null);
        setBackground(new Color(30, 20, 10));

        lblMoney = createInfoLabel("Uang: Rp0", 450, 10);
        lblStock = createInfoLabel("Stok: 0", 450, 30);
        lblSelectedIngredient = createInfoLabel("Bahan terpilih: -", 450, 50);
        add(lblMoney);
        add(lblStock);
        add(lblSelectedIngredient);

        JButton priceBtn = makeActionButton("Atur Harga Menu", 420, 250, 200, 40, new Color(120, 80, 180));
        priceBtn.addActionListener(e -> adjustMenuPrice());
        add(priceBtn);
        

        JButton upgradeBtn = new JButton();

        upgradeBtn.setBounds(300, 370, 180, 60);

        setupImageButton(upgradeBtn, "/buttons/upgrade.png");

        upgradeBtn.addActionListener(e -> {

            if (ctrl.upgradeLevel()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Restoran berhasil naik level!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Uang tidak cukup atau level sudah maksimal."
                );
            }
        });

        add(upgradeBtn);

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

        title.setFont(loadFont(20f));

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

        scrollPane.setBounds(20, 80, 620, 150);

        add(scrollPane);

        table.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                int row = table.getSelectedRow();

                if (row != -1) {

                    nama = namaBahan[row];
                    String harga = hargaBahan[row];

                    gameMessage
                            = nama + " dipilih dengan harga " + harga;

                    repaint();
                }
            }
        });

        tokoButton = new JButton("Ke Toko Jimat");

        tokoButton.setFont(loadFont(8f));

        tokoButton.setFocusPainted(false);

        tokoButton.setBackground(new Color(70, 70, 70));

        tokoButton.setForeground(Color.WHITE);

        tokoButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        tokoButton.setBounds(460, 400, 180, 40);

        tokoButton.addActionListener(e -> {
            ctrl.mainFrame().showPanel("jimat");
        });

        add(tokoButton);

        // Tombol Beli
        beli = new JButton();
        beli.setBounds(10, 370, 180, 60);
        setupImageButton(beli, "/buttons/beli.png");
        beli.addActionListener(e -> {
            buySelectedStock();
            System.out.println("Aksi: Membeli bahan " + (nama != null ? nama : "kosong"));
        });
        add(beli);

        // Tombol Mulai Hari
        mulaihari = new JButton();
        mulaihari.setBounds(150, 370, 180, 60);
        setupImageButton(mulaihari, "/buttons/mulaihari.png");
        mulaihari.addActionListener(e -> {
            ctrl.startSellingPhase();
        });
        add(mulaihari);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(0, 0, 0, 180));

        g2.fillRoundRect(
                20,
                310,
                600,
                60,
                20,
                20
        );

        // BORDER
        g2.setColor(Color.WHITE);

        g2.drawRoundRect(
                20,
                310,
                600,
                60,
                20,
                20
        );

        // TEXT
        g2.setFont(loadFont(10f));

        g2.drawString(
                gameMessage,
                40,
                345
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

        int targetWidth = 160;

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

    private JLabel createInfoLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(loadFont(10f));
        label.setBounds(x, y, 200, 30);
        return label;
    }

    private JButton makeActionButton(
            String text,
            int x,
            int y,
            int width,
            int height,
            Color color
    ) {

        JButton button = new JButton(text);

        button.setBounds(x, y, width, height);

        // WARNA COKLAT MUDA
        button.setBackground(new Color(201, 145, 87));

        button.setForeground(Color.WHITE);

        // FONT PIXEL
        button.setFont(loadFont(10f));

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(120, 70, 30),
                        3
                )
        );

        return button;
    }

    private void buySelectedStock() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih bahan yang ingin dibeli terlebih dahulu.");
            return;
        }

        String bahan = namaBahan[row];
        String hargaText = hargaBahan[row].replace("Rp", "").replace(".", "").trim();
        int hargaPerSepuluh;
        try {
            hargaPerSepuluh = Integer.parseInt(hargaText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga bahan tidak valid.");
            return;
        }

        String jumlahInput = JOptionPane.showInputDialog(
                this,
                "Masukkan jumlah paket (1 paket = 10 unit):",
                "Beli Bahan",
                JOptionPane.PLAIN_MESSAGE
        );

        if (jumlahInput == null || jumlahInput.isBlank()) {
            return;
        }

        try {
            int paket = Integer.parseInt(jumlahInput.trim());
            if (paket <= 0) {
                JOptionPane.showMessageDialog(this, "Jumlah paket harus lebih besar dari 0.");
                return;
            }

            int quantity = paket * 10;
            int unitPrice = Math.max(1, hargaPerSepuluh / 10);
            boolean purchased = ctrl.beliStok(bahan, quantity, unitPrice);
            if (purchased) {
                JOptionPane.showMessageDialog(this, "Berhasil membeli " + quantity + " " + bahan + "!");
                lblSelectedIngredient.setText("Bahan terpilih: " + bahan);
            } else {
                JOptionPane.showMessageDialog(this, "Uang tidak cukup untuk membeli " + bahan + ".");
            }
            ctrl.refreshHUD();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan angka valid untuk jumlah paket.");
        }
    }

    private void adjustMenuPrice() {
        java.util.List<Sellable> menu = ctrl.getCurrentMenu();
        if (menu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Menu belum tersedia.");
            return;
        }

        String[] options = menu.stream().map(Sellable::getName).toArray(String[]::new);
        int selected = JOptionPane.showOptionDialog(
                this,
                "Pilih menu yang ingin diubah harganya:",
                "Atur Harga Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (selected < 0) {
            return;
        }

        String currentPrice = String.valueOf((int) menu.get(selected).getPrice());
        String input = JOptionPane.showInputDialog(
                this,
                "Masukkan harga baru untuk " + menu.get(selected).getName() + ":",
                currentPrice
        );
        if (input == null || input.isBlank()) {
            return;
        }

        try {
            double hargaBaru = Double.parseDouble(input.trim());
            if (hargaBaru <= 0) {
                JOptionPane.showMessageDialog(this, "Harga harus lebih besar dari 0.");
                return;
            }
            ctrl.setMenuPrice(selected, hargaBaru);
            JOptionPane.showMessageDialog(this, "Harga " + menu.get(selected).getName() + " diset menjadi Rp" + (int) hargaBaru + ".");
            ctrl.refreshHUD();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan angka valid untuk harga.");
        }
    }

    public void refresh(
            int uang,
            int stok,
            Map<String, Integer> inventory,
            List<org.example.Sellable> menu
    ) {
        lblMoney.setText("Uang: Rp" + uang);
        lblStock.setText("Stok: " + stok);
        repaint();
    }
}
