package org.gui;

import java.awt.Font;

public class FontUtil {

    private static Font pressStart;

    public static Font loadFont(float size) {
        try {
            if (pressStart == null) {
                pressStart = Font.createFont(
                        Font.TRUETYPE_FONT,
                        FontUtil.class.getResourceAsStream("/fonts/PressStart.ttf")
                );
            }
            return pressStart.deriveFont(size);
        } catch (Exception e) {
            e.printStackTrace();
            return FontUtil.loadFont((float)(int) size);
        }
    }
}


