/*
	File Name:	 Game.java
	Name:			 Shawn Wang
	Class:		 ICS4U1
	Date:			 June 15, 2017
	Description: Main controller of game properties and function.
                Handles opening, registration, and log in function.
                Provides main operations avalaible (gathering resourcres, building upgrades / weapons)
                Provides access to exploration
                Provides save function
*/

   package Game;

// Importing needed game files
   
// for village resource gathering
   import Game.Village.Village;
   import Game.Village.Workers;

// for exploration
   import Game.DuskyPath.Player;
   import Game.Loot.Loot;

// for encrypting saves
   import Game.Encryption.Cipher;

// for graphical displays
   import Game.GUI.*; 
   import java.awt.*;
   import java.awt.event.*;
   import javax.swing.BoxLayout;
   import javax.swing.JButton;
   import javax.swing.JFrame;
   import javax.swing.JLabel;
   import javax.swing.JTextField;
   import javax.swing.SwingConstants;

// for game constants
   import Game.GameMechanics.GameMechanics;

// for handling resources
   import Game.Room.*;

// for operations
   import java.util.Arrays;
   import java.util.Timer;
   import java.util.TimerTask;

// for file input / output
   import java.io.*;

    public class Game {
   
   // displays start screen, provides registration and log in buttons
      private static JFrame startWindow;    
   
   // displays username and password text fields for user input, allows user to register or log in
      private static JFrame registrationWindow;
      private static JTextField usernameField;
      private static JTextField passwordField;
      private static String username;
      private static String password;            
    
   // displays main operations, provides in game messages, displays current resources
   // displays current villager assignment
      private static JFrame gameWindow; 
   
   // buttons for main operations in game   
      private static JButton woodButton;
      private static JButton checkTrapButton;
      private static JButton expeditionButton;
      private static JButton leatherArmorButton;
      private static JButton ironArmorButton;
      private static JButton steelArmorButton;
      private static JButton waterskinButton;
      private static JButton waterCaskButton;
      private static JButton waterTankButton;
      private static JButton rucksackButton;
      private static JButton wagonButton;
      private static JButton convoyButton;
      private static JButton woodenSwordButton;
      private static JButton ironSwordButton;
      private static JButton steelSwordButton;
      private static JButton woodenArrowButton;
      private static JButton ironArrowButton;
      private static JButton steelArrowButton;
      private static JButton buildTrapButton;
      private static JButton bowButton;
      private static JButton saveButton;
   
   // stores cooldown timers for main operations
   // constants
      private static final int WOODDELAY = 15;
      private static final int TRAPDELAY = 20;
      private static final int WORKERDELAY = 20;
      private static final int SAVEDELAY = 60;
   
   // timers
      private static int woodCooldown = 0;
      private static int trapCooldown = 0;
      private static int workerCooldown = 15;
      private static int saveCooldown = 60;
   
   // storing in game messages
      private static Messages gameMessages = new Messages();
   
   // stores difficulty and makes appropriate adjustments (not yet implemented)
      private static final int DIFFICULTY = 1;
      private Village village = new Village(60 - DIFFICULTY * 20);   //initializes village
   
   // StartScreenDisplay class:
   // child class of Display
   // draws the initial messages on screen.
       private static class StartScreenDisplay extends Display {
      
      // stores font used
         private static final Font textFont = new Font("Dialog", Font.PLAIN, 14);
      
      // stores the location of the initial text
         private static final int XPOSITION = 15;
         private static final int MESSAGEYPOSITION = 20;
         private static final int CREDITSYPOSITION = 70;
      
      // stores the names of the creators for display
         private static final String[] creators = {"Shawn Wang", "Michael Chang", "Raghav Srinivasan", "Tong Li Han", "Ian Gu"};
      
      // constructor
          public StartScreenDisplay(int width, int height) {
            super(width, height);
         }
      
      // overides PaintContents method in Display
      // called when painting / repainting the panel
      // displays message and names of creators
      // called by paintComponent method
          public void paintContents(Graphics g) {
         
         // adjusts color and font of messages
            g.setColor(textColor);
            g.setFont(textFont);
         
         // displays the messages in order
            g.drawString("Welcome to the parody of the game \"A dark room\"", XPOSITION, MESSAGEYPOSITION);
            g.drawString("Made by: ", XPOSITION, CREDITSYPOSITION);
            for (int i = 0; i < creators.length; i++)
            {
               g.drawString(creators[i], XPOSITION, CREDITSYPOSITION + (i + 1) * 25);
            }
         }// paintContents method
      
      }// StartScreenDisplay class
   
    // MenuDisplay class:
    // Child class of Display
    // Draws the buttons for the main game operations
       private static class MenuDisplay extends Display {
      
      // stores font used
         private static final Font BUTTONFONT = new Font("Dialog", Font.PLAIN, 10);
      
      // constructor
      // initializes panel layout (7 rows by 3 columns)
      // calls addContent method to add buttons
          public MenuDisplay(int width, int height) {
            super(width, height);
            setLayout(new GridLayout(7, 3));
            addContent();
         }
      
      // adds the buttons for the main game operations
      // makes adjustments as needed
      // called by constructor
          private void addContent() {
         // handles mouse clicks
            MenuButtonHandler handler = new MenuButtonHandler();
         
         // receives upgrade levels to disable buttons if curren level exceeds upgrade
            int armorLevel = Room.getArmorLevel();
            int waterLevel = Room.getWaterLevel();
            int storageLevel = Room.getStorageLevel();
            int arrowLevel = Room.getArrowLevel();
         
         // gather wood button
            woodButton = new JButton("Gather wood");
            woodButton.addActionListener(handler);
            woodButton.setToolTipText("Receive wood");
            woodButton.setFont(BUTTONFONT);
         
         // check trap button
         // disables button if the amount of traps is 0 (nothing to check)
            checkTrapButton = new JButton("Check trap");
            Item traps = Room.getInventory().findItemById(14);
            if (traps == null || traps.getAmount() == 0)
               checkTrapButton.setEnabled(false);
            checkTrapButton.setToolTipText("Receive meat, fur, scales, and teeth");
            checkTrapButton.addActionListener(handler);
            checkTrapButton.setFont(BUTTONFONT);
         
         // build trap button
         // disables if the amount of traps is greater or equal to 10 (max reached)
            buildTrapButton = new JButton("Build trap");
            buildTrapButton.setToolTipText(GameMechanics.trapCost[0].toString());
            buildTrapButton.addActionListener(handler);
            buildTrapButton.setFont(BUTTONFONT);
            if (traps != null && traps.getAmount() >= 10)
               buildTrapButton.setEnabled(false);
            
         // expedition button
            expeditionButton = new JButton("Expedition");
            expeditionButton.setToolTipText("Travel into the dusky path.");
            expeditionButton.addActionListener(handler);
            expeditionButton.setFont(BUTTONFONT);
         
         // leather armor upgrade button
         // disables if current armor level is greater or equal to leather
            leatherArmorButton = new JButton("Leather armor");
            leatherArmorButton.addActionListener(handler);
            leatherArmorButton.setToolTipText(GameMechanics.armorUpgrades[0].toString());
            leatherArmorButton.setFont(BUTTONFONT);
            if (armorLevel >= 1)
               leatherArmorButton.setEnabled(false);
            
         // iron armor upgrade button
         // disables if current armor level is greater or equal to iron
            ironArmorButton = new JButton("Iron armor");
            ironArmorButton.addActionListener(handler);
            ironArmorButton.setToolTipText(GameMechanics.armorUpgrades[1].toString());
            ironArmorButton.setFont(BUTTONFONT);
            if (armorLevel >= 2)
               ironArmorButton.setEnabled(false);
            
         // steel armor upgrade button
         // disables if current armor level is equal to steel (max level)
            steelArmorButton = new JButton("Steel armor");
            steelArmorButton.addActionListener(handler);
            steelArmorButton.setToolTipText(GameMechanics.armorUpgrades[2].toString());
            steelArmorButton.setFont(BUTTONFONT);
            if (armorLevel >= 3)
               steelArmorButton.setEnabled(false);
            
         // waterskin upgrade button
         // disables if current water storage is or is better than waterskin
            waterskinButton = new JButton("Waterskin");
            waterskinButton.addActionListener(handler);
            waterskinButton.setToolTipText(GameMechanics.waterUpgrades[0].toString());
            waterskinButton.setFont(BUTTONFONT);
            if (waterLevel >= 1)
               waterskinButton.setEnabled(false);
            
         // water cask upgrade button
         // disables if current water storage is or is better than water cask
            waterCaskButton = new JButton("Cask");
            waterCaskButton.addActionListener(handler);
            waterCaskButton.setToolTipText(GameMechanics.waterUpgrades[1].toString());
            waterCaskButton.setFont(BUTTONFONT);
            if (waterLevel >= 2)
               waterCaskButton.setEnabled(false);
            
         // water tank upgrade button
         // disables if current water storage is water tank (max level)   
            waterTankButton = new JButton("Water tank");
            waterTankButton.addActionListener(handler);
            waterTankButton.setToolTipText(GameMechanics.waterUpgrades[2].toString());
            waterTankButton.setFont(BUTTONFONT);
            if (waterLevel >= 3)
               waterTankButton.setEnabled(false);
            
         // wooden sword purchase button
            woodenSwordButton = new JButton("Wooden sword");
            woodenSwordButton.addActionListener(handler);
            woodenSwordButton.setToolTipText(GameMechanics.swordUpgrades[0].toString());
            woodenSwordButton.setFont(BUTTONFONT);
         
         // wooden sword purchase button
            ironSwordButton = new JButton("Iron sword");
            ironSwordButton.addActionListener(handler);
            ironSwordButton.setToolTipText(GameMechanics.swordUpgrades[1].toString());
            ironSwordButton.setFont(BUTTONFONT);
         
         // wooden sword purchase button
            steelSwordButton = new JButton("Steel sword");
            steelSwordButton.addActionListener(handler);
            steelSwordButton.setToolTipText(GameMechanics.swordUpgrades[2].toString());
            steelSwordButton.setFont(BUTTONFONT);
         
         // rucksack upgrade button
         // disables if current storage is or is better than racksack   
            rucksackButton = new JButton("Rucksack");
            rucksackButton.addActionListener(handler);
            rucksackButton.setToolTipText(GameMechanics.storageUpgrades[0].toString());
            rucksackButton.setFont(BUTTONFONT);
            if (storageLevel >= 1)
               rucksackButton.setEnabled(false);
         
         // wagon upgrade button
         // disables if current storage is or is better than wagon 
            wagonButton = new JButton("Wagon");
            wagonButton.addActionListener(handler);
            wagonButton.setToolTipText(GameMechanics.storageUpgrades[1].toString());
            wagonButton.setFont(BUTTONFONT);
            if (storageLevel >= 2)
               wagonButton.setEnabled(false);
            
         // convoy upgrade button
         // disables if current storage is or is better than convoy 
            convoyButton = new JButton("Convoy");
            convoyButton.addActionListener(handler);
            convoyButton.setToolTipText(GameMechanics.storageUpgrades[2].toString());
            convoyButton.setFont(BUTTONFONT);
            if (storageLevel >= 3)
               convoyButton.setEnabled(false);
            
         // wooden arrow upgrade button
         // disables if the current arrow is or is stronger than wooden
            woodenArrowButton = new JButton("Wooden arrow");
            woodenArrowButton.addActionListener(handler);
            woodenArrowButton.setToolTipText(GameMechanics.arrowUpgrades[0].toString());
            woodenArrowButton.setFont(BUTTONFONT);
            if (arrowLevel >= 1)
               woodenArrowButton.setEnabled(false);
         
         // iron arrow upgrade button
         // disables if the current arrow is or is stronger than iron
            ironArrowButton = new JButton("Iron arrow");
            ironArrowButton.addActionListener(handler);
            ironArrowButton.setToolTipText(GameMechanics.arrowUpgrades[1].toString());
            ironArrowButton.setFont(BUTTONFONT);
            if (arrowLevel >= 2)
               ironArrowButton.setEnabled(false);
            
         // steel arrow upgrade button
         // disables if the current arrow is or is steel than iron
            steelArrowButton = new JButton("Steel arrow");
            steelArrowButton.addActionListener(handler);
            steelArrowButton.setToolTipText(GameMechanics.arrowUpgrades[2].toString());
            steelArrowButton.setFont(BUTTONFONT);
            if (arrowLevel >= 3)
               steelArrowButton.setEnabled(false);
            
         // save button
            saveButton = new JButton("Save");
            saveButton.addActionListener(handler);
            saveButton.setToolTipText("Saves progress. Automatic save every minute.");
            saveButton.setFont(BUTTONFONT);
         
         // bow purchase button
            bowButton = new JButton("Bow");
            bowButton.addActionListener(handler);
            bowButton.setToolTipText("Requires arrow upgrades to shoot. " + GameMechanics.bowCost[0]);
            bowButton.setFont(BUTTONFONT);
         
         // adds the buttons in order
         
         // most used
            add(woodButton);
            add(checkTrapButton);
            add(buildTrapButton);
         
         // upgrades
         
         // water upgrades
            add(waterskinButton);
            add(waterCaskButton);
            add(waterTankButton);
         
         // storage upgrades
            add(rucksackButton);
            add(wagonButton);
            add(convoyButton);
         
         // armor upgrades
            add(leatherArmorButton);
            add(ironArmorButton);
            add(steelArmorButton);
         
         // arrow upgrades
            add(woodenArrowButton);
            add(ironArrowButton);
            add(steelArrowButton);
         
         // sword purchase buttons
            add(woodenSwordButton);
            add(ironSwordButton);
            add(steelSwordButton);
         
         // bow purchase button
            add(bowButton);
         
         // other buttons
            add(expeditionButton);
            add(saveButton);
         
         }
      }
      
    // GameDisplay class:
    // Child class of Display
    // Draws in game messages
       private static class GameDisplay extends Display {
      
      // stores position of initial messages
         private static final int MESSAGEXPOSITION = 20;
         private static final int MESSAGEINITIALYPOSITION = 20;
      
      // constuctor
          public GameDisplay(int width, int height) {
            super(width, height);
         }
      
      // paints the in game messages
          public void paintContents(Graphics g) {
         
         // sets text color and font
            g.setColor(textColor);
            g.setFont(new Font("Dialog", Font.PLAIN, 14));
         
         // draws all of the messages
            String[] text;
            int xPosition = MESSAGEXPOSITION;
            int yPosition = MESSAGEINITIALYPOSITION;
            for (int i = 0; (text = gameMessages.getText(i)) != null; i++) {
               for (int j = 0; j < text.length && text[j] != null; j++) {
                  g.drawString(text[j], xPosition, yPosition);
                  yPosition += LINESPACING;
               }
               yPosition += TEXTSPACING;
            }
         }// paintContents method
      
      }// GameDisplay class
   
    // ResourceDisplay class:
    // Child class of Display
    // Draws in game resources
       private static class ResourceDisplay extends Display {
      
      // stores font used
         private static final Font TEXTFONT = new Font("Dialog", Font.PLAIN, 14);
      
      // stores the initial position of messages
         private static final int RESOURCEXPOSITION = 20;
         private static final int RESOURCEINITIALYPOSITION = 20;
      
      // constructor
          public ResourceDisplay(int width, int height) {
            super(width, height);
         }
      
       // paints the amount of each resource
          public void paintContents(Graphics g) {
            g.setColor(textColor);
            g.setFont(TEXTFONT);
            int xPosition = RESOURCEXPOSITION;
            int yPosition = RESOURCEINITIALYPOSITION;
         
            Item[] temp = Room.getInventory().getItems();
            for (int i = 0; i < temp.length && temp[i] != null; i++) {
               g.drawString(temp[i].getName() + ": " + temp[i].getAmount(), xPosition, yPosition);
               yPosition += LINESPACING;
            }
         }
      }
   
    // ResourceDisplay class:
    // Child class of Display
    // Draws ithe amount of villagers on each job
    // Adds buttons to assign jobs to villagers
       private static class VillageDisplay extends Display {
      
      // stores font used
         private static final Font BUTTONSFONT = new Font("Dialog", Font.PLAIN, 20);
      
       // constructor
       // calls addContents method to add buttons
          public VillageDisplay(int width, int height) {
            super(width, height);
            addContents();
         }
      
       // adds buttons for assigning jobs to villager
       // called by constructor
          private void addContents() {
         
         // sets the panel into 4 columns with variable rows
         // column 1: job name (JLabel), pops up resource gathered per worker
         // column 2: decrease button, decreases worker by 1
         // column 3: amount of workers
         // column 4: increase button, increases worker by 1
            setLayout(new GridLayout(0, 4));
         
         // gatherer: default job for villagers
         // no increase / decrease buttons
         // all unassigned villagers are gatherers, all assignments take away gatherers
         
         // finding gatherer
            Workers temp = Village.getWorker(0);
         
         // creating name label
            JLabel name = new JLabel(temp.toString());
            name.setForeground(textColor);                           // changes label color
            name.setHorizontalAlignment(SwingConstants.CENTER);      // centers label
            name.setToolTipText(temp.getUnitResource().toString());  // adds pop up text of resource gathered / consumed per villager
         
         // creating amount label
            JLabel amount = new JLabel("" + temp.getNumWorkers());
            amount.setForeground(textColor);                         // changes label color
            amount.setHorizontalAlignment(SwingConstants.CENTER);    // centers label
         
         // adding to panel
            add(name);           // column 1: name label
            add(new JLabel());   // column 2: no decrease button for gatherer, placeholder for formatting
            add(amount);         // column 3: amount label
            add(new JLabel());   // column 4: no increase button for gatherer, placeholder for formatting
         
         // initialize button handler
            ButtonHandler.initialize(temp, amount);
         
         // finds other workers
            int i = 1;
            while ((temp = Village.getWorker(i)) != null) {
            
            // creating name label
               name = new JLabel(temp.toString());
               name.setForeground(textColor);                           // changes label color
               name.setHorizontalAlignment(SwingConstants.CENTER);      // centers label
               name.setToolTipText(temp.getUnitResource().toString());  // adds pop up text of resource gathered / consumed per villager
            
            // creating amount label
               amount = new JLabel("" + temp.getNumWorkers());
               amount.setForeground(textColor);                         // changes label color
               amount.setHorizontalAlignment(SwingConstants.CENTER);    // centers label
            
            // creates increase and decrease buttons
               JButton decreaseButton = new JButton("-");
               decreaseButton.setFont(BUTTONSFONT);         // adjusts font
               JButton increaseButton = new JButton("+");
               increaseButton.setFont(BUTTONSFONT);         // adjusts font
            
            // creates a button handler for the two buttons of the same worker
               ButtonHandler handler = new ButtonHandler(temp, amount, decreaseButton, increaseButton);
            
            // attaches button handler
               decreaseButton.addActionListener(handler);
               increaseButton.addActionListener(handler);
            
            // disable buttons if necessary
               if (Village.getNumWorkers(i) == 0)
                  decreaseButton.setEnabled(false);      // disables decrease button if no remaining worker
               else if (Village.getNumWorkers(0) == 0)
                  increaseButton.setEnabled(false);      // disables increase button if no free worker
               
            // addint to panel
               add(name);              // column 1: name label
               add(decreaseButton);    // column 2: decrease button 
               add(amount);            // column 3: amount label
               add(increaseButton);    // column 4: increase button
            
            // move to next worker
               i++;
            }
         }
      
      // ButtonHandler class:
      // deals with button presses for villager display
          private static class ButtonHandler implements ActionListener {
         
         // stores gatherer instance and its label
         // updated according to action
            static Workers gatherer;
            static JLabel gathererLabel;
         
         // stores all increase buttons for disabling when no free workers 
            static JButton[] addButtons = new JButton[0];
         
         // stores the worker instance and its amount label associated with the buttons
            Workers worker;
            JLabel workerLabel;
         
         // stores removeButton for disabling when no remaining workers
            JButton removeButton;
         
          // constructor
             public ButtonHandler(Workers worker, JLabel workerLabel, JButton removeButton, JButton addButton) {
               this.worker = worker;
               this.workerLabel = workerLabel;
               this.removeButton = removeButton;
               addButtons = Arrays.copyOf(addButtons, addButtons.length + 1);
               addButtons[addButtons.length - 1] = addButton;
            }
         
          // initializes fields
             public static void initialize(Workers worker, JLabel label) {
               gatherer = worker;
               gathererLabel = label;
            }
           
          // handles button presses
          // called when the increase or decrease button is pressed
             public void actionPerformed(ActionEvent e) {
               String s = e.getActionCommand();    // gets the button text
               int numGatherers;                   // stores number of gatherers
               int numWorkers;                     // stores number of workers
            // check for button press 
               switch (s) {
               // if remove button is pressed
                  case "-":
                  // removes a worker
                     worker.removeWorkers();
                  
                  // finds new worker amount
                     numWorkers = worker.getNumWorkers();
                  
                  // updates amount label
                     workerLabel.setText("" + numWorkers);
                  
                  // disables remove button if no remaining workers
                     if (numWorkers == 0) 
                        removeButton.setEnabled(false);
                  
                  // assign removed worker to unassigned state (gatherer)
                     gatherer.addWorkers();
                  
                  // finds new gatherer amount
                     numGatherers = gatherer.getNumWorkers();
                  
                  // updates amount label
                     gathererLabel.setText("" + numGatherers);
                  
                  // check if gatherers changes from none to some
                     if (numGatherers == 1)
                     // if yes, enable all increase buttons since there is free villagers
                        for (int i = 0; i < addButtons.length; i++)
                           addButtons[i].setEnabled(true);
                     break;
                  
               // if increase button is pressed
                  case "+":
                  // adds a worker
                     worker.addWorkers();
                  
                  // finds new worker amount
                     numWorkers = worker.getNumWorkers();
                  
                  // updates amount label
                     workerLabel.setText("" + numWorkers);
                  
                  // checks if workers increase from none to some
                     if (numWorkers == 1)
                     // if yes, enable decrease button since there is remaining workers
                        removeButton.setEnabled(true);
                  
                  // removes the assigned worker from gatherers
                     gatherer.removeWorkers();
                  
                  // finds new gatherer amount
                     numGatherers = gatherer.getNumWorkers();
                  
                  // updates amount label
                     gathererLabel.setText("" + numGatherers);
                  
                  // check if no gatherer remains
                     if (numGatherers == 0)
                     // if yes, disable all increase buttons since there is no free workers
                        for (int i = 0; i < addButtons.length; i++)
                           addButtons[i].setEnabled(false);
                     break;
                  
                  default:
                     break;
               }
            }
         
         }
      }
   
    // MenuButtonHandler class:
    // Deals with button presses in the menu display 
       private static class MenuButtonHandler implements ActionListener {
      
       // handles button presses
       // called when a menu display button is pressed
          public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();  // gets the button text
            String[] text;                         // stores messages to be added to the game display if more than one line is needed.
         // check for button pres
            switch (action) {
            // if gather wood button is pressed
               case "Gather wood":
               // calls gather wood function to get resources
                  Room.gatherWood();
               
               // disable button
                  woodButton.setEnabled(false);
               
               // adds message to game display
                  gameMessages.addText("You gathered some wood branches from a nearby forest.");
               
               // start cooldown period
                  woodCooldown = WOODDELAY;
               
               // set button text to display cooldown
                  woodButton.setText("Cooldown: " + woodCooldown);
                  break;
               
            // if check trap button is pressed
               case "Check trap":
               // calls check trap function to get resources
                  Room.checkTrap();
               
               // disable button
                  checkTrapButton.setEnabled(false);
               
               // adds messages to game display
                  gameMessages.addText("Checking your traps, you have found some needed resources.");
               
               // start cooldown period
                  trapCooldown = TRAPDELAY;
               
               // set button text to display cooldown
                  checkTrapButton.setText("Cooldown: " + trapCooldown);
                  break;
               
            // if expedition button is pressed
               case "Expedition":
               // finds the amount of cooked meat in the player's inventory
               // check if there is an sufficient amount to start an expedition
               
                  Item cookedMeat = Room.getInventory().findItemById(7);
               
                  if (cookedMeat != null && cookedMeat.getAmount() > 10)      // if there is a sufficient amount
                  {
                  // clear game messages
                     gameMessages.clearTexts();
                  
                  // hide game window
                     gameWindow.setVisible(false);
                  
                  // add 10 cooked meat as initial inventory
                     Item[] items = {cookedMeat.retrieve(10)};
                     Loot store = new Loot(items);
                  
                  // creates player
                     Player player = new Player(username, Room.getMaxHealth(), Room.getMaxWater(), Room.getMaxStorage(), store);
                  
                  // let player get items for the expedition
                     player.prepare(Room.getInventory());
                  }
                  else  // if there is not a sufficient amount
                  {
                  // add message to game display
                     gameMessages.addText("Not enough cooked meat to start expedition.");
                  }
                  break;
            
            // if build trap button is pressed
               case "Build trap":
               // try to build a trap
                  if (Room.buildTrap())   // if successful
                  {	
                  // add message to game display
                     gameMessages.addText("Building more traps for more resources.");
                  
                  // find current number of traps
                     int numTraps = Room.getInventory().findItemById(14).getAmount();
                  
                  // check for limitations
                     if (numTraps == 10)  // if max reached
                     // disable build trap button
                        buildTrapButton.setEnabled(false);
                     else if (numTraps == 1) // if first trap built
                     // enable check trap button
                        checkTrapButton.setEnabled(true);
                  }
                  break;
               
            // if leather armor button is pressed
               case "Leather armor":
               // try to upgrade armor
                  if (Room.upgradeArmor(1)) // if successful
                  {
                  // add message to game display
                     gameMessages.addText("Leather won't offer much protection, but is better than having nothing.");
                  
                  // disable past upgrades
                     leatherArmorButton.setEnabled(false);
                  }
                  break;
               
            // if iron armor button is pressed
               case "Iron armor":
               // try to upgrade armor
                  if (Room.upgradeArmor(2))  // if successful
                  {
                  // add message to game display
                     gameMessages.addText("Iron armor. Decent levels of protection. Can still be better.");
                  // disable past upgrades
                     leatherArmorButton.setEnabled(false);
                     ironArmorButton.setEnabled(false);
                  }
                  break;
               
            // if steel armor button is pressed
               case "Steel armor":
               // try to upgrade armor
                  if (Room.upgradeArmor(3))  // if successful
                  {
                  // add message to game display
                     gameMessages.addText("Steel armor. The best protection available for exploration.");
                  // disable past upgrades
                     leatherArmorButton.setEnabled(false);
                     ironArmorButton.setEnabled(false);
                     steelArmorButton.setEnabled(false);
                  }
                  break;
               
            // if waterskin button is pressed
               case "Waterskin":
               // try to upgrade water storage
                  if (Room.upgradeWater(1))  // if successful
                  {
                  // add message to game display
                     text = new String[2];
                     text[0] = "Water capacity increased. You can now journey farther into the wilderness.";
                     text[1] = "Waterskin unlocked, but it won't hold much water.";
                     gameMessages.addText(text);
                  // disable past upgrades
                     waterskinButton.setEnabled(false);
                  }
                  break;
               
            // if water cask button is pressed
               case "Cask":
               // try to upgrade water storage
                  if (Room.upgradeWater(2))  // if successful
                  {
                  // add message to game display
                     text = new String[2];
                     text[0] = "Water capacity increased. You can now journey farther.";
                     text[1] = "Water cask unlocked. Water will be less of a concern";
                     gameMessages.addText(text);
                  // disable past upgrades
                     waterskinButton.setEnabled(false);
                     waterCaskButton.setEnabled(false);
                  }
                  break;
               
            // if water tank button is pressed
               case "Water tank":
               // try to upgrade water storage
                  if (Room.upgradeWater(3))  // if successful
                  {
                  // add message to game display
                     text = new String[2];
                     text[0] = "Water capacity increased. You can now journey farther.";
                     text[1] = "Water tank unlocked, it should be sufficient for most explorations.";
                     gameMessages.addText(text);
                  // disable past upgrades
                     waterskinButton.setEnabled(false);
                     waterCaskButton.setEnabled(false);
                     waterTankButton.setEnabled(false);
                  }
                  break;
               
            // if rucksack button is pressed
               case "Rucksack":
               // try to upgrade storage
                  if (Room.upgradeStorage(1))  // if successful
                  {
                  // add message to game display
                     text = new String[2];
                     text[0] = "Storage capacity increased. You can now carry more items.";
                     text[1] = "Rucksack unlocked. It does not carry much.";
                     gameMessages.addText(text);
                  // disable past upgrades
                     rucksackButton.setEnabled(false);
                  }
                  break;
               
            // if wagon button is pressed
               case "Wagon":
               // try to upgrade storage
                  if (Room.upgradeStorage(2))  // if successful
                  {
                  // add message to game display
                     text = new String[2];
                     text[0] = "Storage capacity increased. You can now carry more items.";
                     text[1] = "Wagon unlocked. Sufficient for most of your items.";
                     gameMessages.addText(text);
                  // disable past upgrades
                     rucksackButton.setEnabled(false);
                     wagonButton.setEnabled(false);
                  }
                  break;
               
            // if convoy button is pressed
               case "Convoy":
               // try to upgrade storage
                  if (Room.upgradeStorage(3))  // if successful
                  {
                  // add message to game display
                     text = new String[2];
                     text[0] = "Storage capacity increased. You can now carry more items.";
                     text[1] = "Convoy unlocked.";
                     gameMessages.addText(text);
                  // disable past upgrades
                     rucksackButton.setEnabled(false);
                     wagonButton.setEnabled(false);
                     convoyButton.setEnabled(false);
                  }
                  break;
               
            // if wooden sword button is pressed
               case "Wooden sword":
               // try to craft a wood sword 
                  if (Room.buildSword(1)) // if successful
                  {
                  // add message to game display
                     gameMessages.addText("Crafted a wooden sword.");
                  }
                  break;
               
            // if iron sword button is pressed
               case "Iron sword":
               // try to craft an iron sword 
                  if (Room.buildSword(2)) // if successful
                  {
                  // add message to game display
                     gameMessages.addText("Crafted an iron sword.");
                  }
                  break;
               
            // if steel sword button is pressed
               case "Steel sword":
               // try to craft a steel sword 
                  if (Room.buildSword(3)) // if successful
                  {
                  // add message to game display
                     gameMessages.addText("Crafted a steel sword.");
                  }
                  break;
               
            // if bow button is pressed
               case "Bow":
               // try to craft a bow 
                  if (Room.buildBow()) // if successful
                  {
                  // add message to game display
                     gameMessages.addText("Crafted a simple bow");
                  }
                  break;
               
            // if wood arrow button is pressed
               case "Wood arrow":
               // try to upgrade arrows
                  if (Room.upgradeArrows(1))  // if successful
                  {
                  // add message to game display
                     gameMessages.addText("Wooden arrows. You may now use the bow in your battles.");
                  // disable past upgrades
                     woodenArrowButton.setEnabled(false);
                  }
                  break;
               
            // if iron arrow button is pressed
               case "Iron arrow":
               // try to upgrade arrows
                  if (Room.upgradeArrows(2))  // if successful
                  {
                  // add message to game display
                     gameMessages.addText("Iron arrows. You now do more damage with your bow.");
                  // disable past upgrades
                     woodenArrowButton.setEnabled(false);
                     ironArrowButton.setEnabled(false);
                  }
                  break;
               
            // if steel aroow button is pressed
               case "Steel arrow":
               // try to upgrade arrows
                  if (Room.upgradeArrows(3))  // if successful
                  {
                  // add message to game display
                     gameMessages.addText("Steel arrows. You now do significant damage with your bow.");
                  // disable past upgrades
                     woodenArrowButton.setEnabled(false);
                     ironArrowButton.setEnabled(false);
                     steelArrowButton.setEnabled(false);
                  }
                  break;
               
            // if save button is pressed
               case "Save":
               // saves file
                  save();
               
               // add message to game display
                  gameMessages.addText("Game saved");
                  break;
               
            // if lights button is pressed (removed from game)
               case "Lights":
                  // switch lights
                  Display.lights();
               
               // add message to game display
                  gameMessages.addText("Toggling lights.");
                  break;
               
               
               default:
                  break;
            }
         // refreshes window
            gameWindow.repaint();
         }
      }
   
   // StartScreenButtonHandler class: 
   // handlers button presses from the start screen and registration screen
       private static class StartScreenButtonHandler implements ActionListener {
      
       // handles button presses
       // deals with buttons from start screen and registration window
          public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();        // get button text
            if (action.equals("Start new game"))         // if user chooses to start a new game
            // user registers an account
               registration();
            else if (action.equals("Load saved game"))   // if user chooses to load a saved game
            // user logs int to their account
               logIn();
            else if (action.equals("Register"))          // if the user registers a new account
            {
               username = usernameField.getText();       // gets current username
               password = passwordField.getText();       // gets current password
               if (username != null && password != null) // if the fields are not empty 
               {
               // checks if the account exists
                  File profile = new File("Game\\saves\\" + username + ".txt");
                  if (!profile.exists())  // if not
                  {
                  // record user information and starts new game
                     try {
                        FileWriter out = new FileWriter(profile);
                        BufferedWriter writer = new BufferedWriter(out);
                     
                     // Creates a new txt file with username as file name and with password as first line
                        writer.write(password);
                     
                        writer.close();
                        out.close();
                     
                     // closes registration window
                        registrationWindow.dispose();
                     
                     // starts game
                        startGame();
                     } 
                         catch (IOException exception) {
                           System.out.println(exception.getMessage());
                        }
                  } 
                  else  // if account exitsts
                  {
                  // prompt for another username
                     usernameField.setText("Error. Profile already exists.");
                  }
               }
            } 
            else if (action.equals("Log In"))   // if the user logs in to an account
            {
               username = usernameField.getText();       // gets the current username
               password = passwordField.getText();       // gets the current password
               if (username != null && password != null) // if the fields are not empty
               {
               // checks if the profile exists
                  File profile = new File("Game\\saves\\" + username + ".txt");
                  if (profile.exists()) // if it exists
                  {
                  // check if the password matches
                     try {
                        FileReader in = new FileReader(profile);
                        BufferedReader reader = new BufferedReader(in);
                        if (password.equals(reader.readLine()))   // if password matches
                        {
                        // close registration window
                           registrationWindow.dispose();
                        
                        // load save data if any
                           load();
                        
                        // starts game
                           startGame();
                        } 
                        else  // if password do not match
                        {
                           passwordField.setText("Error. Wrong password.");
                        }
                     } 
                         catch (IOException exception) {
                           System.out.println(exception.getMessage());
                        }
                  } 
                  else // if the account do not exists
                  {
                     usernameField.setText("Error. Profile does not exist.");
                  }
               }
            } // if the user chooses to exit the registration window
            else if (action.equals("Exit")) {
            // closes the registration window
               registrationWindow.dispose();
            }
         }
      
      }
   
   // starts the game
   // loads game window
   // displays resource and villager information
   // displays main operation buttons
       public static void startGame() {
      
      // hides and closes the start window
         startWindow.setVisible(false);
         startWindow.dispose();
      
      // creates game window
         gameWindow = new JFrame("The dark room");
         gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // set default close operation
         gameWindow.setSize(1200, 700);                              // set size of window
         gameWindow.setLocation(0, 100);                             // set location
         gameWindow.setResizable(false);                             // set non-resizable (resizing GUI components causes weird displays)
      
      // creates the main display for game window
      // holds other displays in its layout
         Display mainDisplay = new Display(1200, 700);
         mainDisplay.setLayout(new GridLayout(1, 3));
      
      // main display part 1: menu screen
         MenuDisplay buttonScreen = new MenuDisplay(400, 700);
         buttonScreen.setPreferredSize(new Dimension(400, 700));
      
      // main display part 2: game messages screen
         GameDisplay storyScreen = new GameDisplay(400, 700);
         storyScreen.setPreferredSize(new Dimension(400, 700));
      
      // main display part 3: resources screen
         Display resourceDisplay = new Display(400, 700);
         resourceDisplay.setLayout(new GridLayout(2, 1));
         resourceDisplay.setPreferredSize(new Dimension(400, 700));
      
      // resources screen part 1: inventory resources
         ResourceDisplay inventoryScreen = new ResourceDisplay(400, 700);
         inventoryScreen.setPreferredSize(new Dimension(400, 400));
      
      // resources screen part 2: villagers
         VillageDisplay villageDisplay = new VillageDisplay(400, 700);
         villageDisplay.setPreferredSize(new Dimension(400, 300));
      
      // combine inventory display and villagers display to form resource display
         resourceDisplay.add(inventoryScreen);
         resourceDisplay.add(villageDisplay);
      
      // combine menu, game messages, and resources to form main display
         mainDisplay.add(buttonScreen);
         mainDisplay.add(storyScreen);
         mainDisplay.add(resourceDisplay);
      
      // display main display
         gameWindow.setContentPane(mainDisplay);
         gameWindow.repaint();
         gameWindow.setVisible(true);
      
      // start timer functions
         Timer timer = new Timer();
      
         TimerTask task = 
             new TimerTask() {
                public void run() {
                  // check for wood cooldown
                  if (woodCooldown > 0) // if on cooldown
                  {
                      // decrease cooldown
                     woodCooldown--;
                      
                      // update button text
                     woodButton.setText("Cooldown: " + woodCooldown);
                     if (woodCooldown == 0) // if cooldown period ends
                     {
                         // enable button
                        woodButton.setEnabled(true);
                         
                         // reset button text
                        woodButton.setText("Gather wood");
                     }
                  }
                   
                   // check for trap cooldown
                  if (trapCooldown > 0) // if on cooldown
                  {
                      // decrease cooldown
                     trapCooldown--;
                      
                      // update button text
                     checkTrapButton.setText("Cooldown: " + trapCooldown);
                     if (trapCooldown == 0) // if cooldown period ends
                     {
                         // enable button
                        checkTrapButton.setText("Check trap");
                         
                         // reset button text
                        checkTrapButton.setEnabled(true);
                     }
                  }
                   // check for worker cooldown
                  if (workerCooldown > 0) // if on cooldown
                  {
                      // decrease cooldown
                     workerCooldown--;
                     if (workerCooldown == 0) // if cooldown period ends
                     {
                         // let villagers gather resources and attemp to add to current inventory
                        Resource[] resources = Village.gatherResources();
                        for (int i = 0; i < resources.length; i++)
                           Room.changeResources(resources[i]);
                         
                         // refresh window
                        gameWindow.repaint();
                         
                         // start worker cooldown
                        workerCooldown = WORKERDELAY;
                        gameMessages.addText("Your villagers gathered some resources.");
                     }
                  }
                   // check for auto save cooldown
                  if (saveCooldown > 0) // if on cooldown
                  {
                      // decrease cooldown
                     saveCooldown--;
                     if (saveCooldown == 0) // if cooldown period ends
                     {
                         // save file
                        save();
                         
                         // add message to game display
                        gameMessages.addText("Auto save.");
                         
                         // start auto save cooldown
                        saveCooldown = 60;
                     }
                  }
               }
            };
         timer.scheduleAtFixedRate(task, 0, 1000); //1000ms = 1sec
      }
   
   // encrypts data and saves it into a file
   // called by save method
   // uses Cipher class's encryption methods
       public static void save() {
         File profile = new File("Game\\saves\\" + username + ".txt");
         if (profile.exists()) // check if profile exists
         {
            try {
               FileWriter out = new FileWriter(profile);
               BufferedWriter writer = new BufferedWriter(out);
            
            // record all information in order:
            // password
            // item amounts from ID = 0 to ID = 14 (excludes ID == 9, not actual item)
            // villager assignment
            // upgrade levels
            // lights toggled
            
            // record password
               writer.write(password);
               writer.newLine();
            
            // record item amounts
               for (int i = 0; i < GameMechanics.itemNames.length; i++) {
                  Item temp = Room.getInventory().findItemById(i);
                  if (i != GameMechanics.FISTID) {
                     if (temp == null) {
                        writer.write(Cipher.encrypt(0));
                     } 
                     else {
                        writer.write(Cipher.encrypt(temp.getAmount()));
                     }
                     writer.newLine();
                  }
               }
            
            // record villager asignment
               for (int i = 0; i < Village.length(); i++) {
                  writer.write(Cipher.encrypt(Village.getNumWorkers(i)));
                  writer.newLine();
               }
            
            // record upgrade levels
               writer.write(Cipher.encrypt(Room.getArmorLevel()));
               writer.newLine();
               writer.write(Cipher.encrypt(Room.getStorageLevel()));
               writer.newLine();
               writer.write(Cipher.encrypt(Room.getArrowLevel()));
               writer.newLine();
               writer.write(Cipher.encrypt(Room.getWaterLevel()));
               writer.newLine();
            
            // record toggled lighting
               writer.write(Cipher.encrypt(Display.getLighting()));
            
               writer.close();
               out.close();
            } 
                catch (IOException exception) {
                  System.out.println(exception.getMessage());
               }
         } 
         else // if profile does not exists
         {
            usernameField.setText("Error. Profile does not exist.");
         }
      }
   
   // loads from a saved file
   // called by log in method
   // uses Cipher class's decrypton methods
       public static void load() {
         File profile = new File("Game\\saves\\" + username + ".txt");
         try {
            FileReader in = new FileReader(profile);
            BufferedReader reader = new BufferedReader(in);
         
         // reads data in order
         // password
         // item amounts
         // villager assignment
         // upgrade levels
         // lights toggled
         
         // reads password
            reader.readLine();
         
         // reads item amounts (ID = 0 to ID = 6)
            Item[] items = new Item[0];
            int amount;
            for (int i = 0; i < GameMechanics.COOKEDMEATID; i++) {
               amount = Cipher.decryptToInt(reader.readLine());
               if (amount > 0) {
                  items = Arrays.copyOf(items, items.length + 1);
                  items[items.length - 1] = new Material(i, amount);
               }
            }
         
         // reads item amount ID = 7
            amount = Cipher.decryptToInt(reader.readLine());
            if (amount > 0) {
               items = Arrays.copyOf(items, items.length + 1);
               items[items.length - 1] = new Healing(GameMechanics.COOKEDMEATID, amount, 8);
            }
         
         // reads item amount ID = 8
            amount = Cipher.decryptToInt(reader.readLine());
            if (amount > 0) {
               items = Arrays.copyOf(items, items.length + 1);
               items[items.length - 1] = new Material(GameMechanics.BAITID, amount);
            }
         
         // reads item amounts ID = 10 to ID = 12
            for (int i = GameMechanics.WOODSWORDID; i <= GameMechanics.STEELSWORDID; i++) {
               amount = Cipher.decryptToInt(reader.readLine());
               if (amount > 0) {
                  items = Arrays.copyOf(items, items.length + 1);
                  items[items.length - 1] = new Weapon(GameMechanics.swords[i - GameMechanics.WOODSWORDID + 1], amount);
               }
            }
         
         // reads item amount ID = 13
            amount = Cipher.decryptToInt(reader.readLine());
            if (amount > 0) {
               items = Arrays.copyOf(items, items.length + 1);
               items[items.length - 1] = new Weapon(GameMechanics.compoundBow, amount);
               if (Room.getArrowLevel() <= 0)
               {
                  ((Weapon)(items[items.length - 1])).setStrength(0);
               }
               else
               {
                  ((Weapon)(items[items.length - 1])).setStrength(GameMechanics.BOWDAMAGE + GameMechanics.ARROWDAMAGE[Room.getArrowLevel() - 1]);
               }
            
            }
         
         // reads item amount ID = 114
            amount = Cipher.decryptToInt(reader.readLine());
            if (amount > 0) {
               items = Arrays.copyOf(items, items.length + 1);
               items[items.length - 1] = new Material(GameMechanics.BAITID, amount);
            }
         
         // stores items
            Resource inventory = new Resource(items);
         
         // reads worker assignment
            int[] workers = new int[Village.length()];
            for (int i = 0; i < Village.length(); i++) {
               workers[i] = Cipher.decryptToInt(reader.readLine());
            }
         
         // reads upgrade levels
            int armor = Cipher.decryptToInt(reader.readLine());
            int storage = Cipher.decryptToInt(reader.readLine());
            int arrow = Cipher.decryptToInt(reader.readLine());
            int water = Cipher.decryptToInt(reader.readLine());
            
         // reads toggled lighting
            boolean lighting = Cipher.decryptToBoolean(reader.readLine());
         
         // loads infomation
            Room.load(inventory, armor, storage, arrow, water);
            Village.load(workers);
            if (lighting) {
               Display.lights();
            }
         
            reader.close();
            in.close();
            
         } 
             catch (IOException exception) {
               System.out.println(exception.getMessage());
            }
      }
   
   // constructor
   // initializes starting screen
       public Game() {
      
      // initializes start window
         startWindow = new JFrame("Game");
         startWindow.setSize(480, 640);         // sets size
         startWindow.setLocation(400, 200);     // sets location
         startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets default closing operation
         startWindow.setResizable(false);       // not resizable
      
      // creates master display
         Display masterDisplay = new Display(480, 640);
         masterDisplay.setLayout(new BoxLayout(masterDisplay, BoxLayout.PAGE_AXIS));
      
      // master display part 1: start screen messages
         StartScreenDisplay startScreenDisplay = new StartScreenDisplay(480, 440);
         startScreenDisplay.setPreferredSize(new Dimension(480, 440));
      
      // master display part 2: registration and log in buttons  
         Display buttonDisplay = new Display(480, 200);
         buttonDisplay.setPreferredSize(new Dimension(480, 200));
         buttonDisplay.setLayout(new GridLayout(2, 1));
      
         // create buttons and button handler and adds to button display
         StartScreenButtonHandler handler = new StartScreenButtonHandler();
      
         JButton newGame = new JButton("Start new game");
         newGame.addActionListener(handler);
      
         JButton loadGame = new JButton("Load saved game");
         loadGame.addActionListener(handler);
      
         buttonDisplay.add(newGame);
         buttonDisplay.add(loadGame);
      
      // combines start screen and buttons to form master display
         masterDisplay.add(startScreenDisplay);
         masterDisplay.add(buttonDisplay);
      
      // display master display
         startWindow.setContentPane(masterDisplay);
         startWindow.setVisible(true);
      
      }
   
   // controlls user registration
   // called by button press   
       private static void registration() {
      // sets up registration window
      // no close operation to allow users to go back to previous step
         registrationWindow = new JFrame("Registration");
         registrationWindow.setSize(480, 640);     // set size
         registrationWindow.setLocation(400, 200); // set location
         registrationWindow.setResizable(false);   // set not resizable
      
      // sets up master display
         Display masterDisplay = new Display(480, 640);
         masterDisplay.setLayout(new BoxLayout(masterDisplay, BoxLayout.PAGE_AXIS));
      
      // master display part 1: text field screen
         Display textDisplay = new Display(480, 300);
         textDisplay.setPreferredSize(new Dimension(480, 300));
         textDisplay.setLayout(new GridLayout(2, 1));
      
         usernameField = new JTextField("Username");
         passwordField = new JTextField("Password");
      
         textDisplay.add(usernameField);
         textDisplay.add(passwordField);
      
      // master display part 2: button screen
         Display buttonDisplay = new Display(480, 340);
         buttonDisplay.setLayout(new GridLayout(2, 1));
         buttonDisplay.setPreferredSize(new Dimension(480, 340));
      
         StartScreenButtonHandler handler = new StartScreenButtonHandler();
         JButton register = new JButton("Register");
         register.addActionListener(handler);
         JButton exit = new JButton("Exit");
         exit.addActionListener(handler);
      
         buttonDisplay.add(register);
         buttonDisplay.add(exit);
      
      // combine textfields and buttons to form master display
         masterDisplay.add(textDisplay);
         masterDisplay.add(buttonDisplay);
      
      // display master display
         registrationWindow.setContentPane(masterDisplay);
         registrationWindow.setVisible(true);
      
      }
   
   // controlls user log in
   // called by button press
       private static void logIn() {
      // sets up registration window
      // no close operation to allow users to go back to previous step
         registrationWindow = new JFrame("Log In");
         registrationWindow.setSize(480, 640);     // set size
         registrationWindow.setLocation(400, 200); // set location
         registrationWindow.setResizable(false);   // set not resizable
      
      // sets up master display
         Display masterDisplay = new Display(480, 640);
         masterDisplay.setLayout(new BoxLayout(masterDisplay, BoxLayout.PAGE_AXIS));
      
      // master display part 1: text field screen
         Display textDisplay = new Display(480, 300);
         textDisplay.setPreferredSize(new Dimension(480, 300));
         textDisplay.setLayout(new GridLayout(2, 1));
      
         usernameField = new JTextField("Username");
         passwordField = new JTextField("Password");
      
         textDisplay.add(usernameField);
         textDisplay.add(passwordField);
      
      // master display part 2: button screen
         Display buttonDisplay = new Display(480, 340);
         buttonDisplay.setLayout(new GridLayout(2, 1));
         buttonDisplay.setPreferredSize(new Dimension(480, 340));
      
         StartScreenButtonHandler handler = new StartScreenButtonHandler();
         JButton register = new JButton("Log In");
         register.addActionListener(handler);
         JButton exit = new JButton("Exit");
         exit.addActionListener(handler);
      
         buttonDisplay.add(register);
         buttonDisplay.add(exit);
      
      // combine textfields and buttons to form master display
         masterDisplay.add(textDisplay);
         masterDisplay.add(buttonDisplay);
      
      // display master display
         registrationWindow.setContentPane(masterDisplay);
         registrationWindow.setVisible(true);
      }
   
   // end expedition by returning
   // player's inventory is added to resources
       public static void endExpedition(Player p) {
      // add message to game display
         gameMessages.addText("Returned from expedition.");
      // update resources
         Room.changeResources(p.getInventory());
      // show game window
         gameWindow.setVisible(true);
      }
   
   // end expedition by dying
   // inventory is lost
       public static void respawn() {
      // add message to game display
         gameMessages.addText("You wake up at the village.");
      // show game window
         gameWindow.setVisible(true);
      }
   
   // returns difficulty
       public static int getDifficulty() {
         return DIFFICULTY;
      }   
   
   }
