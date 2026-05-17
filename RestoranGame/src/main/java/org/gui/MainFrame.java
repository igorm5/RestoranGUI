package org.gui;

import org.controller.GameController;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout = new CardLayout();
    private JPanel     container  = new JPanel(cardLayout);
    private GameController ctrl;

    // Scenes
    private DapurScene  dapurScene;
    private JimatScene  jimatScene;
    private RekapScene  rekapScene;

    private Level1Scene lvl1;
    private Level2Scene lvl2;
    private Level3Scene lvl3;
    private Level4Scene lvl4;
    private Level5Scene lvl5;

    public MainFrame() {
        setTitle("Restaurant Tycoon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Controller dibuat setelah frame siap
        ctrl = new GameController(this);

        // Buat semua scene
        dapurScene = new DapurScene(ctrl);
        jimatScene = new JimatScene(ctrl);
        rekapScene = new RekapScene(ctrl);

        lvl1 = new Level1Scene(ctrl);
        lvl2 = new Level2Scene(ctrl);
        lvl3 = new Level3Scene(ctrl);
        lvl4 = new Level4Scene(ctrl);
        lvl5 = new Level5Scene(ctrl);

        // Daftarkan ke controller
        ctrl.registerScenes(dapurScene, jimatScene, rekapScene);

        // Tambahkan ke CardLayout
        container.add(new Start(this), "menu");
        container.add(dapurScene,      "dapur");
        container.add(jimatScene,      "jimat");
        container.add(rekapScene,      "rekap");
        container.add(lvl1,            "level1");
        container.add(lvl2,            "level2");
        container.add(lvl3,            "level3");
        container.add(lvl4,            "level4");
        container.add(lvl5,            "level5");

        add(container);
        cardLayout.show(container, "menu");

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        // Init HUD awal
        ctrl.refreshHUD();
    }

    public void showPanel(String name) {
        cardLayout.show(container, name);
    }

    /** Tampilkan level scene yang sesuai dan daftarkan ke controller */
    public void showLevelScene(int level) {
        LevelScene scene;
        String panelName;
        switch (level) {
            case 2:  scene = lvl2; panelName = "level2"; break;
            case 3:  scene = lvl3; panelName = "level3"; break;
            case 4:  scene = lvl4; panelName = "level4"; break;
            case 5:  scene = lvl5; panelName = "level5"; break;
            default: scene = lvl1; panelName = "level1"; break;
        }
        ctrl.setActiveLevelScene(scene);
        cardLayout.show(container, panelName);
    }

    /** Popup tikus — di-invoke dari GameController (pastikan di EDT) */
    public void showPopupTikus() {
        SwingUtilities.invokeLater(() -> PopUpScene.tikus(this));
    }

    public void showPopupKabur() {
        SwingUtilities.invokeLater(() -> PopUpScene.pelanggankabur(this));
    }

    public GameController getCtrl() { return ctrl; }
}
