package Game.Loot;

import java.util.Random; //Import random

public class RandomGenerator {
    // Base Random Generator

    private static double randomGenerator() {
        Random random = new Random();
        return random.nextDouble();
    }

    //Returns True or False based on chance
    //Also used for hit is Successful
    public static boolean trueFalse(double chance) {
        return (randomGenerator() <= chance);
    }
    // Returns range from 0 to max

    public static int range(int max) {
        return (int) randomGenerator() * (max);
    }
    // Returns range from min to max

    public static int range(int min, int max) {
        return (int) (randomGenerator() * (max) + min);
    }
}
