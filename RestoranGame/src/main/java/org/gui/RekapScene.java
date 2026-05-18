package org.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import org.controller.GameController;

public class RekapScene extends JPanel {

    private GameController ctrl;

    private JLabel lblHari, lblPendapatan, lblKerugian, lblBasi, lblSaldo;
    private JTextArea logArea;

    public RekapScene(GameController ctrl) {
        this.ctrl = ctrl;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(10, 20, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buildUI();
    }

    private void buildUI() {
        // JUDUL
        JLabel judul = makeLabel("REKAP AKHIR HARI", new Color(255, 220, 80), 22);
        judul.setHorizontalAlignment(SwingConstants.CENTER);
        add(judul, BorderLayout.NORTH);

        // TENGAH: ringkasan
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(15, 25, 15));
        center.setBorder(BorderFactory.createLineBorder(new Color(100, 200, 100), 2));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10, 20, 10, 20);
        gc.anchor = GridBagConstraints.WEST;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 2;

        lblHari = makeLabel("Hari ke-1", Color.YELLOW, 12);
        lblPendapatan = makeLabel("Pendapatan : Rp0", Color.GREEN, 10);
        lblKerugian = makeLabel("Kerugian bencana: Rp0", new Color(255, 100, 100), 10);
        lblBasi = makeLabel("Bahan basi dibuang: Rp0", new Color(255, 150, 50), 10);
        lblSaldo = makeLabel("Saldo akhir  : Rp0", Color.WHITE, 12);

        JLabel[] rows = { lblHari, lblPendapatan, lblKerugian, lblBasi,
                makeLabel("======================", new Color(100, 200, 100), 12), lblSaldo };
        for (int i = 0; i < rows.length; i++) {
            gc.gridy = i;
            center.add(rows[i], gc);
        }

        add(center, BorderLayout.CENTER);

        // BAWAH: log harian + tombol lanjut
        JPanel bottom = new JPanel(new BorderLayout(8, 8));
        bottom.setOpaque(false);

        logArea = new JTextArea(8, 50);
        logArea.setEditable(false);
        logArea.setBackground(new Color(5, 12, 5));
        logArea.setForeground(Color.WHITE);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 200, 100)),
                "Log Hari Ini", 0, 0, new Font("Arial", Font.BOLD, 11), Color.GREEN));
        bottom.add(logScroll, BorderLayout.CENTER);

        JButton btnLanjut = makeButton("Lanjut ke Hari Berikutnya ▶", new Color(60, 160, 60));
        btnLanjut.setFont(new Font("Monospaced", Font.BOLD, 14));
        btnLanjut.setPreferredSize(new Dimension(300, 50));
        btnLanjut.addActionListener(e -> ctrl.nextDay());
        bottom.add(btnLanjut, BorderLayout.EAST);

        add(bottom, BorderLayout.SOUTH);
    }

    /** Dipanggil GameController saat rekap dimulai */
    public void update(int hari, int pendapatan, int kerugian, int basi, int saldo, List<String> log) {
        lblHari.setText("Hari ke-" + hari);
        lblPendapatan.setText("Pendapatan     : Rp" + pendapatan);
        lblKerugian.setText("Kerugian bencana: Rp" + kerugian);
        lblBasi.setText("Bahan basi dibuang: Rp" + basi);
        lblSaldo.setText("Saldo akhir    : Rp" + saldo);

        logArea.setText("");
        for (String line : log)
            logArea.append(line + "\n");
        logArea.setCaretPosition(0);
        repaint();
    }

    private JLabel makeLabel(String t, Color c, int s) {
        JLabel l = new JLabel(t);
        l.setForeground(c);
        l.setFont(FontUtil.loadFont((float) s));
        return l;
    }

    private JButton makeButton(String t, Color bg) {
        JButton b = new JButton(t);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 12));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }
}