/*
	File Name:	 DuskyPath.java
	Name:			 Shawn Wang (GUI)
	Class:		 ICS4U1
	Date:			 June 15, 2017
	Description: Handles explore events. 
                Provides a random chance of encountering an enemy, finding an outpost, or nothing happening.
                Provides a message display to inform the result of the action.
*/
package Game.DuskyPath;

// imports classes needed
import Game.Game;
import Game.Room.*;
import Game.GUI.*;
import Game.GameMechanics.GameMechanics;
import Game.Loot.Loot;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DuskyPath {

   private static JFrame exploreWindow;      // displays exploration information

   private Player player;                    //player who's playing
   private static Messages exploreMessages = new Messages();      // keeps track of messages
   
   // 80% chance of fight, 10% chance for outpose, 10% chance nothing
   private static final double ENCOUNTERRANGE = 0.8;
   private static final double OUTPOSTRANGE = 0.9;
   private static final double NOEVENTRANGE = 1.0;

   // constructor
   // sets up exploration window
   public DuskyPath(Player p) {
      player = p;
      
      // sets up exploration window
      exploreWindow = new JFrame("Exploration");
      exploreWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // set default close operation
      exploreWindow.setSize(600, 600);                               // set size
      exploreWindow.setLocation(300, 200);                           // set location
      exploreWindow.setResizable (false);                            // set not resizable
      exploreWindow.setContentPane(new ExploreDisplay(600, 600));    // set display
      exploreWindow.getContentPane().setLayout(new BorderLayout());  // set display layout
   
      // adds message to explore display
      exploreMessages.addText("Started expedition.");
      
      // sets up buttons
      JButton exploreButton = new JButton("Explore");
      JButton returnButton = new JButton("Return");
      ButtonHandler handler = new ButtonHandler();
      exploreButton.addActionListener(handler);
      returnButton.addActionListener(handler);
      
      exploreWindow.add(exploreButton, BorderLayout.PAGE_START);
      exploreWindow.add(returnButton, BorderLayout.PAGE_END);
      
      // displays window
      exploreWindow.setVisible(true);
   }
   // ExploreDisplay:
   // Child class of Display
   // Displays player information (health, water, inventory...)
   private class ExploreDisplay extends Display {
   
      // stores message locations
      private static final int TEXTXPOSITION = 20;
      private static final int TEXTINITIALYPOSITION = 50;
      private static final int MESSAGEXPOSITION = 220;
      private static final int MESSAGEINITIALYPOSITION = 50;
      private static final int INVENTORYXPOSITION = 450;
      private static final int INVENTORYINITIALYPOSITION = 50;
   
      // constructor
      public ExploreDisplay(int width, int length) {
         super(width, length);
      }
   
      // painting components of explore display
      // calls paintPlayer (display health, water...)
      // calls paintMessages (display fight messages)
      // calls paintInventory (display inventory)
      public void paintContents(Graphics g) {
         g.setColor(textColor);
         paintPlayer(g);
         paintMessages(g);
         paintInventory(g);
      }
   
      // displays important player information
      public void paintPlayer(Graphics g) {
         int xPosition = TEXTXPOSITION;
         int yPosition = TEXTINITIALYPOSITION;
         g.drawString("Exploration", xPosition, yPosition);
         yPosition += LINESPACING;
         // name
         g.drawString(player.getName(), xPosition, yPosition);
         yPosition += LINESPACING;
         // health
         g.drawString("Health: " + player.getHealth() + " / " + player.getMaxHealth(), xPosition, yPosition);
         yPosition += LINESPACING;
         // water
         g.drawString("Water: " + player.getCurrentWater() + " / " + player.getMaxWater(), xPosition, yPosition);
      }
      
      // displays fight messages
      public void paintMessages(Graphics g) {
         String[] text; // stores messages
         int xPosition = MESSAGEXPOSITION;
         int yPosition = MESSAGEINITIALYPOSITION;
         
         // displays each message
         for (int i = 0; (text = exploreMessages.getText(i)) != null; i++) {
            for (int j = 0; j < text.length && text[j] != null; j++) {
               g.drawString(text[j], xPosition, yPosition);
               yPosition += LINESPACING;
            }
            yPosition += TEXTSPACING;
         }
      
      }
   
       // finds each item in player's inventory and displays it
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

   // ButtonHandler:
   // Handles the player's decision to continue to explore or to return
   private class ButtonHandler implements ActionListener {
   
      // Handles explore display buttons
      public void actionPerformed(ActionEvent e) {
         String action = e.getActionCommand(); // get button text
         // check for player's decision
         if (action.equals("Explore")) // if continue to explore
         {
            player.travel(); // account for travel costs
            if (player.isAlive()) // if player is alive (possible death from exploration without food or water)
            {
               // generate a random event
               Double result = Math.random();
               if (result < ENCOUNTERRANGE) // 0 <= result < 0.8: fight with random enemy
               {
                  // creates a new enemy
                  Enemy enemy = new Enemy(GameMechanics.enemies[(int)(Math.random() * (GameMechanics.enemies.length -  1))]);
                  
                  // adds its description to explore window
                  exploreMessages.addText(enemy.getDescription());
                  
                  // hide explore window
                  exploreWindow.setVisible(false);
                  
                  // start fight process
                  new Fight(player, enemy);
               }
               else if (result < OUTPOSTRANGE) // 0.8 <= result < 0.9: discover outpost
               {
                  // adds a message to explore display
                  exploreMessages.addText("You found an abandoned outpost and restocked your supplies.");
                  // allow player to replenish supplies
                  player.restock();
                  // player discover some resources in the abandoned outpost
                  Item[] items = new Item[1];
                  items[0] = GameMechanics.OUTPOST.drop();
                  player.receiveLoot(new Loot(items));
               }
               else  // 0.9 <= result < 1: nothing
               {
                  // adds a message to explore display
                  exploreMessages.addText("You explored for a while, but nothing happened.");
               }
               // refresh explore window
               exploreWindow.repaint();
            } 
            else // if player dies
            {
               // stop exploration
               exploreWindow.dispose();
               // respawn
               Game.respawn();
            }
         } 
         else if (action.equals("Return")) // if player choose to return
         {
            // end exploration
            exploreWindow.dispose();
            // return with loot
            Game.endExpedition(player);
         }
      }
   }

   // execute when player runs into a monster
   // for adding into fight messages (not yet implemented)
   public String encounterEnemy() 
   {
      Enemy monster = new Enemy(GameMechanics.enemies[(int) (Math.random() * (GameMechanics.enemies.length - 1))]); //generate enemies to return to Events so they can use
      Fight newFight = new Fight(player, monster);
      return "YOU ENCOUNTERED " + monster.getName() + ".";
   }

   // execute when you win a fight
   // reopens exploration
   public static void winFight() {
      exploreWindow.setVisible(true);
   }

   // execute when you lose a fight
   // end exploration and respawn
   public static void loseFight() {
      exploreWindow.dispose();
      Game.respawn();
   }
}
