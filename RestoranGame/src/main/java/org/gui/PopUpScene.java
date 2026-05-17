package org.gui;

import javax.swing.*;

public class PopUpScene {
    public static void tikus(JFrame parent) {
        JOptionPane.showMessageDialog(parent,
                "🐀 Tikus menyerang dapur! Stok berkurang!",
                "Bencana Tikus",
                JOptionPane.WARNING_MESSAGE);
    }

    public static void pelanggankabur(JFrame parent) {
        JOptionPane.showMessageDialog(parent,
                "💨 Seorang pelanggan kabur!",
                "Pelanggan Kabur",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
