/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.main;

/**
 *
 * @author ASUS
 */


import javax.swing.*;
import org.gui.*;

public class MainTest {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Menu Test");

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setSize(800, 600);

            frame.setLocationRelativeTo(null);

            frame.setContentPane(new Level5Scene());

            frame.setVisible(true);
        });
    }
}
