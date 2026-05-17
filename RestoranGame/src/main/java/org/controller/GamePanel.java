/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.controller;

///**
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Image;
//import java.awt.Toolkit;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import javax.swing.ImageIcon;
//import javax.swing.JPanel;
//import javax.swing.Timer;
//
//public class GamePanel extends JPanel {
//
//    // Screen settings
//    final int originalTileSize = 16;
//    final int scale = 3;
//
//    public final int tileSize = originalTileSize * scale;
//
//    final int maxScreenCol = 16;
//    final int maxScreenRow = 12;
//
//    public final int screenWidth = tileSize * maxScreenCol;
//    final int screenHeight = tileSize * maxScreenRow;
//
//    private Timer gameTimer;
//
//    // Shake effect
//    public boolean isShaking = false;
//    private int shakeDuration = 0;
//    private int shakeOffsetX = 0;
//    private int shakeOffsetY = 0;
//
//    // Timer game
//    public int detikBerjalan = 0;
//    private int frameCounter = 0;
//
//    public GamePanel() {
//        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
//        this.setBackground(Color.black);
//        this.setDoubleBuffered(true);
//        this.setFocusable(true);
//        
//        // Inisialisasi Timer (berjalan setiap 16ms = ~60 FPS)
//        gameTimer = new Timer(16, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                update();
//                repaint();
//                
//                // Logika menghitung detik
//                frameCounter++;
//                if (frameCounter >= 60) { // Jika sudah 60 frame (1 detik)
//                    detikBerjalan++;
//                    frameCounter = 0;
//                }
//            }
//        });
//    }
//
//    public void startGameThread() {
//        if (gameTimer.isRunning()) return;
//        gameTimer.start();
//    }
//    
//    public void stopGameThread() {
//        gameTimer.stop();
//    }
//
//    public void update() {
//
//        // update shake effect
//        if (isShaking) {
//
//            shakeOffsetX = (int)(Math.random() * 10 - 5);
//            shakeOffsetY = (int)(Math.random() * 10 - 5);
//
//            shakeDuration--;
//
//            if (shakeDuration <= 0) {
//
//                isShaking = false;
//
//                shakeOffsetX = 0;
//                shakeOffsetY = 0;
//            }
//        }
//    }
//
//    @Override
//    public void paintComponent(Graphics g) {
//
//        super.paintComponent(g);
//
//        Graphics2D g2 = (Graphics2D) g;
//
//        // efek shake
//        g2.translate(shakeOffsetX, shakeOffsetY);
//
//        // background sementara
//        g2.setColor(new Color(40, 40, 40));
//        g2.fillRect(100, 200, 16, 16);
//
//        // contoh lantai restoran
//        g2.setColor(new Color(90, 90, 90));
//
//        for (int i = 0; i < screenWidth; i += tileSize) {
//
//            for (int j = 0; j < screenHeight; j += tileSize) {
//
//                g2.drawRect(i, j, tileSize, tileSize);
//            }
//        }
//
//        // meja contoh
//        g2.setColor(new Color(139, 69, 19));
//        g2.fillRect(300, 200, 100, 60);
//
//        // kasir contoh
//        g2.setColor(Color.YELLOW);
//        g2.fillRect(100, 200, 40, 40);
//
//        // tulisan waktu
//        g2.setColor(Color.WHITE);
//
//        g2.drawString("Restaurant Tycoon", 20, 30);
//        g2.drawString("Waktu: " + detikBerjalan + " detik", 20, 50);
//
//        g2.dispose();
//    }
//}**/