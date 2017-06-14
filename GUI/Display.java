/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author shawn
 */
public class Display extends JPanel {

    private static boolean switchedLighting = false;
    protected static Color backgroundColor = Color.BLACK;
    protected static Color textColor = Color.WHITE;
    protected final static int TEXTSPACING = 25;
    protected final static int LINESPACING = 15;

    int width;
    int height;

    public Display(int width, int height) {
        this.width = width;
        this.height = height;
        setSize(width, height);
    }

    public static boolean getLighting() {
        return switchedLighting;
    }

    public static void lights() {
        if (backgroundColor.equals(Color.BLACK)) {
            backgroundColor = Color.DARK_GRAY;
        } else {
            backgroundColor = Color.BLACK;
        }
        switchedLighting = true;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBackground(g);
        paintContents(g);
    }

    public void paintBackground(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, width, height);
    }

    public void paintContents(Graphics g) {

    }

}
