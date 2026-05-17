/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.main;

/**
 *
 * @author ASUS
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


import javax.swing.JFrame;
import org.gui.*;
import org.controller.GameController;

public class MainTest {

    public static void main(String[] args) {

        JFrame frame = new JFrame();

        frame.setTitle("GUI TEST");

        frame.setSize(800, 600);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        frame.setResizable(false);

        // DUMMY MAINFRAME
        MainFrame dummyFrame = null;

        // CONTROLLER
        GameController ctrl = new GameController(dummyFrame);

        // TEST PANEL
        JimatScene panel = new JimatScene(ctrl);

        frame.add(panel);

        frame.setVisible(true);
    }
}