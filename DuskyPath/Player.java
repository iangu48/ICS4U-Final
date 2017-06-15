/*
	File Name:	 Player.java
	Name:			 Tongli Han, Shawn Wang(GUI/editing)
	Class:		 ICS4U1
	Date:			 June 15, 2017
	Description: Keeps track of player values: current / max: health / water / storage.
                Keeps track of player's inventory and strongest weapon.
                Controls player's attack, attacked, and heal methods.
                Keeps track of food and water costs of exploration.
                Controls over inventory.
*/
package Game.DuskyPath;

// import needed classes
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

   private static JFrame lootWindow;      // window for loot
   private static int maxWater;           // maximum water capacity
   private static int currentWater;       // current water amount
   private static int maxStorage;         // maximum storage capacity
   private static int currentStorage;     // current storage amount
   private static Loot store;             // player's inventory
   private static Item strongestWep;      // player's strongest weapon

   private static final int DRINKDELAY = 1;  // interval between drinking water
   private static final int EATDELAY = 2;    // interval between eating cooked meat
   private static int drinkCooldown = 1;     // cooldown until next drinking
   private static int eatCooldown = 2;       // cooldown until next eating
   
   // debugging contructor
   public Player() {
      super("Debug", 10);
      maxWater = 1;
      currentWater = 0;
      maxStorage = 1;
      currentStorage = 0;
      strongestWep = null;
   }

   // actual constructor
   public Player(String name, int health, int water, int storage, Loot loot) {
      super(name, health);
      maxWater = water;
      currentWater = maxWater;
      maxStorage = storage;
      currentStorage = 0;
      store = loot;
      strongestWep = null;
   }

   // returns maximum amount of water
   public int getMaxWater() {
      return maxWater;
   }
   
   // returns maximum storage
   public int getMaxtStorage() {
      return maxStorage;
   }
   
   // returns current amount of water
   public int getCurrentWater() {
      return currentWater;
   }
   
   // returns current storage amount
   public int getCurrentStorage() {
      return currentStorage;
   }
   
   // returns strongest weapon in inventory
   public Item getStrongestWeapon() {
      return strongestWep;
   }
   
   // returns inventory
   public Loot getInventory()
   {
      return store;
   }
   
   // accounts for travel costs (food, water)
   public void travel()
   {
      // decreases drinking cooldown
      drinkCooldown --;
      if (drinkCooldown == 0) // if cooldown ends
      {
         // drink and reset cooldown
         currentWater --;
         drinkCooldown = DRINKDELAY;
         
         // if not enough to drink
         if (currentWater <= 0)
         {
            // thirst to death
            setHealth(0);
         }
      }
      
      // decreases eating cooldown
      eatCooldown --;
      if (eatCooldown == 0) // if cooldown ends
      {
         // if no food present
         Item temp = store.getLoot(new Material(GameMechanics.COOKEDMEATID, 0));
         if (temp == null || temp.getAmount() == 0)
         {
            // starve to death
            setHealth(0); 
         }
         else // if food present
         {
            // consume food and reset cooldown
            temp.deduct(1);
            eatCooldown = EATDELAY;
         }
      }
   }

   // finding an outpost replenishes water and health
   public void restock()
   {
      // set water and health to max
      currentWater = maxWater;
      setHealth(getMaxHealth());
   }
   
   // deal damage equivalent to strongest weapon's damage
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

   // add an item to inventory
   public void bringItem(Item other) {
      if (currentStorage < maxStorage) {
         store.add(other);
      }
      store.sortLoot();
   }

   // return an item from inventory
   public Item returnItem(Item item) {
      return store.take(item);
   }

   // returns an item in the inventory
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
   
   // picks up items from resources
   public void prepare(Resource resource) {
      // convert resource into loot
      Loot loot = new Loot(resource);
   
      // create window for interchange
      lootWindow = new JFrame("Loot");
      lootWindow.setSize(480, 640);
      lootWindow.setResizable(false);
      lootWindow.setLocation(400, 200);
      lootWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      // set up master display
      Display masterDisplay = new Display(480, 640);
      masterDisplay.setLayout(new BoxLayout(masterDisplay, BoxLayout.PAGE_AXIS));
   
      // master display part 1: inventory loot interface
      LootDisplay lootDisplay = new LootDisplay(480, 600, store, loot);
      lootDisplay.setPreferredSize(new Dimension(480, 600));
   
      // master display part 2: exit button
      Display buttonDisplay = new Display(480, 40);
      buttonDisplay.setPreferredSize(new Dimension(480, 40));
      JButton exitButton = new JButton("Start exploration");
      exitButton.addActionListener(new MenuButtonHandler());
      buttonDisplay.add(exitButton);
   
      // combine interface with buttons to form master display
      masterDisplay.add(lootDisplay);
      masterDisplay.add(buttonDisplay);
   
      // display master display
      lootWindow.setContentPane(masterDisplay);
      lootWindow.repaint();
      lootWindow.setVisible(true);
   }

   public void receiveLoot(Loot loot) {
      // create window for interchange
      lootWindow = new JFrame("Loot");
      lootWindow.setSize(480, 640);
      lootWindow.setResizable(false);
      lootWindow.setLocation(400, 200);
      lootWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      // set up master display
      Display masterDisplay = new Display(480, 640);
      masterDisplay.setLayout(new BoxLayout(masterDisplay, BoxLayout.PAGE_AXIS));
   
      // master display part 1: inventory loot interface
      LootDisplay lootDisplay = new LootDisplay(480, 600, store, loot);
      lootDisplay.setPreferredSize(new Dimension(480, 600));
   
      // master display part 2: exit button
      Display buttonDisplay = new Display(480, 40);
      buttonDisplay.setPreferredSize(new Dimension(480, 40));
      JButton exitButton = new JButton("OK");
      exitButton.addActionListener(new MenuButtonHandler());
      buttonDisplay.add(exitButton);
   
      // combine interface with buttons to form master display
      masterDisplay.add(lootDisplay);
      masterDisplay.add(buttonDisplay);
   
      // display master display
      lootWindow.setContentPane(masterDisplay);
      lootWindow.repaint();
      lootWindow.setVisible(true);
   }

   // start exploration of the dusky path
   private void startExploration() {
      new DuskyPath(this);
   }

   // LootDisplay:
   // Child class of Display
   // Interface for interchanging items between loot and inventory
   private static class LootDisplay extends Display {
   
      // stores the two interchanging arrays
      private Loot inventory;
      private Loot loot;
   
      // constructor
      // calls addContents
      public LootDisplay(int width, int height, Loot inventory, Loot loot) {
         // set up LootDisplay
         super(width, height);
         this.inventory = inventory;
         this.loot = loot;
         
         // set layout to have 5 columns and flexible rows
         // column 1: name
         // column 2: decrease button
         // column 3: inventory amount
         // column 4: loot amount
         // column 5: increase button
         setLayout(new GridLayout(0, 5));
         
         // add interface
         addContents();
      }
   
      // add labels and buttons
      public void addContents() {
      
         // first row, text labels of the source of items
                  
         // label column 3: inventory
         JLabel inventoryLabel = new JLabel("Inventory");
         inventoryLabel.setHorizontalAlignment(SwingConstants.CENTER);  // centering label
         inventoryLabel.setForeground(textColor);                       // changing color
         
         // label column 4: loot
         JLabel lootLabel = new JLabel("Loot");    
         lootLabel.setHorizontalAlignment(SwingConstants.CENTER);    // centering label
         lootLabel.setForeground(textColor);                         // changing color
         
         add(new JLabel());   // filler
         add(new JLabel());   // filler
         add(inventoryLabel);
         add(lootLabel);
         add(new JLabel());   // filler
      
         for (int i = 0; i < inventory.length(); i++) {
            // find all inventory items first
            Item inventoryItem = inventory.getLoot(i);
            if (inventoryItem != null && inventoryItem.getAmount() > 0)    // check if item exists
            {
               // check if the item exists in loot, if not create one
               Item lootItem = loot.getLoot(inventoryItem);
               if (lootItem == null) {
                  if (inventoryItem instanceof Material) { // If instance of material
                     lootItem = new Material((Material) inventoryItem, 0); //Give material of x amount
                  } 
                  else if (inventoryItem instanceof Weapon) { //If drop is weapon
                     lootItem = new Weapon((Weapon) inventoryItem, 0); //Give weapon of x amount
                  } 
                  else if (inventoryItem instanceof Healing) { //If drop is Healing
                     lootItem = new Healing((Healing) inventoryItem, 0); //Give healing of x amount
                  }
               }
               
               // label column 1: name of item
               JLabel name = new JLabel(inventoryItem.toString()); 
               name.setForeground(textColor);                           // changing color
               
               // label column 3: inventory amount
               JLabel inventoryAmount = new JLabel("" + inventoryItem.getAmount()); 
               inventoryAmount.setHorizontalAlignment(SwingConstants.CENTER);    // centering label
               inventoryAmount.setForeground(textColor);                         // changing color
               
               // label column 4: loot amount
               JLabel lootAmount = new JLabel("" + lootItem.getAmount());
               inventoryAmount.setHorizontalAlignment(SwingConstants.CENTER);    // centering label
               inventoryAmount.setForeground(textColor);                         // changing color
               
               // button column 2: decreasing inventory amount
               JButton dropButton = new JButton("Drop");
               if (inventoryItem.getAmount() == 0) {
                  dropButton.setEnabled(false);
               }
               
               // button column 5: increasing inventory amount
               JButton takeButton = new JButton("Take");
               if (lootItem.getAmount() == 0) {
                  takeButton.setEnabled(false);
               }
               
               // button handler
               ButtonHandler handler = new ButtonHandler(inventory, loot, inventoryItem, lootItem, dropButton, inventoryAmount, lootAmount, takeButton);
               dropButton.addActionListener(handler);
               takeButton.addActionListener(handler);
               
               // adding to panel
               add(name);
               add(dropButton);
               add(inventoryAmount);
               add(lootAmount);
               add(takeButton);
            }
         }
         for (int i = 0; i < loot.length(); i++) {
            Item lootItem = loot.getLoot(i);
            // find all valid items in loot and check if not already displayed
            if (lootItem != null && inventory.getLoot(lootItem) == null) 
            {
               // create item for inventory
               Item inventoryItem;
               if (lootItem instanceof Material) { // If instance of material
                  inventoryItem = new Material((Material) lootItem, 0); //Give material of x amount
               } 
               else if (lootItem instanceof Weapon) { //If drop is weapon
                  inventoryItem = new Weapon((Weapon) lootItem, 0); //Give weapon of x amount
               } 
               else { //If drop is Healing
                  inventoryItem = new Healing((Healing) lootItem, 0); //Give healing of x amount
               }
            
               // label column 1: name of item
               JLabel name = new JLabel(inventoryItem.toString()); 
               name.setHorizontalAlignment(SwingConstants.CENTER);      // centering label
               name.setForeground(textColor);                           // changing color
               
               // label column 3: inventory amount
               JLabel inventoryAmount = new JLabel("0"); 
               inventoryAmount.setHorizontalAlignment(SwingConstants.CENTER);    // centering label
               inventoryAmount.setForeground(textColor);                         // changing color
               
               // label column 4: loot amount
               JLabel lootAmount = new JLabel("" + lootItem.getAmount());
               inventoryAmount.setHorizontalAlignment(SwingConstants.CENTER);    // centering label
               inventoryAmount.setForeground(textColor);                         // changing color
               
               // button column 2: decreasing inventory amount
               JButton dropButton = new JButton("Drop");
               if (inventoryItem.getAmount() == 0) {
                  dropButton.setEnabled(false);
               }
               
               // button column 5: increasing inventory amount
               JButton takeButton = new JButton("Take");
               if (lootItem.getAmount() == 0) {
                  takeButton.setEnabled(false);
               }
               
               // button handler
               ButtonHandler handler = new ButtonHandler(inventory, loot, inventoryItem, lootItem, dropButton, inventoryAmount, lootAmount, takeButton);
               dropButton.addActionListener(handler);
               takeButton.addActionListener(handler);
               
               // adding to panel
               add(name);
               add(dropButton);
               add(inventoryAmount);
               add(lootAmount);
               add(takeButton);
               add(takeButton);
            }
         }
      
      }
   }

   // MenuButtonHandler class:
   // Handles LootWindow buttons
   private class MenuButtonHandler implements ActionListener {
   
      // Handles exiting loot selection screen
      public void actionPerformed(ActionEvent e) {
         String s = e.getActionCommand(); // get button text
         lootWindow.dispose();
         store.updateArray();
         Item temp = store.getLoot(0);
         if (temp instanceof Weapon)
            strongestWep = temp;
         if (s.equals("Start exploration"))  // check if beginning exploreation
            startExploration();   
         else if (s.equals("OK"))            // check if you won a fight
            DuskyPath.winFight();
      }
   }
   
   // ButtonHandler class:
   // Handles loot inventory interface buttons
   private static class ButtonHandler implements ActionListener {
   
      // keeps track of all add buttons for disabling when max storage
      private static JButton[] addButtons = new JButton[0];
      
      // keeps track of various object for transfer and updates
      private final Loot inventory;
      private final Loot loot;
      private final Item inventoryItem;
      private final Item lootItem;
      private final JButton reduceButton;
      private final JLabel inventoryLabel;
      private final JLabel lootLabel;
      private final JButton addButton;
      private final int modifier;
   
      // constructor
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
   
      // a button is pressed 
      public void actionPerformed(ActionEvent e) {
         String s = e.getActionCommand(); // get button text
         if (s.equals("Take") && currentStorage + modifier <= maxStorage) // if trying to take an item and not at capacity limit
         {
            // add to inventory
            currentStorage += modifier;
            inventoryItem.addItem(lootItem.retrieve(1));
            
            // add to other array
            if (inventoryItem.getAmount() == 1) {
               inventory.add(inventoryItem);
            }
            // if all items are removed
            if (lootItem.getAmount() == 0) {
               // disable button
               addButton.setEnabled(false);
            }
            // if all items are dropped
            if (inventoryItem.getAmount() == 0)
               // disable drop buttone
               reduceButton.setEnabled(true);
               
            // update labels
            inventoryLabel.setText("" + inventoryItem.getAmount());
            lootLabel.setText("" + lootItem.getAmount());
         
            // if at max capacity
            if (currentStorage == maxStorage)
            {
               // disable all get buttons
               for (int i = 0; i < addButtons.length; i++)
               {
                  addButtons[i].setEnabled(false);
               }
            }
         
         }
         else if (s.equals("Drop")) // if trying to drop an item from an array
         {
            // remove item
            currentStorage -= modifier;
            lootItem.addItem(inventoryItem.retrieve(1));
            
            // add to other array
            if (lootItem.getAmount() == 1) {
               loot.add(lootItem);
            }
            // if dropped all items
            if (inventoryItem.getAmount() == 0) {
               // disable drop button;
               reduceButton.setEnabled(false);
            }
            // if dropped all items
            if (lootItem.getAmount() == 0) {
               // disable drop button;
               addButton.setEnabled(true);
            }
            
            // update labels
            inventoryLabel.setText("" + inventoryItem.getAmount());
            lootLabel.setText("" + lootItem.getAmount());
            
            // check capacity decreased from full to not full
            if (currentStorage == maxStorage - modifier)
            {
               // enable all increase 
               for (int i = 0; i < addButtons.length; i++)
               {
                  addButtons[i].setEnabled(true);
               }
            }
         }
      
      }
   }

}
