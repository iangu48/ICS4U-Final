/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
   package Game;

/**
 *
 * @author shawn
 */
   import Game.Village.*;
   import Game.DuskyPath.Player;
   import Game.Encription.Cipher;
   import Game.GUI.*;
   import Game.GameMechanics.GameMechanics;
   import Game.Room.*;
   import Game.Loot.Loot;
   import java.util.Arrays;
   import java.util.Timer;
   import java.util.TimerTask;
   import java.io.*;
   import java.awt.*;
   import java.awt.event.*;
   import javax.swing.BoxLayout;
   import javax.swing.JButton;
   import javax.swing.JFrame;
   import javax.swing.JLabel;
   import javax.swing.JTextField;
   import javax.swing.SwingConstants;

    public class Game {
   
      private static JFrame window;
      private static JFrame gameWindow;
      private static JFrame registrationWindow;
      private static JTextField usernameField;
      private static JTextField passwordField;
      private static String username;
      private static String password;
      
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
   
      private static Messages gameMessages = new Messages();
      private static final int DIFFICULTY = 1;
      private static int woodCooldown = 0;
      private static int trapCooldown = 0;
      private static int workerCooldown = 10;
      private static int saveCooldown = 60;
      private Village village = new Village(60 - DIFFICULTY * 20);
      private static final String[] creators = {"Shawn Wang", "Michael Chang", "Raghav Srinivasan", "Tong Li Han", "Ian Gu"};
   
       private static class StartScreenDisplay extends Display {
      
          public StartScreenDisplay(int width, int height) {
            super(width, height);
         }
      
          @Override
          public void paintContents(Graphics g) {
            g.setColor(textColor);
            g.setFont(new Font("Dialog", Font.PLAIN, 14));
            g.drawString("Welcome to the parody of the game \"A dark room\"", 15, 20);
            g.drawString("Made by: ", 15, 70);
            for (int i = 0; i < creators.length; i++)
            {
               g.drawString(creators[i], 15, 95 + i * 25);
            }
         }
      
      }
   
       private static class MenuDisplay extends Display {
      
         private static final Font BUTTONFONT = new Font("Dialog", Font.PLAIN, 10);
      
          public MenuDisplay(int width, int height) {
            super(width, height);
            setLayout(new GridLayout(7, 3));
            addContent();
         }
      
          private void addContent() {
            MenuButtonHandler handler = new MenuButtonHandler();
            woodButton = new JButton("Gather wood");
            woodButton.addActionListener(handler);
            woodButton.setToolTipText("Receive wood");
            woodButton.setFont(BUTTONFONT);
            checkTrapButton = new JButton("Check trap");
            Item traps = Room.getInventory().findItemById(14);
            if (traps == null || traps.getAmount() == 0)
               checkTrapButton.setEnabled(false);
            checkTrapButton.setToolTipText("Receive meat, fur, scales, and teeth");
            checkTrapButton.addActionListener(handler);
            checkTrapButton.setFont(BUTTONFONT);
            buildTrapButton = new JButton("Build trap");
            buildTrapButton.setToolTipText(GameMechanics.trapCost[0].toString());
            buildTrapButton.addActionListener(handler);
            buildTrapButton.setFont(BUTTONFONT);
            if (traps != null && traps.getAmount() == 10)
         		buildTrapButton.setEnabled(false);
            expeditionButton = new JButton("Expedition");
            expeditionButton.setToolTipText("Travel into the dusky path.");
            expeditionButton.addActionListener(handler);
            expeditionButton.setFont(BUTTONFONT);
            leatherArmorButton = new JButton("Leather armor");
            leatherArmorButton.addActionListener(handler);
            leatherArmorButton.setToolTipText(GameMechanics.armorUpgrades[0].toString());
            leatherArmorButton.setFont(BUTTONFONT);
            ironArmorButton = new JButton("Iron armor");
            ironArmorButton.addActionListener(handler);
            ironArmorButton.setToolTipText(GameMechanics.armorUpgrades[1].toString());
            ironArmorButton.setFont(BUTTONFONT);
            steelArmorButton = new JButton("Steel armor");
            steelArmorButton.addActionListener(handler);
            steelArmorButton.setToolTipText(GameMechanics.armorUpgrades[2].toString());
            steelArmorButton.setFont(BUTTONFONT);
            waterskinButton = new JButton("Waterskin");
            waterskinButton.addActionListener(handler);
            waterskinButton.setToolTipText(GameMechanics.waterUpgrades[0].toString());
            waterskinButton.setFont(BUTTONFONT);
            waterCaskButton = new JButton("Cask");
            waterCaskButton.addActionListener(handler);
            waterCaskButton.setToolTipText(GameMechanics.waterUpgrades[1].toString());
            waterCaskButton.setFont(BUTTONFONT);
            waterTankButton = new JButton("Water tank");
            waterTankButton.addActionListener(handler);
            waterTankButton.setToolTipText(GameMechanics.waterUpgrades[2].toString());
            waterTankButton.setFont(BUTTONFONT);
            woodenSwordButton = new JButton("Wooden sword");
            woodenSwordButton.addActionListener(handler);
            woodenSwordButton.setToolTipText(GameMechanics.swordUpgrades[0].toString());
            woodenSwordButton.setFont(BUTTONFONT);
            ironSwordButton = new JButton("Iron sword");
            ironSwordButton.addActionListener(handler);
            ironSwordButton.setToolTipText(GameMechanics.swordUpgrades[1].toString());
            ironSwordButton.setFont(BUTTONFONT);
            steelSwordButton = new JButton("Steel sword");
            steelSwordButton.addActionListener(handler);
            steelSwordButton.setToolTipText(GameMechanics.swordUpgrades[2].toString());
            steelSwordButton.setFont(BUTTONFONT);
            rucksackButton = new JButton("Rucksack");
            rucksackButton.addActionListener(handler);
            rucksackButton.setToolTipText(GameMechanics.storageUpgrades[0].toString());
            rucksackButton.setFont(BUTTONFONT);
            wagonButton = new JButton("Wagon");
            wagonButton.addActionListener(handler);
            wagonButton.setToolTipText(GameMechanics.storageUpgrades[1].toString());
            wagonButton.setFont(BUTTONFONT);
            convoyButton = new JButton("Convoy");
            convoyButton.addActionListener(handler);
            convoyButton.setToolTipText(GameMechanics.storageUpgrades[2].toString());
            convoyButton.setFont(BUTTONFONT);
            woodenArrowButton = new JButton("Wooden arrow");
            woodenArrowButton.addActionListener(handler);
            woodenArrowButton.setToolTipText(GameMechanics.arrowUpgrades[0].toString());
            woodenArrowButton.setFont(BUTTONFONT);
            ironArrowButton = new JButton("Iron arrow");
            ironArrowButton.addActionListener(handler);
            ironArrowButton.setToolTipText(GameMechanics.arrowUpgrades[1].toString());
            ironArrowButton.setFont(BUTTONFONT);
            steelArrowButton = new JButton("Steel arrow");
            steelArrowButton.addActionListener(handler);
            steelArrowButton.setToolTipText(GameMechanics.arrowUpgrades[2].toString());
            steelArrowButton.setFont(BUTTONFONT);
            saveButton = new JButton("Save");
            saveButton.addActionListener(handler);
            saveButton.setToolTipText("Saves progress. Automatic save every minute.");
            saveButton.setFont(BUTTONFONT);
            bowButton = new JButton("Bow");
            bowButton.addActionListener(handler);
            bowButton.setToolTipText("Requires arrow upgrades to shoot. " + GameMechanics.bowCost[0]);
            bowButton.setFont(BUTTONFONT);
         
            add(woodButton);
            add(checkTrapButton);
            add(buildTrapButton);
            
            add(waterskinButton);
            add(waterCaskButton);
            add(waterTankButton);
            add(rucksackButton);
            add(wagonButton);
            add(convoyButton);
            
            add(leatherArmorButton);
            add(ironArmorButton);
            add(steelArmorButton);
            add(woodenSwordButton);
            add(ironSwordButton);
            add(steelSwordButton);
            add(woodenArrowButton);
            add(ironArrowButton);
            add(steelArrowButton);
            add(bowButton);
            add(expeditionButton);
            add(saveButton);
         
         }
      }
   
       private static class GameDisplay extends Display {
      
         private static final int MESSAGEXPOSITION = 20;
         private static final int MESSAGEINITIALYPOSITION = 20;
      
          public GameDisplay(int width, int height) {
            super(width, height);
         }
      
          @Override
          public void paintContents(Graphics g) {
            g.setColor(textColor);
            g.setFont(new Font("Dialog", Font.PLAIN, 14));
            paintMessages(g);
         }
      
          public void paintMessages(Graphics g) {
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
         }
      }
   
       private static class ResourceDisplay extends Display {
      
         private static final int RESOURCEXPOSITION = 20;
         private static final int RESOURCEINITIALYPOSITION = 20;
      
          public ResourceDisplay(int width, int height) {
            super(width, height);
         }
      
          @Override
          public void paintContents(Graphics g) {
            g.setColor(textColor);
            g.setFont(new Font("Dialog", Font.PLAIN, 14));
            int xPosition = RESOURCEXPOSITION;
            int yPosition = RESOURCEINITIALYPOSITION;
         
            Item[] temp = Room.getInventory().getItems();
            for (int i = 0; i < temp.length && temp[i] != null; i++) {
               g.drawString(temp[i].getName() + ": " + temp[i].getAmount(), xPosition, yPosition);
               yPosition += LINESPACING;
            }
         }
      }
   
       private static class VillageDisplay extends Display {
      
         private static final Font BUTTONSFONT = new Font("Dialog", Font.PLAIN, 20);
      
          public VillageDisplay(int width, int height) {
            super(width, height);
            addContents();
         }
      
          private void addContents() {
            setLayout(new GridLayout(0, 4));
            Workers temp = Village.getWorker(0);
            JLabel name = new JLabel(temp.toString());
            name.setForeground(textColor);
            name.setHorizontalAlignment(SwingConstants.CENTER);
            name.setToolTipText(temp.getUnitResource().toString());
            JLabel amount = new JLabel("" + temp.getNumWorkers());
            amount.setHorizontalAlignment(SwingConstants.CENTER);
            amount.setForeground(textColor);
            add(name);
            add(new JLabel());
            add(amount);
            add(new JLabel());
            ButtonHandler.initialize(temp, amount);
            int i = 1;
            while ((temp = Village.getWorker(i)) != null) {
               name = new JLabel(temp.toString());
               name.setHorizontalAlignment(SwingConstants.CENTER);
               name.setToolTipText(temp.getUnitResource().toString());
               name.setForeground(textColor);
               amount = new JLabel("" + temp.getNumWorkers());
               amount.setHorizontalAlignment(SwingConstants.CENTER);
               amount.setForeground(textColor);
               JButton removeButton = new JButton("-");
               JButton addButton = new JButton("+");
               ButtonHandler handler = new ButtonHandler(temp, amount, removeButton, addButton);
               removeButton.addActionListener(handler);
               if (Village.getNumWorkers(i) == 0)
                  removeButton.setEnabled(false);
               removeButton.setFont(BUTTONSFONT);
               addButton.addActionListener(handler);
               addButton.setFont(BUTTONSFONT);
               add(name);
               add(removeButton);
               add(amount);
               add(addButton);
               i++;
            }
         }
      
          private static class ButtonHandler implements ActionListener {
         
            static Workers gatherer = Village.getWorker(0);
            static JLabel gathererLabel;
            static JButton[] addButtons = new JButton[0];
            Workers worker;
            JLabel workerLabel;
            JButton removeButton;
         
             public static void initialize(Workers worker, JLabel label) {
               gatherer = worker;
               gathererLabel = label;
            }
         
             public ButtonHandler(Workers worker, JLabel workerLabel, JButton removeButton, JButton addButton) {
               this.worker = worker;
               this.workerLabel = workerLabel;
               this.removeButton = removeButton;
               addButtons = Arrays.copyOf(addButtons, addButtons.length + 1);
               addButtons[addButtons.length - 1] = addButton;
            }
         
             @Override
             public void actionPerformed(ActionEvent e) {
               String s = e.getActionCommand();
               int numGatherers;
               switch (s) {
                  case "-":
                     worker.removeWorkers();
                     int numWorkers = worker.getNumWorkers();
                     workerLabel.setText("" + numWorkers);
                     if (numWorkers == 0) {
                        removeButton.setEnabled(false);
                     }
                     gatherer.addWorkers();
                     numGatherers = gatherer.getNumWorkers();
                     gathererLabel.setText("" + numGatherers);
                     if (numGatherers == 1) {
                        for (int i = 0; i < addButtons.length; i++) {
                           addButtons[i].setEnabled(true);
                        }
                     }
                     break;
                  case "+":
                     worker.addWorkers();
                     workerLabel.setText("" + worker.getNumWorkers());
                     removeButton.setEnabled(true);
                     gatherer.removeWorkers();
                     numGatherers = gatherer.getNumWorkers();
                     gathererLabel.setText("" + numGatherers);
                     if (numGatherers == 0) {
                        for (int i = 0; i < addButtons.length; i++) {
                           addButtons[i].setEnabled(false);
                        }
                     }
                     break;
                  default:
                     break;
               }
            }
         
         }
      }
   
       private static class MenuButtonHandler implements ActionListener {
      
          @Override
          public void actionPerformed(ActionEvent e) {
            String[] text;
            String action = e.getActionCommand();
            switch (action) {
               case "Gather wood":
                  Room.gatherWood();
                  woodButton.setEnabled(false);
                  text = new String[2];
                  gameMessages.addText("You gathered some wood branches from a nearby forest.");
                  woodCooldown = 15;
                  woodButton.setText("Cooldown: " + woodCooldown);
                  break;
               case "Check trap":
                  Room.checkTrap();
                  checkTrapButton.setEnabled(false);
                  gameMessages.addText("Checking your traps, you have found some needed resources.");
                  trapCooldown = 20;
                  checkTrapButton.setText("Cooldown: " + trapCooldown);
                  break;
               case "Expedition":
                  Item curedMeat = Room.getInventory().findItemById(7);
                  if (curedMeat != null && curedMeat.getAmount() > 10) {
                     gameMessages.clearTexts();
                     gameWindow.setVisible(false);
                     Item[] items = {curedMeat.retrieve(10)};
                     Loot store = new Loot(items);
                     Player player = new Player(username, Room.getMaxHealth(), Room.getMaxWater(), Room.getMaxStorage(), store);
                     player.prepare(Room.getInventory());
                  }
                  break;
               case "Build trap":
                  if (Room.buildTrap())
                  {	
                     gameMessages.addText("Building more traps for more resources.");
                     int numTraps = Room.getInventory().findItemById(14).getAmount();
                     if (numTraps == 10)
                        buildTrapButton.setEnabled(false);
                     else if (numTraps == 1)
                        checkTrapButton.setEnabled(true);
                  }
                  break;
               case "Leather armor":
                  if (Room.upgradeArmor(1)) {
                     gameMessages.addText("Leather won't offer much protection, but is better than having nothing.");
                     leatherArmorButton.setEnabled(false);
                  }
                  break;
               case "Iron armor":
                  if (Room.upgradeArmor(2)) {
                     gameMessages.addText("Iron armor. Decent levels of protection. Can still be better.");
                     leatherArmorButton.setEnabled(false);
                     ironArmorButton.setEnabled(false);
                  }
                  break;
               case "Steel armor":
                  if (Room.upgradeArmor(3)) {
                     gameMessages.addText("Steel armor. The best protection available for exploration.");
                     leatherArmorButton.setEnabled(false);
                     ironArmorButton.setEnabled(false);
                     steelArmorButton.setEnabled(false);
                  }
                  break;
               case "Waterskin":
                  if (Room.upgradeWater(1)) {
                     text = new String[2];
                     text[0] = "Water capacity increased. You can now journey farther into the wilderness.";
                     text[1] = "Waterskin unlocked, but it won't hold much water.";
                     gameMessages.addText(text);
                     waterskinButton.setEnabled(false);
                  }
                  break;
               case "Cask":
                  if (Room.upgradeWater(2)) {
                     text = new String[2];
                     text[0] = "Water capacity increased. You can now journey farther.";
                     text[1] = "Water cask unlocked. Water will be less of a concern";
                     gameMessages.addText(text);
                     waterskinButton.setEnabled(false);
                     waterCaskButton.setEnabled(false);
                  }
                  break;
               case "Water tank":
                  if (Room.upgradeWater(3)) {
                     text = new String[2];
                     text[0] = "Water capacity increased. You can now journey farther.";
                     text[1] = "Water tank unlocked, it should be sufficient for most explorations.";
                     gameMessages.addText(text);
                     waterskinButton.setEnabled(false);
                     waterCaskButton.setEnabled(false);
                     waterTankButton.setEnabled(false);
                  }
                  break;
               case "Rucksack":
                  if (Room.upgradeStorage(1)) {
                     text = new String[2];
                     text[0] = "Storage capacity increased. You can now carry more items.";
                     text[1] = "Rucksack unlocked. It does not carry much.";
                     gameMessages.addText(text);
                     rucksackButton.setEnabled(false);
                  }
                  break;
               case "Wagon":
                  if (Room.upgradeStorage(2)) {
                     text = new String[2];
                     text[0] = "Storage capacity increased. You can now carry more items.";
                     text[1] = "Wagon unlocked. Sufficient for most of your items.";
                     gameMessages.addText(text);
                     rucksackButton.setEnabled(false);
                     wagonButton.setEnabled(false);
                  }
                  break;
               case "Convoy":
                  if (Room.upgradeStorage(3)) {
                     text = new String[2];
                     text[0] = "Storage capacity increased. You can now carry more items.";
                     text[1] = "Convoy unlocked.";
                     gameMessages.addText(text);
                     rucksackButton.setEnabled(false);
                     wagonButton.setEnabled(false);
                     convoyButton.setEnabled(false);
                  }
                  break;
               case "Wooden sword":
                  if (Room.buildSword(1))
                  {
                     gameMessages.addText("Crafted a wooden sword.");
                  }
                  break;
               case "Iron sword":
                  if (Room.buildSword(2))
                  {
                     gameMessages.addText("Crafted an iron sword.");
                  }
                  break;
               case "Steel sword":
                  if (Room.buildSword(3))
                  {
                     gameMessages.addText("Crafted a steel sword.");
                  }
                  break;
               case "Bow":
                  if (Room.buildBow())
                  {
                     gameMessages.addText("Crafted a simple bow");
                  }
                  break;
               case "Wood arrow":
                  if (Room.upgradeArrows(1)) {
                     gameMessages.addText("Wooden arrows. You may now use the bow in your battles.");
                     woodenArrowButton.setEnabled(false);
                  }
                  break;
               case "Iron arrow":
                  if (Room.upgradeArrows(2)) {
                     gameMessages.addText("Iron arrows. You now do more damage with your bow.");
                     woodenArrowButton.setEnabled(false);
                     ironArrowButton.setEnabled(false);
                  }
                  break;
               case "Steel arrow":
                  if (Room.upgradeArrows(3)) {
                     gameMessages.addText("Steel arrows. You now do significant damage with your bow.");
                     woodenArrowButton.setEnabled(false);
                     ironArrowButton.setEnabled(false);
                     steelArrowButton.setEnabled(false);
                  }
                  break;
               case "Save":
                  save();
                  gameMessages.addText("Game saved");
                  break;
               case "Lights":
                  Display.lights();
                  gameMessages.addText("Toggling lights.");
                  break;
               default:
                  break;
            }
            gameWindow.repaint();
         }
      }
   
       private static class StartScreenButtonHandler implements ActionListener {
      
          @Override
          public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
            if (action.equals("Start new game")) {
               registration();
            } 
            else if (action.equals("Load saved game")) {
               logIn();
            } 
            else if (action.equals("Register")) {
               username = usernameField.getText();
               password = passwordField.getText();
               if (username != null && password != null) {
                  File profile = new File("Game\\saves\\" + username + ".txt");
                  if (!profile.exists()) {
                     try {
                        FileWriter out = new FileWriter(profile);
                        BufferedWriter writer = new BufferedWriter(out);
                        writer.write(password);
                        writer.close();
                        out.close();
                        registrationWindow.dispose();
                        startGame();
                     } 
                         catch (IOException exception) {
                           System.out.println(exception.getMessage());
                        }
                  } 
                  else {
                     usernameField.setText("Error. Profile already exists.");
                  }
               }
            } 
            else if (action.equals("Log In")) {
               username = usernameField.getText();
               password = passwordField.getText();
               if (username != null && password != null) {
                  File profile = new File("Game\\saves\\" + username + ".txt");
                  if (profile.exists()) {
                     try {
                        FileReader in = new FileReader(profile);
                        BufferedReader reader = new BufferedReader(in);
                        if (password.equals(reader.readLine())) {
                           registrationWindow.dispose();
                           load();
                           startGame();
                        } 
                        else {
                           passwordField.setText("Error. Wrong password.");
                        }
                     } 
                         catch (IOException exception) {
                           System.out.println(exception.getMessage());
                        }
                  } 
                  else {
                     usernameField.setText("Error. Profile does not exist.");
                  }
               }
            } 
            else if (action.equals("Exit")) {
               registrationWindow.dispose();
            }
         
            window.repaint();
         }
      
      }
   
       public static void startGame() {
         window.setVisible(false);
         window.dispose();
      
         gameWindow = new JFrame("The dark room");
         gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         gameWindow.setSize(1200, 700);
         gameWindow.setLocation(0, 100);
      
         Display mainDisplay = new Display(1200, 700);
         mainDisplay.setLayout(new GridLayout(1, 3));
      
         MenuDisplay buttonScreen = new MenuDisplay(400, 700);
         buttonScreen.setPreferredSize(new Dimension(400, 700));
      
         GameDisplay storyScreen = new GameDisplay(400, 700);
         storyScreen.setPreferredSize(new Dimension(400, 700));
      
         Display resourceDisplay = new Display(400, 700);
         resourceDisplay.setLayout(new GridLayout(2, 1));
         resourceDisplay.setPreferredSize(new Dimension(400, 700));
      
         ResourceDisplay inventoryScreen = new ResourceDisplay(400, 700);
         inventoryScreen.setPreferredSize(new Dimension(400, 400));
      
         VillageDisplay villageDisplay = new VillageDisplay(400, 700);
         villageDisplay.setPreferredSize(new Dimension(400, 300));
      
         resourceDisplay.add(inventoryScreen);
         resourceDisplay.add(villageDisplay);
      
         mainDisplay.add(buttonScreen);
         mainDisplay.add(storyScreen);
         mainDisplay.add(resourceDisplay);
      
         gameWindow.setContentPane(mainDisplay);
         gameWindow.repaint();
         gameWindow.setVisible(true);
      
         Timer timer = new Timer();
      
         TimerTask task = 
             new TimerTask() {
                @Override
                public void run() {
                  if (woodCooldown > 0) {
                     woodCooldown--;
                     woodButton.setText("Cooldown: " + woodCooldown);
                     if (woodCooldown == 0) {
                        woodButton.setEnabled(true);
                        woodButton.setText("Gather wood");
                     }
                  }
                  if (trapCooldown > 0) {
                     trapCooldown--;
                     checkTrapButton.setText("Cooldown: " + trapCooldown);
                     if (trapCooldown == 0) {
                        checkTrapButton.setText("Check trap");
                        checkTrapButton.setEnabled(true);
                     }
                  }
                  if (workerCooldown > 0) {
                     workerCooldown--;
                     if (workerCooldown == 0) {
                        Resource[] resources = Village.gatherResources();
                        for (int i = 0; i < resources.length; i++)
                           Room.changeResources(resources[i]);
                        gameWindow.repaint();
                        workerCooldown = 15;
                        gameMessages.addText("Your villagers gathered some resources.");
                     }
                  }
                  if (saveCooldown > 0) {
                     saveCooldown--;
                     if (saveCooldown == 0) {
                        save();
                        gameMessages.addText("Auto save.");
                        saveCooldown = 60;
                     }
                  }
               }
            };
         timer.scheduleAtFixedRate(task, 0, 1000); //1000ms = 1sec
      }
   
       public static void save() {
         File profile = new File("Game\\saves\\" + username + ".txt");
         if (profile.exists()) {
            try {
               FileWriter out = new FileWriter(profile);
               BufferedWriter writer = new BufferedWriter(out);
               writer.write(password);
               writer.newLine();
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
               for (int i = 0; i < Village.length(); i++) {
                  writer.write(Cipher.encrypt(Village.getNumWorkers(i)));
                  writer.newLine();
               }
               writer.write(Cipher.encrypt(Room.getArmorLevel()));
               writer.newLine();
               writer.write(Cipher.encrypt(Room.getStorageLevel()));
               writer.newLine();
               writer.write(Cipher.encrypt(Room.getArrowLevel()));
               writer.newLine();
               writer.write(Cipher.encrypt(Room.getWaterLevel()));
               writer.newLine();
               writer.write(Cipher.encrypt(Display.getLighting()));
               writer.close();
               out.close();
            } 
                catch (IOException exception) {
                  System.out.println(exception.getMessage());
               }
         } 
         else {
            usernameField.setText("Error. Profile does not exist.");
         }
      }
   
       public static void load() {
         try {
            File profile = new File("Game\\saves\\" + username + ".txt");
            if (profile.exists()) {
               try {
                  FileReader in = new FileReader(profile);
                  BufferedReader reader = new BufferedReader(in);
                  reader.readLine();
                  Item[] items = new Item[0];
                  int amount;
                  for (int i = 0; i < GameMechanics.COOKEDMEATID; i++) {
                     amount = Cipher.decryptToInt(reader.readLine());
                     if (amount > 0) {
                        items = Arrays.copyOf(items, items.length + 1);
                        items[items.length - 1] = new Material(i, amount);
                     }
                  }
                  amount = Cipher.decryptToInt(reader.readLine());
                  if (amount > 0) {
                     items = Arrays.copyOf(items, items.length + 1);
                     items[items.length - 1] = new Healing(GameMechanics.COOKEDMEATID, amount, 8);
                  }
                  amount = Cipher.decryptToInt(reader.readLine());
                  if (amount > 0) {
                     items = Arrays.copyOf(items, items.length + 1);
                     items[items.length - 1] = new Material(GameMechanics.BAITID, amount);
                  }
                  for (int i = GameMechanics.WOODSWORDID; i <= GameMechanics.TRAPID; i++) {
                     amount = Cipher.decryptToInt(reader.readLine());
                     if (amount > 0) {
                        items = Arrays.copyOf(items, items.length + 1);
                        items[items.length - 1] = new Material(i, amount);
                     }
                  }
                  Resource inventory = new Resource(items);
                  int[] workers = new int[Village.length()];
                  for (int i = 0; i < Village.length(); i++) {
                     workers[i] = Cipher.decryptToInt(reader.readLine());
                  }
               
                  int armor = Cipher.decryptToInt(reader.readLine());
                  int storage = Cipher.decryptToInt(reader.readLine());
                  int arrow = Cipher.decryptToInt(reader.readLine());
                  int water = Cipher.decryptToInt(reader.readLine());
               
                  boolean lighting = Cipher.decryptToBoolean(reader.readLine());
               
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
            else {
               usernameField.setText("Error. Profile does not exist.");
            }
         } 
             catch (NumberFormatException e) {
            
            }
      }
   
       public Game() {
         window = new JFrame("Game");
         Display masterDisplay = new Display(480, 640);
         masterDisplay.setLayout(new BoxLayout(masterDisplay, BoxLayout.PAGE_AXIS));
      
         StartScreenDisplay startScreenDisplay = new StartScreenDisplay(480, 440);
         startScreenDisplay.setPreferredSize(new Dimension(480, 440));
      
         Display buttonDisplay = new Display(480, 200);
         buttonDisplay.setPreferredSize(new Dimension(480, 200));
         buttonDisplay.setLayout(new GridLayout(2, 1));
      
         StartScreenButtonHandler handler = new StartScreenButtonHandler();
         JButton newGame = new JButton("Start new game");
         newGame.addActionListener(handler);
         JButton loadGame = new JButton("Load saved game");
         loadGame.addActionListener(handler);
         buttonDisplay.add(newGame);
         buttonDisplay.add(loadGame);
      
         masterDisplay.add(startScreenDisplay);
         masterDisplay.add(buttonDisplay);
      
         window.setContentPane(masterDisplay);
         window.setSize(480, 640);
         window.setLocation(400, 200);
         window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         window.setVisible(true);
      
      }
   
       private static void registration() {
         registrationWindow = new JFrame("Registration");
         registrationWindow.setSize(480, 640);
         registrationWindow.setLocation(400, 200);
         registrationWindow.setResizable(false);
      
         Display masterDisplay = new Display(480, 640);
         masterDisplay.setLayout(new BoxLayout(masterDisplay, BoxLayout.PAGE_AXIS));
      
         Display textDisplay = new Display(480, 300);
         textDisplay.setPreferredSize(new Dimension(480, 300));
         textDisplay.setLayout(new GridLayout(2, 1));
      
         usernameField = new JTextField("Username");
         passwordField = new JTextField("Password");
      
         textDisplay.add(usernameField);
         textDisplay.add(passwordField);
      
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
      
         masterDisplay.add(textDisplay);
         masterDisplay.add(buttonDisplay);
      
         registrationWindow.setContentPane(masterDisplay);
         registrationWindow.setVisible(true);
      
      }
   
       private static void logIn() {
         registrationWindow = new JFrame("Log In");
         registrationWindow.setSize(480, 640);
         registrationWindow.setLocation(400, 200);
         registrationWindow.setResizable(false);
      
         Display masterDisplay = new Display(480, 640);
         masterDisplay.setLayout(new BoxLayout(masterDisplay, BoxLayout.PAGE_AXIS));
      
         Display textDisplay = new Display(480, 300);
         textDisplay.setPreferredSize(new Dimension(480, 300));
         textDisplay.setLayout(new GridLayout(2, 1));
      
         usernameField = new JTextField("Username");
         passwordField = new JTextField("Password");
      
         textDisplay.add(usernameField);
         textDisplay.add(passwordField);
      
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
      
         masterDisplay.add(textDisplay);
         masterDisplay.add(buttonDisplay);
      
         registrationWindow.setContentPane(masterDisplay);
         registrationWindow.setVisible(true);
      }
   
       public static void endExpedition(Player p) {
         gameMessages.addText("Returned from expedition.");
         Room.changeResources(p.getInventory());
         gameWindow.setVisible(true);
      }
   
       public static void respawn() {
         gameMessages.addText("You wake up at the village.");
         gameWindow.setVisible(true);
      }
   
       public static int getDifficulty() {
         return DIFFICULTY;
      }
   
       public static void enableTrap() {
         checkTrapButton.setEnabled(true);
      }
      
   
   }
