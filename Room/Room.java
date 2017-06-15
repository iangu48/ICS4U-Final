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

    public static boolean changeResources(Resource other) {
        return inventory.addResources(other);
    }

    public static boolean changeResources(Loot loot) {
        Resource resource = new Resource(loot);
        return changeResources(resource);
    }

    public static Resource getInventory() {
        return inventory;
    }

    public static void gatherWood() {
        Item wood = GameMechanics.WOOD.drop();
        inventory.addItem(wood);
    }

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
            Item meat = GameMechanics.MEAT.drop();
            Item scales = GameMechanics.SCALES.drop();
            Item teeth = GameMechanics.TEETH.drop();
				
				Item[] gains = {meat, scales, teeth};
				
				if (numBait > 0)
				{
					numBait--;
					for (int j = 0; j < gains.length; j++)
					{
						Item temp = gains[j];
						if (temp != null)
						{
							temp.setAmount(temp.getAmount() * 2);
						}
					}
				}
            Resource obtained = new Resource(gains);

            inventory.combineResources(obtained);
        }
    }

    public static boolean buildTrap() {
        Item traps = inventory.findItemById(14);
        if (traps == null || traps.getAmount() < 10) {
            Resource expense = new Resource(GameMechanics.trapCost[0]);
            if (inventory.addResources(expense)) {
                Item copy = new Material(GameMechanics.trap);
                inventory.addItem(copy);
                return true;
            }
        }
        return false;
    }

    public static boolean upgradeArmor(int level) {
        if (level > armorLevel && level <= MAXLEVEL) {
            if (inventory.addResources(GameMechanics.armorUpgrades[level - 1])) {
                armorLevel = level;
                return true;
            }
        }
        return false;
    }

    public static boolean upgradeStorage(int level) {
        if (level > storageLevel && level <= MAXLEVEL) {
            if (inventory.addResources(GameMechanics.storageUpgrades[level - 1])) {
                storageLevel = level;
                return true;
            }
        }
        return false;
    }

    public static boolean upgradeWater(int level) {
        if (level > waterLevel && level <= MAXLEVEL) {
            if (inventory.addResources(GameMechanics.waterUpgrades[level - 1])) {
                waterLevel = level;
                return true;
            }
        }
        return false;
    }

    public static boolean buildSword(int level) {
        if (inventory.addResources(GameMechanics.swordUpgrades[level - 1])) {
            inventory.addItem(GameMechanics.swords[level]);
            return true;
        }
        return false;
    }

    public static boolean buildBow() {
        if (inventory.addResources(GameMechanics.bowCost[0])) {
            inventory.addItem(GameMechanics.compoundBow);
            arrowLevel = 0;
            return true;
        }
        return false;
    }

    public static boolean upgradeArrows(int level) {
        if (arrowLevel > -1 && level > arrowLevel && level < MAXLEVEL) {
            if (inventory.addResources(GameMechanics.arrowUpgrades[level - 1])) {
                arrowLevel = level;
                Weapon bow = (Weapon)inventory.findItemById(GameMechanics.BOWID);
                bow.setStrength(bow.getStrength() + GameMechanics.ARROWDAMAGE[level - 1]);
                return true;
            }
        }
        return false;
    }

}
