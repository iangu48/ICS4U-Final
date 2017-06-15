/*
Class Name: Room.java
Author: Raghav
Date: June 15, 2017
School: AY Jackson SS
Purpose: For the user to manage game resources when not on expeditions, and to upgrade and
	 build items. 
*/


package Game.Room;

import Game.Game;
import Game.Loot.ItemDrop;
import Game.Loot.Loot;
import Game.GameMechanics.GameMechanics;

public class Room {

    private static final int MAXLEVEL = 3;
    private static Item[] items = new Item[0];
    private static Resource inventory = new Resource(items);
    private static int armorLevel = 0;
    private static int storageLevel = 0;
    private static int arrowLevel = 0;
    private static int waterLevel = 0;

    public static void load(Resource items, int armor, int storage, int arrow, int water) {
        inventory = items;
        armorLevel = armor;
        storageLevel = storage;
        arrowLevel = arrow;
        waterLevel = water;
    }

    //All the accessor methods:
    public static int getArmorLevel() {
        return armorLevel;
    }

    public static int getStorageLevel() {
        return storageLevel;
    }

    public static int getArrowLevel() {
        return arrowLevel;
    }

    public static int getWaterLevel() {
        return waterLevel;
    }

    public static int getMaxHealth() {
        return GameMechanics.ARMORS[armorLevel];
    }

    public static int getMaxWater() {
        return GameMechanics.WATER[armorLevel];
    }

    public static int getMaxStorage() {
        return GameMechanics.CAPACITY[armorLevel];
    }
    
    public static Resource getInventory() {
        return inventory;
    }
    
    //This method is to manipulate the contents of the player's inventory, and it returns a boolean to show if this change was successful or not:
    public static boolean changeResources(Resource other) {
        return inventory.addResources(other);
    }
    
    //This method also manipulates the contents of the inventory, but is to take in a loot instead to add to inventory
    //Returns a boolean to show if change is successfull or not
    public static boolean changeResources(Loot loot) {
        Resource resource = new Resource(loot);
        return changeResources(resource);
    }

    //This method allows the player to manually gather wood to add to inventory
    //This is called by the button in the game that says "gather wood"
    public static void gatherWood() {
        Item wood = GameMechanics.WOOD.drop();
        inventory.addItem(wood);
    }

     //This method allows the player to gain resources from the traps they build
     //It is called by the button in the game that says "check trap"
    public static void checkTrap() {
        Item trap = inventory.findItemById(GameMechanics.TRAPID);
		  
        int numTraps;
        if (trap == null) {
            numTraps = 0;
        } else {
            numTraps = trap.getAmount();
        }
		  
        Item bait = inventory.findItemById(GameMechanics.BAITID);
		  
        int numBait;
        if (bait == null) {
            numBait = 0;
        } else {
            numBait = bait.getAmount();
        }

        for (int i = 0; i < numTraps; i++) {
	    //gets drops from trap
            Item meat = GameMechanics.MEAT.drop();
            Item scales = GameMechanics.SCALES.drop();
            Item teeth = GameMechanics.TEETH.drop();
				
	    Item[] gains = {meat, scales, teeth};
			
	     //bait increases trap gains:
	    if (numBait > 0)
	    {
		numBait--;
		for (int j = 0; j < gains.length; j++)
		{
		    Item temp = gains[j];
		    if (temp != null)
		    {
			temp.setAmount(temp.getAmount() * 2); //bait doubles gains
		    }
	     }
	}
         Resource obtained = new Resource(gains);

            inventory.combineResources(obtained); //add new gains to inventory
        }
    }

    //builds a trap item and adds it to player inventory
    //returns a boolean to see if the player actually successfully builds item
    //takes in the level of upgrade the user wishes to upgrade to, so the user may skip upgrades to go to more advanced options if available
    public static boolean buildTrap() {
        Item traps = inventory.findItemById(GameMechanics.TRAPID);
        if (traps == null || traps.getAmount() < 10) { //there is a limit on number of traps the player can own
            Resource expense = new Resource(GameMechanics.trapCost[0]);
            if (inventory.addResources(expense)) { //checks if player can actually afford item
                Item copy = new Material(GameMechanics.trap);
                inventory.addItem(copy);
                return true;
            }
        }
        return false;
    }

    //upgrades the armour for the player
    //returns a boolean for if this upgrade is successful
    //takes in the level of upgrade the user wishes to upgrade to, so the user may skip upgrades to go to more advanced options if available
    public static boolean upgradeArmor(int level) {
        if (level > armorLevel && level <= MAXLEVEL) {
            if (inventory.addResources(GameMechanics.armorUpgrades[level - 1])) {
                armorLevel = level;
                return true;
            }
        }
        return false;
    }

    //upgrades the storage capacity for the user to carry on expeditions - allows them to carry more loot
    //returns a boolean for if this upgrade is successfull
    //takes in the level of upgrade the user wishes to upgrade to, so the user may skip upgrades to go to more advanced options if available
    public static boolean upgradeStorage(int level) {
        if (level > storageLevel && level <= MAXLEVEL) {
            if (inventory.addResources(GameMechanics.storageUpgrades[level - 1])) {
                storageLevel = level;
                return true;
            }
        }
        return false;
    }
    
     //upgrades water storage for the player, so they can go on longer expeditions - water is consumed the longer the expedition is, and when the player runs out, they die
     //returns a boolean for if this upgrade is successfull
    //takes in the level of upgrade the user wishes to upgrade to, so the user may skip upgrades to go to more advanced options if available
    public static boolean upgradeWater(int level) {
        if (level > waterLevel && level <= MAXLEVEL) {
            if (inventory.addResources(GameMechanics.waterUpgrades[level - 1])) {
                waterLevel = level;
                return true;
            }
        }
        return false;
    }
     
    //Allows the user to build a sword and add it to their inventory
    //takes in a level to see which sword the player wishes to buy
    //returns a boolean for if this purchase is successful or not
    public static boolean buildSword(int level) {
        if (inventory.addResources(GameMechanics.swordUpgrades[level - 1])) { //requires user to pay for the item cost
            inventory.addItem(GameMechanics.swords[level]);
            return true;
        }
        return false;
    }
 
    //Allows user to build a bow and add it to their inventory
    //No parameters as there is only one type of bow
    //returns a boolean for if the purchase is successful
    public static boolean buildBow() {
         if (inventory.addResources(GameMechanics.bowCost[0])) { //requires user to pay item cost
            Weapon bow = new Weapon(GameMechanics.compoundBow, 1);
            //arrow level is unlocked for user to purchase to upgrade bow:
            if (arrowLevel <= 0)
            {
               bow.setStrength(0);
               arrowLevel = -1;
            }
            else
            {
               bow.setStrength(GameMechanics.BOWDAMAGE + GameMechanics.ARROWDAMAGE[arrowLevel - 1]);
            }
            return true;
         }
         return false;
    }
	
     //Allows user to upgrade arrows to increase bow damage
    //returns a boolean for if this upgrade is successfull
    //takes in the level of upgrade the user wishes to upgrade to, so the user may skip upgrades to go to more advanced options if available
    public static boolean upgradeArrows(int level) {
        if (arrowLevel > -2 && level > arrowLevel && level <= MAXLEVEL) { //requires bow to be unlocked before being able to upgrade
            if (inventory.addResources(GameMechanics.arrowUpgrades[level - 1])) {
               Weapon bow = (Weapon)inventory.findItemById(GameMechanics.BOWID);
               arrowLevel = level;
               bow.setStrength(GameMechanics.BOWDAMAGE + GameMechanics.ARROWDAMAGE[level-1]); //updates bow damage after obtaining upgrade
               return true;
            }
         }
         return false;
      }

}
