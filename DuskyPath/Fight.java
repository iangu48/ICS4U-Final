/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.DuskyPath;

/**
 *
 * @author shawn
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Game.GUI.*;
import Game.GameMechanics.GameMechanics;
import Game.Room.*;

public class Fight {

    private int MEATHEAL = 8; //WHAT DOES IT HEAL
    private Player player;
    private Enemy enemy;
    private JFrame fightWindow;

    private class FightDisplay extends Display {

        private int width;
        private int length;
        private static final int TEXTXPOSITION = 20;
        private static final int TEXTINITIALYPOSITION = 50;
        private static final int MESSAGEXPOSITION = 140;
        private static final int MESSAGEINITIALYPOSITION = 20;
        private static final int ENEMYXPOSITION = 370;
        private static final int ENEMYINITIALYPOSITION = 50;

        public FightDisplay(int width, int length) {
            super(width, length);
        }

        public void paintContents(Graphics g) {
            g.setColor(textColor);
            paintPlayer(g);
            paintMessages(g);
            paintEnemy(g);
        }

        public void paintPlayer(Graphics g) {
            int xPosition = TEXTXPOSITION;
            int yPosition = TEXTINITIALYPOSITION;
            g.drawString("Fight", xPosition, yPosition);
            yPosition += LINESPACING;
            g.drawString(player.getName(), xPosition, yPosition);
            yPosition += LINESPACING;
            g.drawString("Health: " + player.getHealth() + " / " + player.getMaxHealth(), xPosition, yPosition);
            yPosition += LINESPACING;
            if (player.getStrongestWeapon() == null) {
                g.drawString("Weapon: fist", xPosition, yPosition);
            } else {
                g.drawString("Weapon: " + player.getStrongestWeapon().getName(), xPosition, yPosition);
            }
        }

        public void paintMessages(Graphics g) {
            int xPosition = MESSAGEXPOSITION;
            int yPosition = ENEMYINITIALYPOSITION;

            g.drawString("You encountered " + enemy.getName(), xPosition, yPosition);
        }

        public void paintEnemy(Graphics g) {
            int xPosition = ENEMYXPOSITION;
            int yPosition = ENEMYINITIALYPOSITION;

            g.drawString(enemy.getName(), xPosition, yPosition);
            yPosition += LINESPACING;
            g.drawString("Health: " + enemy.getHealth() + " / " + enemy.getMaxHealth(), xPosition, yPosition);
        }
    }

    public class ButtonHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String s = e.getActionCommand();
            if (s.equals("Fight")) {
                playerFight();
            } else if (s.equals("Heal")) {
                consumeMeat();
            }
            if (!enemy.isAlive()) {
                fightWindow.dispose();
                DuskyPath.winFight();
            } else if (!player.isAlive()) {
                fightWindow.dispose();
                DuskyPath.loseFight();
            }
            fightWindow.repaint();
        }

    }

    //Constructor for Fight
    public Fight(Player player, Enemy enemy) {
        this.enemy = enemy;
        this.player = player;

        Display masterDisplay = new Display(480, 640);
        masterDisplay.setLayout(new BoxLayout(masterDisplay, BoxLayout.PAGE_AXIS));

        FightDisplay fightDisplay = new FightDisplay(480, 540);
        fightDisplay.setPreferredSize(new Dimension(480, 540));

        Display buttonDisplay = new Display(480, 100);
        buttonDisplay.setPreferredSize(new Dimension(480, 100));
        buttonDisplay.setLayout(new GridLayout(1, 2));

        JButton fightButton = new JButton("Fight");
        fightButton.addActionListener(new ButtonHandler());
        JButton healButton = new JButton("Heal");
        healButton.addActionListener(new ButtonHandler());
        buttonDisplay.add(fightButton);
        buttonDisplay.add(healButton);

        masterDisplay.add(fightDisplay);
        masterDisplay.add(buttonDisplay);

        fightWindow = new JFrame("Fight");
        fightWindow.setContentPane(masterDisplay);
        fightWindow.setSize(480, 640);
        fightWindow.setLocation(300, 200);
        fightWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fightWindow.setVisible(true);
    }

    private void consumeMeat() {
        Item temp = player.getInventory().getLoot(new Material(GameMechanics.COOKEDMEATID, 0));
        if (temp != null && temp.deduct(1))
        {
            player.heal(MEATHEAL);
            enemyFight();
        }
    }

    private void playerFight() {
        player.attack(enemy);
        enemyFight();
    }

    private void enemyFight() {
        enemy.attack(player);
    }
}
