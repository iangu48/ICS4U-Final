/*
Class: GameMechanics.java
Authors: Michael and Raghav
Date: June 15, 2017
School: AY Jackson SS
Purpose: To store all the constant values needed throghout the product. Some values are hardcoded, others are read from text files.
*/


package Game.GameMechanics;

import Game.Game;
import Game.Loot.ItemDrop;
import Game.Room.*;
import Game.DuskyPath.Enemy;
import java.io.*;

public class GameMechanics {

    // ========== START OF MODIFIED BY DIFFICULTY ==========
   public static final int DIFFICULTY = 1;
   public static final double HITCHANCE = 0.8 - (0.1 * DIFFICULTY);
   public static final int MEATHEAL = 8;
   public static final int GATHERWOOD = 1;
    // ========== END OF MODIFIED BY DIFFICULTY ==========

    // ========== START OF FINALS ==========
    //Item IDs
   public static final int WOODID = 0;
   public static final int IRONID = 1;
   public static final int STEELID = 2;
   public static final int TEETHID = 3;
   public static final int SCALESID = 4;
   public static final int FURID = 5;
   public static final int MEATID = 6;
   public static final int COOKEDMEATID = 7;
   public static final int BAITID = 8;
   public static final int FISTID = 9;
   public static final int WOODSWORDID = 10;
   public static final int IRONSWORDID = 11;
   public static final int STEELSWORDID = 12;
   public static final int BOWID = 13;
   public static final int TRAPID = 14;
   public static final String[] itemNames = {"Wood", "Iron", "Steel", "Teeth", "Scales", "Fur", "Meat", "Cooked meat", "Bait", "Fist", "Wood sword", "Iron sword", "Steel sword", "Bow", "Trap"};

    //Each index in the arrays for the following constants represent the value of that upgrade at each level, where the index represents the level of upgrade
    //Armor Constant in levels
   public static int[] ARMORS = {10, 15, 25, 45};//numbers represent health for the player

    //Water Constants in levels
   public static int[] WATER = {5, 10, 20, 60}; //numbers represent water the player may carry, which is needed for the expedition

    //Store Constants in levels
   public static int[] CAPACITY = {10, 20, 40, 70}; //numbers represent how many items the player is capable of carrying

    //Arrow Damage with levels
   public static int[] ARROWDAMAGE = {0, 2, 5}; //numbers represent how much damage this arrow upgrades adds on to the bow damage

    //Melee Damage with levels
   public static int[] SWORDDAMAGE = {1, 2, 4, 6}; //numbers represent sword damage

    //Bow Damage:
   public static int BOWDAMAGE = 5;

    //Worker Input: these constants represent how much of each resource is gained or lost when the worker does the task assigned to them
    //Gatherer
   public static final int GATHERERWOOD = 1;
    //Hunter
   public static final int HUNTERFUR = 1;
   public static final int HUNTERMEAT = 1;
    //Baiter
   public static final int BAITERMEAT = -1;
   public static final int BAITERBAIT = 1;
    //Chef
   public static final int CHEFMEAT = -5;
   public static final int CHEFWOOD = -5;
   public static final int CHEFCOOKEDMEAT = 1;
    //Iron Miner
   public static final int MINERMEAT = -1;
   public static final int MINERIRON = 1;
    //Steel Worker
   public static final int STEELIRON = -1;
   public static final int STEELWOOD = -2;
   public static final int STEELSTEEL = 1;
    // ========== END OF FINALS ==========

    // ========== START OF ITEM DROPS==========
   public static ItemDrop WOOD = new ItemDrop(new Material(WOODID, 0), 5, 15, 1.0); //when gathering wood manually
    //for checking traps: 
   public static ItemDrop MEAT = new ItemDrop(new Material(MEATID, 0), 5, 10, 1.0);
   public static ItemDrop SCALES = new ItemDrop(new Material(SCALESID, 0), 1, 3, 0.5);
   public static ItemDrop TEETH = new ItemDrop(new Material(TEETHID, 0), 3, 8, 0.8);
    //for outposts
   public static ItemDrop OUTPOST = new ItemDrop(new Material(COOKEDMEATID, 0), 2, 10, 1.0);
    // ========== END OF ITEM DROPS ==========

    // ========== START OF UPGRADE COSTS ==========
    
   //This method is to read in the item costs of each upgrade type from a file, referenced in the parameter
   //The other parameter, numUpgrades, represents how many upgrades the player may make on this specific aspect of the game
   //The method returns a Resource array, where the index of the array represents the UPGRADE number, which corresponds to the level-1 (since the first upgrade is actually a level 1 item, since level 0 is the default)
   //The contents of the Resource represent the item cost for the specific upgrade
   public static Resource[] costs(String file, int numUpgrades) {
      try {
         BufferedReader in = new BufferedReader(new FileReader(new File("Game\\GameMechanics\\" + file)));
         Resource[] upgrades = new Resource[numUpgrades];  //there are 3 upgrades for every upgrade possible (ex. armour, weapon, health, etc)          
         int[] numItems = new int[numUpgrades]; //get the number of items required for each upgrade
         for (int i = 0; i < upgrades.length; i++) {
            numItems[i] = Integer.parseInt(in.readLine());
         }
         in.readLine();
         for (int i = 0; i < upgrades.length; i++) {
            Item[] costs = new Item[numItems[i]];//in each upgrade, the number of item it takes utilizes the numItems method, so dynamic arrays are unnecessary
            for (int j = 0; j < costs.length; j++) {
               String item = in.readLine();
               String[] str = item.split(" ");
               int code = Integer.parseInt(str[0]);
               int amount = (Integer.parseInt(str[1])) * (-1); //make amount negative to indicate it is a cost
               costs[j] = new Material(code, amount);
            }
            upgrades[i] = new Resource(costs);
            in.readLine();
         }
      
         return upgrades;
      
      } 
      catch (IOException iox) {
         System.out.println (iox.getMessage());
         return null;
      }
   }

    //Upgrade Cost Instantiation: 
   public static Resource[] swordUpgrades = costs("swordCosts.txt", 3);
   public static Resource[] storageUpgrades = costs("storageCosts.txt", 3);
   public static Resource[] waterUpgrades = costs("waterCosts.txt", 3);
   public static Resource[] armorUpgrades = costs("armorCosts.txt", 3);
   public static Resource[] arrowUpgrades = costs("arrowCosts.txt", 3);
   public static Resource[] bowCost = costs("bowCost.txt", 1);
   public static Resource[] trapCost = costs("trapCost.txt", 1);
    // ========== END OF UPGRADE COSTS ==========

    // ========== START OF WEAPONS ===========
   //To have specific weapons to refer to:
   public static Weapon fist = new Weapon(FISTID, 1, SWORDDAMAGE[0]);
   public static Weapon woodSword = new Weapon(WOODSWORDID, 1, SWORDDAMAGE[1]);
   public static Weapon ironSword = new Weapon(IRONSWORDID, 1, SWORDDAMAGE[2]);
   public static Weapon steelSword = new Weapon(STEELSWORDID, 1, SWORDDAMAGE[3]);
   public static Weapon compoundBow = new Weapon(BOWID, 1, BOWDAMAGE);

   public static Weapon[] swords = {fist, woodSword, ironSword, steelSword};
   public static Resource meleeWeapons = new Resource(swords);

    // ========== END OF WEAPONS ==========
    //Trap Item:
   public static Material trap = new Material(TRAPID, 1);
	 
	 // ========== START OF ENEMIES ===========
	
   //This method takes in a file and reads that file to obtain the statistics of all enemies present in the game
   //The Enemy array it returns is a list containing all enemies present in the game
   public static Enemy [] enemyRead(String file)
   {
      try
      {
         BufferedReader in = new BufferedReader(new FileReader(new File("Game\\GameMechanics\\" + file)));
         int numEnemies = Integer.parseInt(in.readLine()); //read in number of enemies in file
      	
         Enemy[] enemies = new Enemy[numEnemies]; // create array of enemies
         for (int i = 0; i < enemies.length; i++)
         {
         	//get data about the enemy
            int hp = Integer.parseInt(in.readLine());
            String name = in.readLine();
            int damage = Integer.parseInt(in.readLine());
            int numItems = Integer.parseInt(in.readLine()); //how many items the enemy can potentially drop
         	
            //Get the enemy's item drops:
            ItemDrop[] items = new ItemDrop[numItems];
            for (int j = 0; j < numItems; j++)
            {
               int id = Integer.parseInt(in.readLine());
               int min = Integer.parseInt(in.readLine());
               int max = Integer.parseInt(in.readLine());
               double chance = Double.parseDouble(in.readLine());
               items[j] = new ItemDrop(id, min, max, chance);
            }
            String description = in.readLine();
            enemies[i] = new Enemy(hp, name, damage, items, description);
            in.readLine();
         }
         in.close();
         return enemies;
      	
      }
      catch (IOException iox)
      {
         System.out.println (iox.getMessage());
         return null;
      }
   	
   }
   public static Enemy[] enemies = enemyRead("enemies.txt"); //set the field
   // ========== END OF ENEMIES ==========
	 
   //The following method is a list of all LootDrop items that the player may encounter at outposts on expeditions
   //The file it takes in is the list of items it is to read
   //The method returns an ItemDrop array, which shows the chances of obtaining each item on an expedition
   public static ItemDrop[] lootDrops(String file)
   {
      try
      {
         BufferedReader in = new BufferedReader(new FileReader(new File("Game\\GameMechanics\\" + file)));
         int numItems = Integer.parseInt(in.readLine()); //number of ItemDrop elements in the file is on the first line
         ItemDrop[] drops = new ItemDrop[numItems];
         for (int i = 0; i < numItems; i++)
         {
            //Read in numbers for each item drop:
            int id = Integer.parseInt(in.readLine());
            int min = Integer.parseInt(in.readLine());
            int max = Integer.parseInt(in.readLine());
            double chance = Double.parseDouble(in.readLine());
            in.readLine(); //there is a space to get to the next drop item
            drops[i] = new ItemDrop(id, min, max, chance);//create new item drop
         }
	 in.close();
         return drops;
      }
      catch(IOException iox)
      {
         System.out.println (iox.getMessage());
         return null;
      }
   }
	 
   public static ItemDrop[] lootDrop = lootDrops("lootDrop.txt");
}
