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

            Level5Scene scene = new Level5Scene();

            frame.setContentPane(scene);

            frame.setVisible(true);

            // TEST POPUP
            //PopUpScene.pelanggankabur(scene);
            // TEST LAIN
            // Popup.notifStokHabis(scene);
            // Popup.notifLevelUp(scene);
            // Popup.notifUangKurang(scene);
        });
    }
}
