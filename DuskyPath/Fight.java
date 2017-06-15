/*
	File Name:	 Fight.java
	Name:			 Shawn Wang
	Class:		 ICS4U1
	Date:			 June 15, 2017
	Description: Main container for fight events in the game.
                Combat system between player and enemy.
*/
package Game.DuskyPath;

// import needed classes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Game.GUI.*;
import Game.GameMechanics.GameMechanics;
import Game.Room.*;

public class Fight {

   private int MEATHEAL = 8;    // amount of healing by cooked meat in combat
   private Player player;       // player
   private Enemy enemy;         // enemy
   private JFrame fightWindow;  // displays fight information and controls

  // FightDisplay:
  // Child class of Display
  // Displays player information, enemy information
  // Displays text messages (not yet implemented)
   private class FightDisplay extends Display {
   
      private static final int TEXTXPOSITION = 20;
      private static final int TEXTINITIALYPOSITION = 50;
      private static final int MESSAGEXPOSITION = 140;
      private static final int MESSAGEINITIALYPOSITION = 20;
      private static final int ENEMYXPOSITION = 370;
      private static final int ENEMYINITIALYPOSITION = 50;
   
       // constructor
      public FightDisplay(int width, int length) {
         super(width, length);
      }
       
       // paints player, enemy and text information
      public void paintContents(Graphics g) {
         g.setColor(textColor);
         paintPlayer(g);
         paintMessages(g);
         paintEnemy(g);
      }
   
       // displays player health and weapon
      public void paintPlayer(Graphics g) {
         int xPosition = TEXTXPOSITION;
         int yPosition = TEXTINITIALYPOSITION;
         g.drawString("Fight", xPosition, yPosition);
         yPosition += LINESPACING;
         // name
         g.drawString(player.getName(), xPosition, yPosition);
         yPosition += LINESPACING;
         // health
         g.drawString("Health: " + player.getHealth() + " / " + player.getMaxHealth(), xPosition, yPosition);
         yPosition += LINESPACING;
         // weapon
         if (player.getStrongestWeapon() == null) {
            g.drawString("Weapon: fist", xPosition, yPosition);
         } 
         else {
            g.drawString("Weapon: " + player.getStrongestWeapon().getName(), xPosition, yPosition);
         }
      }
       
       // displays messages (not yet implemented)
      public void paintMessages(Graphics g) {
         int xPosition = MESSAGEXPOSITION;
         int yPosition = ENEMYINITIALYPOSITION;
      
         g.drawString("You encountered " + enemy.getName(), xPosition, yPosition);
      }
      
      // displays enemy information
      public void paintEnemy(Graphics g) {
         int xPosition = ENEMYXPOSITION;
         int yPosition = ENEMYINITIALYPOSITION;
         
         // name
         g.drawString(enemy.getName(), xPosition, yPosition);
         yPosition += LINESPACING;
         // health
         g.drawString("Health: " + enemy.getHealth() + " / " + enemy.getMaxHealth(), xPosition, yPosition);
      }
   }
   
   // ButtonHandler:
   // Handles player's decision to fight or to health 
   public class ButtonHandler implements ActionListener {
      
       // called by button press
      public void actionPerformed(ActionEvent e) {
         String s = e.getActionCommand(); // get button text
         if (s.equals("Fight")) // if player chooses fight
         {
            playerFight(); // call fight method
         } 
         else if (s.equals("Heal")) // if player chooses to health
         {
            consumeMeat(); // call heal method
         }
         if (!enemy.isAlive()) // if enemy dies
         {
            fightWindow.dispose();  // end fight
            enemy.giveLoot(player); // player receives enemy's loot
         } 
         else if (!player.isAlive()) // if player dies
         {
            fightWindow.dispose();  // end fight
            DuskyPath.loseFight();  // calls loseFight method and respawns
         }
         // refreshes fight window
         fightWindow.repaint();
      }
   
   }

   //Constructor for Fight
   public Fight(Player player, Enemy enemy) {
      this.enemy = enemy;
      this.player = player;
   
      // sets up fight window
      fightWindow = new JFrame("Fight");
      fightWindow.setSize(480, 640);      // size
      fightWindow.setLocation(300, 200);  // location
      fightWindow.setResizable(false);    // resizable
      fightWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // close operation
   
      // sets up fight displays
      Display masterDisplay = new Display(480, 640);
      masterDisplay.setLayout(new BoxLayout(masterDisplay, BoxLayout.PAGE_AXIS));
   
      // for player, enemy information and text messages
      FightDisplay fightDisplay = new FightDisplay(480, 540);
      fightDisplay.setPreferredSize(new Dimension(480, 540));
   
      // for buttons
      Display buttonDisplay = new Display(480, 100);
      buttonDisplay.setPreferredSize(new Dimension(480, 100));
      buttonDisplay.setLayout(new GridLayout(1, 2));
   
      // sets up buttons
      JButton fightButton = new JButton("Fight");
      fightButton.addActionListener(new ButtonHandler());
      JButton healButton = new JButton("Heal");
      healButton.addActionListener(new ButtonHandler());
      
      buttonDisplay.add(fightButton);
      buttonDisplay.add(healButton);
   
      // creates fight window's display
      masterDisplay.add(fightDisplay);
      masterDisplay.add(buttonDisplay);
      
      // displays fight window
      fightWindow.setContentPane(masterDisplay);
      fightWindow.repaint();
      fightWindow.setVisible(true);
   }
   
   // player chooses to heal:
   // checks for sufficient amount of cooked meat and heal if possible
   // if action is performed, let enemy take their turn
   private void consumeMeat() {
      Item temp = player.getInventory().getLoot(new Material(GameMechanics.COOKEDMEATID, 0));
      if (temp != null && temp.deduct(1))
      {
         player.heal(MEATHEAL);
         enemyFight();
      }
   }
   
   // player chooses to fight:
   // player attacks enemy, let enemy take their turn
   private void playerFight() {
      player.attack(enemy);
      enemyFight();
   }

   // enemy's turn: 
   // enemy chooses to attack player
   private void enemyFight() {
      enemy.attack(player);
   }
}