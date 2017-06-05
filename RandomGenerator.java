/*
    Random Generation
    Class: ICS4U1
    Author: Michael Chang
    Date: May 31 2017
    School: A.Y. Jackson S.S.
    Purpose: Random Number generation and boolean based on chance double
*/

import java.util.Random; //Import random

public class RandomGenerator{
    // Base Random Generator
    private static double randomGenerator() {
        Random random = new Random();
        return random.nextDouble();
    }

    //Returns True or False based on chance
    //Also used for hit is Successful
    public static boolean trueFalse (double chance) {
        return (randomGenerator() <= chance);
    }
    // Returns range from 0 to max
    public static int range(int max) {
        return (int)randomGenerator(max);
    }
    // Returns range from min to max
    public static int range (int min, int max) {
        return (int)(randomGenerator(max)+min);
    }
}
