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
    final int HITCHANCE = 0.8-(0.1*DIFFICULTY);
    final int MEATHEAL = 8;
    final int GATHERWOOD =
    // ========== END OF MODIFIED BY DIFFICULTY ==========

    // ========== START OF FINALS ==========
    //Item IDs
    final int WOODID = 0;
    final int IRONID = 1;
    final int STEELID = 2;
    final int TEETHID = 3;
    final int SCALESID = 4;
    final int FURID = 5;
    final int MEATID = 6;
    final int COOKEDMEATID = 7;
    final int BAIT = 8;

    //Armor Constant in levels
    int[] ARMORS = new int[4];
    ARMORS[0] = 10;
    ARMORS[1] = 15;
    ARMORS[2] = 25;
    ARMORS[3] = 45;

    //Water Constants in levels
    int[] WATER = new int[4];
    WATER[0] = 5; //None
    WATER[1] = 10; // Waterskin
    WATER[2] = 20; //Watercask
    WATER[3] = 60; //Iron Cask

    //Store Constants in levels
    int[] CAPACITY = new int[4];
    CAPACITY[0] = 10;
    CAPACITY[1] = 20;
    CAPACITY[2] = 40;
    CAPACITY[3] = 70;

    //Arrow Damage with levels
    int[] ARROWDAMAGE = new int[3];
    ARROWDAMAGE[0] = 0; //Wood Arrow
    ARROWDAMAGE[1] = 2; // Steel Arrow
    ARROWDAMAGE[2] = 5; // Iron Arrow

    //Worker Input
    //Gatherer
    final int GATHERERWOOD = 1;
    //Hunter
    final int HUNTERFUR = 1;
    final int HUNTERMEAT = 1;
    //Trapper
    final int TRAPPERMEAT = -1;
    final int TRAPPERBAIT =1;
    //Chef
    final int CHEFMEAT = -5;
    final int CHEFWOOD = -5;
    final int CHEFCUREDMEAT = 1;
    //Iron Miner
    final int MINERMEAT = -1;
    final int MINERIRON = 1;
    //Steel Worker
    final int STEELIRON = -1;
    final int STEELWOOD = -2;
    final int STEELSTEEL = 1;
    // ========== END OF FINALS ==========

    // ========== START OF WEAPONS ===========

    // ========== END OF WEAPONS ==========
}