import java.io.*;

public class GameMechanics {
/*
    try
    {
        String fileName = "";
        FileReader reader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            //PARSE
        }
        reader.close();
    } catch IOException e) {
    }
*/

    // ========== START OF MODIFIED BY DIFFICULTY ==========
    public static final int DIFFICULTY = 0;
    public static final double HITCHANCE = 0.8-(0.1*DIFFICULTY);
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
    public static final int BAIT = 8;

    //Armor Constant in levels
    public static int [] ARMORS = {10, 15, 25, 45};

    //Water Constants in levels
    public static int[] WATER = {5, 10, 20, 60};

    //Store Constants in levels
    public static int [] CAPACITY = {10, 20, 40, 70};

    //Arrow Damage with levels
    public static int [] ARROWDAMAGE = {0, 2, 5};

    //Worker Input
    //Gatherer
    public static final int GATHERERWOOD = 1;
    //Hunter
    public static final int HUNTERFUR = 1;
    public static final int HUNTERMEAT = 1;
    //Trapper
    public static final int TRAPPERMEAT = -1;
    public static final int TRAPPERBAIT =1;
    //Chef
    public static final int CHEFMEAT = -5;
    public static final int CHEFWOOD = -5;
    public static final int CHEFCUREDMEAT = 1;
    //Iron Miner
    public static final int MINERMEAT = -1;
    public static final int MINERIRON = 1;
    //Steel Worker
    public static final int STEELIRON = -1;
    public static final int STEELWOOD = -2;
    public static final int STEELSTEEL = 1;
    // ========== END OF FINALS ==========

    // ========== START OF UPGRADE COSTS ==========
    
    //This method is to get the number of items required for each upgrade,so we don't have to implement dynamic arrays
    public static int[] numItems(String file)
    {
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(file));
            int [] numUpgrades = new int[3];
            String str; //the file input
            for (int i = 0; i < numUpgrades.length; i++)
            {
               int numItems = 0;
               while((str = in.readLine()).charAt(0)!= ' ')//once the reader encounters a space instead of a number, we know that that upgrade set is complete
               {
                  numItems++;
               }
               numUpgrades[i] = numItems;
            }
            return numUpgrades; //the index of this array represents which upgrade and the number represents how many items are required for each upgrade
            
        }
        catch(IOException iox)
        {
            return null;
        }
    }
    
    public static Resource[] costs(String file)
    {
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(file));
            Resource[] upgrades = new Resource[3];  //there are 3 upgrades for every upgrade possible (ex. armour, weapon, health, etc)          
            int [] numItems = numItems(file); //get the number of items required for each upgrade
            for (int i = 0; i < upgrades.length; i++)
            {
               Item[] costs = new Item[numItems[i]];//in each upgrade, the number of item it takes utilizes the numItems method, so dynamic arrays are unnecessary
               for (int j = 0; j < costs.length; j++)
               {
                  String item = in.readLine();
                  String[] str = item.split(" ");
                  int code = Integer.parseInt(str[0]);
                  int amount = (Integer.parseInt(str[1]))*(-1);
                  String name = " "; //TheRoom.getInventory().findItemById(code).getName();
                  costs[i] = new Material(amount, code, name);  
               }
               upgrades[i] = new Resource(costs);
               in.readLine();
            }
            
            return upgrades;
        }
        catch(IOException iox)
        {
            return null;
        }
    }
    
    //Upgrade Cost Instantiation: 
    public static Resource[] swordUpgrades = costs("swordCosts.txt");
    public static Resource[] healthUpgrades = costs("healthCosts.txt");
    public static Resource[] storageUpgrades = costs("storageCosts.txt");
    public static Resource[] waterUpgrades = costs("waterCosts.txt");
    public static Resource[] armorUpgrades = costs("armorCosts.txt");
    // ========== END OF UPGRADE COSTS ==========

    // ========== START OF WEAPONS ===========

    // ========== END OF WEAPONS ==========
}
