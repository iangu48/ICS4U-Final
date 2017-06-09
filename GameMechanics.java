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
    public static final int HITCHANCE = 0.8-(0.1*DIFFICULTY);
    public static final int MEATHEAL = 8;
    public static final int GATHERWOOD =
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
    public static int ARMORS = new int[4];
    ARMORS[0] = 10;
    ARMORS[1] = 15;
    ARMORS[2] = 25;
    ARMORS[3] = 45;

    //Water Constants in levels
    public static int WATER = new int[4];
    WATER[0] = 5; //None
    WATER[1] = 10; // Waterskin
    WATER[2] = 20; //Watercask
    WATER[3] = 60; //Iron Cask

    //Store Constants in levels
    public static int CAPACITY = new int[4];
    CAPACITY[0] = 10;
    CAPACITY[1] = 20;
    CAPACITY[2] = 40;
    CAPACITY[3] = 70;

    //Arrow Damage with levels
    public static int ARROWDAMAGE = new int[3];
    ARROWDAMAGE[0] = 0; //Wood Arrow
    ARROWDAMAGE[1] = 2; // Steel Arrow
    ARROWDAMAGE[2] = 5; // Iron Arrow

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

    // ========== END OF UPGRADE COSTS ==========

    // ========== START OF WEAPONS ===========

    // ========== END OF WEAPONS ==========
}