/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.DuskyPath;

import Game.Loot.Loot;
import Game.Room.*;
import Game.GUI.*;
import Game.DuskyPath.DuskyPath;
import Game.GameMechanics.GameMechanics;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;

public class Player extends Entity {

   private static JFrame lootWindow;
   private static int maxWater;
   private static int currentWater;
   private static int maxStorage;
   private static int currentStorage;
   private static Loot store;
   private static Item strongestWep;
   
   private static final int DRINKDELAY = 1;
   private static final int EATDELAY = 2;
   private static int drinkCooldown = 1;
   private static int eatCooldown = 2;

   public Player() {
      super("Debug", 10);
      maxWater = 1;
      currentWater = 0;
      maxStorage = 1;
      currentStorage = 0;
      strongestWep = null;
   }

   public Player(String name, int health, int water, int storage, Loot loot) {
      super(name, health);
      maxWater = water;
      currentWater = maxWater;
      maxStorage = storage;
      currentStorage = 0;
      store = loot;
      strongestWep = null;
   }

   public int getMaxWater() {
      return maxWater;
   }

   public int getMaxtStorage() {
      return maxStorage;
   }

   public int getCurrentWater() {
      return currentWater;
   }

   public int getCurrentStorage() {
      return currentStorage;
   }

   public Item getStrongestWeapon() {
      return strongestWep;
   }
 
   public Loot getInventory()
   {
      return store;
   }
   
   public void travel()
   {
      drinkCooldown --;
      if (drinkCooldown == 0)
      {
         currentWater --;
         drinkCooldown = DRINKDELAY;
         if (currentWater <= 0)
         {
            setHealth(0);
         }
      }
      
      eatCooldown --;
      if (eatCooldown == 0)
      {
         Item temp = store.getLoot(new Material(GameMechanics.COOKEDMEATID, 0));
         if (temp == null || temp.getAmount() == 0)
         {
            setHealth(0); 
         }
         else
         {
            temp.deduct(1);
            eatCooldown = EATDELAY;
         }
      }
   }
   
   public void restock()
   {
      currentWater = maxWater;
      setHealth(getMaxHealth());
   }
      
   public void attack(Entity other) {
      int dmg;
      if (strongestWep == null) {
         dmg = GameMechanics.fist.getStrength();
      } 
      else {
         dmg = ((Weapon) strongestWep).getStrength();   //reads weapon dmg number (assuming index 0 of inventory is weapon)
      }
      other.receiveDamage(dmg);         //enemy "receives" attack	
   }

   public void bringItem(Item other) {
      if (currentStorage < maxStorage) {
         store.add(other);
      }
   }

   public Item returnItem(Item item) {
      return store.take(item);
   }

   public Item displayInventory(int i) {
      return store.getLoot(i);
   }

   public void selectAll(Loot other) { //picks up all loot if possible (ease of use)
      store.addInventories(other);
   }

   public void wipeInventory() {      //necessary for recieve loot logic and death
      for (int i = 0; i < store.length(); i++) {
         store.getLoot(i).setAmount(0);
      }
   }

   public void prepare(Resource resource) {
      Loot loot = new Loot(resource);
   
      lootWindow = new JFrame("Loot");
      lootWindow.setSize(480, 640);
   
      Display masterDisplay = new Display(480, 640);
      masterDisplay.setLayout(new BoxLayout(masterDisplay, BoxLayout.PAGE_AXIS));
   
      LootDisplay lootDisplay = new LootDisplay(480, 600, store, loot);
      lootDisplay.setPreferredSize(new Dimension(480, 600));
   
      Display buttonDisplay = new Display(480, 40);
      buttonDisplay.setPreferredSize(new Dimension(480, 40));
      JButton exitButton = new JButton("Start exploration");
      exitButton.addActionListener(new MenuButtonHandler());
      buttonDisplay.add(exitButton);
   
      masterDisplay.add(lootDisplay);
      masterDisplay.add(buttonDisplay);
   
      lootWindow.setContentPane(masterDisplay);
      lootWindow.repaint();
      lootWindow.setVisible(true);
   }

   public void receiveLoot(Loot loot) {
      lootWindow = new JFrame("Loot");
      lootWindow.setSize(480, 640);
   
      Display masterDisplay = new Display(480, 640);
      masterDisplay.setLayout(new BoxLayout(masterDisplay, BoxLayout.PAGE_AXIS));
   
      LootDisplay lootDisplay = new LootDisplay(480, 600, store, loot);
      lootDisplay.setPreferredSize(new Dimension(480, 600));
   
      Display buttonDisplay = new Display(480, 40);
      buttonDisplay.setPreferredSize(new Dimension(480, 40));
      JButton exitButton = new JButton("OK");
      exitButton.addActionListener(new MenuButtonHandler());
      buttonDisplay.add(exitButton);
   
      masterDisplay.add(lootDisplay);
      masterDisplay.add(buttonDisplay);
   
      lootWindow.setContentPane(masterDisplay);
      lootWindow.repaint();
      lootWindow.setVisible(true);
   }

   private void startExploration() {
      new DuskyPath(this);
   }

   private static class LootDisplay extends Display {
   
      Loot inventory;
      Loot loot;
      Loot all;
      int rows;
   
      public LootDisplay(int width, int height, Loot inventory, Loot loot) {
         super(width, height);
         this.inventory = inventory;
         this.loot = loot;
         setLayout(new GridLayout(1, 1));
         addContents();
      }
   
      public void addContents() {
         setLayout(new GridLayout(0, 5));
         add(new JLabel());
         add(new JLabel());
         JLabel inventoryLabel = new JLabel("Inventory");
         inventoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
         inventoryLabel.setForeground(textColor);
         add(inventoryLabel);
         JLabel lootLabel = new JLabel("Loot");
         lootLabel.setForeground(textColor);
         lootLabel.setHorizontalAlignment(SwingConstants.CENTER);
         add(lootLabel);
         add(new JLabel());
      
         for (int i = 0; i < inventory.length(); i++) {
            Item inventoryItem = inventory.getLoot(i);
            Item lootItem = loot.getLoot(inventoryItem);
            if (lootItem == null) {
               if (inventoryItem instanceof Material) { // If instance of material
                  lootItem = new Material((Material) inventoryItem); //Give material of x amount
               } 
               else if (inventoryItem instanceof Weapon) { //If drop is weapon
                  lootItem = new Weapon((Weapon) inventoryItem); //Give weapon of x amount
               } 
               else if (inventoryItem instanceof Healing) { //If drop is Healing
                  lootItem = new Healing((Healing) inventoryItem); //Give healing of x amount
               }
            }
            JLabel name = new JLabel(inventoryItem.toString());
            name.setForeground(textColor);
            add(name);
            JLabel inventoryAmount = new JLabel("" + inventoryItem.getAmount());
            inventoryAmount.setForeground(textColor);
            inventoryAmount.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel lootAmount = new JLabel("" + lootItem.getAmount());
            lootAmount.setForeground(textColor);
            lootAmount.setHorizontalAlignment(SwingConstants.CENTER);
            JButton dropButton = new JButton("Drop");
            if (inventoryItem.getAmount() == 0) {
               dropButton.setEnabled(false);
            }
            JButton takeButton = new JButton("Take");
            if (lootItem.getAmount() == 0) {
               takeButton.setEnabled(false);
            }
            ButtonHandler handler = new ButtonHandler(inventory, loot, inventoryItem, lootItem, dropButton, inventoryAmount, lootAmount, takeButton);
            dropButton.addActionListener(handler);
            takeButton.addActionListener(handler);
            add(dropButton);
            add(inventoryAmount);
            add(lootAmount);
            add(takeButton);
         }
         for (int i = 0; i < loot.length(); i++) {
            Item lootItem = loot.getLoot(i);
            if (inventory.getLoot(lootItem) == null) {
               Item inventoryItem;
               if (lootItem instanceof Material) { // If instance of material
                  inventoryItem = new Material((Material) lootItem); //Give material of x amount
               } 
               else if (lootItem instanceof Weapon) { //If drop is weapon
                  inventoryItem = new Weapon((Weapon) lootItem); //Give weapon of x amount
               } 
               else { //If drop is Healing
                  inventoryItem = new Healing((Healing) lootItem); //Give healing of x amount
               }
            
               JLabel name = new JLabel(inventoryItem.toString());
               name.setForeground(textColor);
               add(name);
               JLabel inventoryAmount = new JLabel("0");
               inventoryAmount.setForeground(textColor);
               inventoryAmount.setHorizontalAlignment(SwingConstants.CENTER);
               JLabel lootAmount = new JLabel("" + lootItem.getAmount());
               lootAmount.setForeground(textColor);
               lootAmount.setHorizontalAlignment(SwingConstants.CENTER);
               JButton dropButton = new JButton("Drop");
               dropButton.setEnabled(false);
               JButton takeButton = new JButton("Take");
               ButtonHandler handler = new ButtonHandler(inventory, loot, inventoryItem, lootItem, dropButton, inventoryAmount, lootAmount, takeButton);
               dropButton.addActionListener(handler);
               takeButton.addActionListener(handler);
               add(dropButton);
               add(inventoryAmount);
               add(lootAmount);
               add(takeButton);
            }
         }
      
      }
   }

   private class MenuButtonHandler implements ActionListener {
   
      public void actionPerformed(ActionEvent e) {
         String s = e.getActionCommand();
         if (s.equals("Start exploration")) {
            lootWindow.dispose();
            startExploration();
         } 
         else if (s.equals("OK")) {
            lootWindow.dispose();
         }
      }
   }

   private static class ButtonHandler implements ActionListener {
   
      private static JButton[] addButtons = new JButton[0];
      private static JButton[] removeButtons = new JButton[0];
      private final Loot inventory;
      private final Loot loot;
      private final Item inventoryItem;
      private final Item lootItem;
      private final JButton reduceButton;
      private final JLabel inventoryLabel;
      private final JLabel lootLabel;
      private final JButton addButton;
      private final int modifier;
   
      public ButtonHandler(Loot inventory, Loot loot, Item inventoryItem, Item lootItem, JButton reduceButton, JLabel inventoryLabel, JLabel lootLabel, JButton addButton) {
         this.inventory = inventory;
         this.loot = loot;
         this.inventoryItem = inventoryItem;
         this.lootItem = lootItem;
         this.reduceButton = reduceButton;
         this.inventoryLabel = inventoryLabel;
         this.lootLabel = lootLabel;
         this.addButton = addButton;
         this.modifier = 1;
         addButtons = Arrays.copyOf(addButtons, addButtons.length + 1);
         addButtons[addButtons.length - 1] = addButton;
      }
   
      public void actionPerformed(ActionEvent e) {
         String s = e.getActionCommand();
         if (s.equals("Take") && currentStorage + modifier <= maxStorage)
         {
            currentStorage += modifier;
            inventoryItem.addItem(lootItem.retrieve(1));
            if (inventoryItem.getAmount() == 1) {
               inventory.add(inventoryItem);
            }
            if (lootItem.getAmount() == 0) {
               addButton.setEnabled(false);
            }
            reduceButton.setEnabled(true);
            
            inventoryLabel.setText("" + inventoryItem.getAmount());
            lootLabel.setText("" + lootItem.getAmount());
            
            if (currentStorage == maxStorage)
            {
               for (int i = 0; i < addButtons.length; i++)
               {
                  addButtons[i].setEnabled(false);
               }
            }
         	
            loot.updateArray();
         }
         else if (s.equals("Drop"))
         {
            currentStorage -= modifier;
            lootItem.addItem(inventoryItem.retrieve(1));
            if (lootItem.getAmount() == 1) {
               loot.add(lootItem);
            }
            if (inventoryItem.getAmount() == 0) {
               reduceButton.setEnabled(false);
            }
            addButton.setEnabled(true);
            
            inventoryLabel.setText("" + inventoryItem.getAmount());
            lootLabel.setText("" + lootItem.getAmount());
            if (currentStorage == maxStorage - modifier)
            {
               for (int i = 0; i < addButtons.length; i++)
               {
                  addButtons[i].setEnabled(true);
               }
            }
            loot.updateArray();
         }
      
      }
   }

}
