package org.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import org.controller.GameController;
import org.example.Charm;

public class JimatScene extends JPanel {

    private GameController ctrl;
    private JLabel   lblMoney;
    private JTextArea logArea;
    private JTextArea activeArea;

    private static final String[] JIMAT_TYPES = { "Charming", "Security", "Cleaner" };
    private static final String[] JIMAT_DESC  = {
        "Charming  — Pelanggan kadang beri tips ekstra",
        "Security  — Kurangi peluang pembeli kabur",
        "Cleaner   — Kurangi peluang tikus menyerang"
    };
    private static final int HARGA_JIMAT = 30000;

    public JimatScene(GameController ctrl) {
        this.ctrl = ctrl;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(20, 10, 30));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        buildUI();
    }

    private void buildUI() {
        // HEADER
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        header.setOpaque(false);
        lblMoney = makeLabel("Uang: Rp0", Color.GREEN, 14);
        header.add(lblMoney);
        JButton btnBack = makeButton("Kembali ke Dapur", new Color(80, 80, 80));
        btnBack.addActionListener(e -> ctrl.mainFrame().showPanel("dapur"));
        header.add(btnBack);
        add(header, BorderLayout.NORTH);

        // CENTER: pilih jimat
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(15, 8, 25));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10, 10, 10, 10);
        gc.fill = GridBagConstraints.HORIZONTAL;

        JLabel judul = makeLabel("=== TOKO JIMAT ===", new Color(200, 150, 255), 16);
        gc.gridx=0; gc.gridy=0; gc.gridwidth=3; center.add(judul, gc);
        gc.gridwidth=1;

        gc.gridy=1; gc.gridx=0; center.add(makeLabel("Jimat", Color.ORANGE, 13), gc);
        gc.gridx=1; center.add(makeLabel("Efek", Color.ORANGE, 13), gc);
        gc.gridx=2; center.add(makeLabel("Harga", Color.ORANGE, 13), gc);
        gc.gridx=3; center.add(makeLabel("Beli", Color.ORANGE, 13), gc);

        for (int i = 0; i < JIMAT_TYPES.length; i++) {
            final int idx = i;
            gc.gridy = i + 2;
            gc.gridx=0; center.add(makeLabel(JIMAT_DESC[i], Color.WHITE, 12), gc);
            gc.gridx=1; center.add(makeLabel("Acak (0–50%)", new Color(200,200,200), 12), gc);
            gc.gridx=2; center.add(makeLabel("Rp" + HARGA_JIMAT, Color.YELLOW, 12), gc);
            gc.gridx=3;
            JButton btn = makeButton("Beli", new Color(100, 50, 150));
            btn.addActionListener(e -> handleBeli(idx));
            center.add(btn, gc);
        }

        JScrollPane scroll = new JScrollPane(center);
        scroll.setOpaque(false); scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(150,80,220)));
        add(scroll, BorderLayout.CENTER);

        // BOTTOM: jimat aktif + log
        JPanel bottom = new JPanel(new GridLayout(1, 2, 10, 0));
        bottom.setOpaque(false);

        activeArea = new JTextArea(5, 30);
        activeArea.setEditable(false);
        activeArea.setBackground(new Color(10,5,20));
        activeArea.setForeground(Color.CYAN);
        activeArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane activeScroll = new JScrollPane(activeArea);
        activeScroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(150,80,220)),
            "Jimat Aktif", 0, 0, new Font("Arial",Font.BOLD,11), new Color(200,150,255)));
        bottom.add(activeScroll);

        logArea = new JTextArea(5, 30);
        logArea.setEditable(false);
        logArea.setBackground(new Color(10,5,20));
        logArea.setForeground(Color.WHITE);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(150,80,220)),
            "Log", 0, 0, new Font("Arial",Font.BOLD,11), Color.ORANGE));
        bottom.add(logScroll);

        add(bottom, BorderLayout.SOUTH);
    }

    private void handleBeli(int idx) {
        boolean ok = ctrl.beliJimat(JIMAT_TYPES[idx]);
        log(ok ? "Membeli " + JIMAT_TYPES[idx] + " Charm (-Rp" + HARGA_JIMAT + ")"
               : "Uang tidak cukup!");
    }

    public void refresh(int uang, List<Charm> activeCharms) {
        lblMoney.setText("Uang: Rp" + uang);
        activeArea.setText("");
        if (activeCharms.isEmpty()) {
            activeArea.append("(belum ada jimat aktif)\n");
        } else {
            for (Charm c : activeCharms) {
                activeArea.append("• " + c.getName() +
                    String.format(" (%.1f%%)\n", c.getEffectPercentage()));
            }
        }
        repaint();
    }

    private void log(String msg) {
        logArea.append(msg + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    private JLabel makeLabel(String t, Color c, int s) {
        JLabel l = new JLabel(t); l.setForeground(c); l.setFont(new Font("Arial", Font.BOLD, s)); return l;
    }

    private JButton makeButton(String t, Color bg) {
        JButton b = new JButton(t);
        b.setBackground(bg); b.setForeground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 12));
        b.setFocusPainted(false); b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }
}
