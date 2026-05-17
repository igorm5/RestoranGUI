/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.gui;

import javax.swing.*;
import java.awt.*;

import org.controller.GamePanel;

public class MainFrame extends JFrame {

    CardLayout cardLayout = new CardLayout();
    JPanel container = new JPanel(cardLayout);
    

    GamePanel gp = new GamePanel();

    public MainFrame() {

        setTitle("Restaurant Tycoon");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        container.add(new Start(this), "menu");
        container.add(gp, "restoran");

        add(container);

        cardLayout.show(container, "menu");

        pack();

        setLocationRelativeTo(null);

        setResizable(false);

        setVisible(true);

        gp.startGameThread();
    }

    public void showPanel(String name) {
        cardLayout.show(container, name);
    }

    public GamePanel getGamePanel() {
        return gp;
    }
}