/*
   Name: DuskyPath.java
   Author: Raghav and Michael
   Date: June 15, 2017
   School: AY Jackson SS
   Purpose: This class is to manage all the encounters that the player faces and to
            be the class that manages the "exploration" aspect of expeditions.
   
 */
package Game.DuskyPath;

import Game.Game;
import Game.Room.*;
import Game.GUI.*;
import Game.GameMechanics.GameMechanics;
import Game.Loot.Loot;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author shawn
 */
public class DuskyPath {

   private static JFrame exploreWindow;

   private double BASECHANCE = 0.1; //minimum loot chance
   private double lootDrop; //loot chance
   private Player player; //player who's playing
   private Enemy[] enemies; //pass in enemies from the event you are executing
   private static Messages exploreMessages = new Messages();
   private static final double ENCOUNTERRANGE = 0.8;
   private static final double OUTPOSTRANGE = 0.9;
   private static final double NOEVENTRANGE = 1.0;

   public DuskyPath(Player p) {
      player = p;
      exploreWindow = new JFrame("Exploration");
      exploreWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      exploreWindow.setContentPane(new ExploreDisplay(600, 600));
      exploreWindow.setSize(600, 600);
      exploreWindow.setLocation(300, 200);
      exploreMessages.addText("Started expedition.");
      JButton exploreButton = new JButton("Explore");
      JButton returnButton = new JButton("Return");
      ButtonHandler handler = new ButtonHandler();
      exploreButton.addActionListener(handler);
      returnButton.addActionListener(handler);
      exploreWindow.getContentPane().setLayout(new BorderLayout());
      exploreWindow.add(exploreButton, BorderLayout.PAGE_START);
      exploreWindow.add(returnButton, BorderLayout.PAGE_END);
      exploreWindow.setVisible(true);
   }

   private class ExploreDisplay extends Display {
   
      private static final int TEXTXPOSITION = 20;
      private static final int TEXTINITIALYPOSITION = 50;
      private static final int MESSAGEXPOSITION = 220;
      private static final int MESSAGEINITIALYPOSITION = 50;
      private static final int INVENTORYXPOSITION = 450;
      private static final int INVENTORYINITIALYPOSITION = 50;
   
      public ExploreDisplay(int width, int length) {
         super(width, length);
      }
   
      public void paintContents(Graphics g) {
         g.setColor(textColor);
         paintPlayer(g);
         paintMessages(g);
         paintInventory(g);
      }
   
      public void paintPlayer(Graphics g) {
         int xPosition = TEXTXPOSITION;
         int yPosition = TEXTINITIALYPOSITION;
         g.drawString("Exploration", xPosition, yPosition);
         yPosition += LINESPACING;
         g.drawString(player.getName(), xPosition, yPosition);
         yPosition += LINESPACING;
         g.drawString("Health: " + player.getHealth() + " / " + player.getMaxHealth(), xPosition, yPosition);
         yPosition += LINESPACING;
         g.drawString("Water: " + player.getCurrentWater() + " / " + player.getMaxWater(), xPosition, yPosition);
      }
   
      public void paintMessages(Graphics g) {
         String[] text;
         int xPosition = MESSAGEXPOSITION;
         int yPosition = MESSAGEINITIALYPOSITION;
      
         for (int i = 0; (text = exploreMessages.getText(i)) != null; i++) {
            for (int j = 0; j < text.length && text[j] != null; j++) {
               g.drawString(text[j], xPosition, yPosition);
               yPosition += LINESPACING;
            }
            yPosition += TEXTSPACING;
         }
      
      }
   
      public void paintInventory(Graphics g) {
         g.setColor(textColor);
         int xPosition = INVENTORYXPOSITION;
         int yPosition = INVENTORYINITIALYPOSITION;
         int i = 0;
         Item temp;
         while ((temp = player.displayInventory(i)) != null) {
            g.drawString(temp.getName() + ": " + temp.getAmount(), xPosition, yPosition);
            i++;
            yPosition += LINESPACING;
         }
      }
   
   }

   private class ButtonHandler implements ActionListener {
   
      public void actionPerformed(ActionEvent e) {
         String action = e.getActionCommand();
         if (action.equals("Explore")) {
            player.travel();
            if (player.isAlive()) {
               Double result = Math.random();
               if (result < ENCOUNTERRANGE)
               {
                  Enemy enemy = new Enemy(GameMechanics.enemies[(int)(Math.random() * 5)]);
                  exploreMessages.addText(enemy.getDescription());
                  exploreWindow.setVisible(false);
                  new Fight(player, enemy);
               }
               else if (result < OUTPOSTRANGE)
               {
                  exploreMessages.addText("You found an abandoned outpost and restocked your supplies.");
                  player.restock();
                  Item[] items = new Item[1];
                  items[0] = GameMechanics.OUTPOST.drop();
                  player.receiveLoot(new Loot(items));
               }
               else
               {
                  exploreMessages.addText("Nothing happened.");
               }
            } 
            else {
               exploreWindow.dispose();
               Game.respawn();
            }
         } 
         else if (action.equals("Return")) {
            exploreWindow.dispose();
            Game.endExpedition(player);
         }
      }
   }

   /*
    encounterEnemy Purpose: To randomly generage enemies for the player to encounter.
    Return values: This method returns a string to display what monster they actually encounter
   */
   public String encounterEnemy() //execute when player runs into a monster
   {
      Enemy monster = enemies[(int) (Math.random() * (enemies.length - 1))]; //generate enemies to return to Events so they can use
      Fight newFight = new Fight(player, monster);
      return "YOU ENCOUNTERED " + monster.getName() + ".";
   
   }

   public static void winFight() {
      exploreWindow.setVisible(true);
   }

   public static void loseFight() {
      exploreWindow.dispose();
      Game.respawn();
   }
}
