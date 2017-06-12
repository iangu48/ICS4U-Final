package Game.Room;

import Game.Loot.ItemDrop;
import Game.GameMechanics.GameMechanics;

public class Room {

    /*
		item codes:
		0: wood
		1: iron
		2: steel
		3: teeth
		4: scales
		5: fur
		6: meat
		7: cooked_meat
		8: bait
		
		Water Storage:
		Level 0: 5 Nothing
		Level 1: 10 waterskin
		Level 2: 20 watercask
		Level 3: 60 ironcask
		
		Storage:
		Level 0: 10
		Level 1: 20
		Level 2: 40
		Level 3: 70
     */
    //Need class constants for item drops
    private static final int MAXLEVEL = 3;
    private static Resource inventory;
    private static int armourLevel = 0;
    private static int storageLevel = 0;
    private static int arrowLevel = -1;
    private static int healthLevel = 0;
    private static int waterLevel = 0;
    
    public Room ()
    {
        armourLevel = 0;
        storageLevel = 0;
        arrowLevel = -1;
        healthLevel = 0;
        waterLevel = 0;
        inventory = new Resource (new Item[0]);
    }
    
    public Room(int armour, int storage, int arrow, int health, int water, Item[] items)
    {
        armourLevel = armour;
        storageLevel = storage;
        arrowLevel = arrow;
        healthLevel = health;
        waterLevel = water;
        inventory = new Resource(items);
    }
    
    public static boolean changeResources(Resource other) {
        return inventory.addResources(other);
    }

    public static Resource getInventory() {
        return inventory;
    }

    public static void gatherWood() {
        Item[] wood = {GameMechanics.WOOD.drop()};
        inventory.addResources(new Resource(wood));
    }

    public static void checkTrap() {
        for (int i = 0; i < numTraps; i++) {
            //change constants using Constant class
            Item meat = MEAT.drop();
            Item scales = SCALES.drop();
            Item teeth = TEETH.drop();

            Item[] gains = {meat, scales, teeth};
            Resource obtained = new Resource(gains);

            inventory.addResources(obtained);
        }

    }

    public static boolean buildTrap() {
        if (inventory.addResources(/*use Constants class to get cost*/new Resource(new Item[0]))) {
            numTraps++;
            return true;
        }
        return false;
    }

    public static boolean upgradeHealth(int level) {
        if (level > healthLevel && level < MAXLEVEL) {
            if (inventory.addResources(/*use upgrade constant from constant class*/new Resource(new Item[0]))) {
                healthLevel = level;
                return true;
            }
        }
        return false;
    }

    public static boolean upgradeArmour(int level) {
        if (level > armourLevel && level < MAXLEVEL) {
            if (inventory.addResources(/*use upgrade constant from constant class*/new Resource(new Item[0]))) {
                armourLevel = level;
                return true;
            }
        }
        return false;
    }

    public static boolean upgradeStorage(int level) {
        if (level > storageLevel && level < MAXLEVEL) {
            if (inventory.addResources(/*use upgrade constant from constant class*/new Resource(new Item[0]))) {
                storageLevel = level;
                return true;
            }
        }
        return false;
    }

}
