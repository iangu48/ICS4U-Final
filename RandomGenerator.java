import java.util.Random;
public class RandomGenerator{
    // Hit is successful
    public static boolean hitChance () {
        return (randomGenerator()>=0.8);
    }
    // Checks if first scene is true
    public static boolean sceneChance (double firstCondition) {
        return (randomGenerator()<=firstCondition);
    }
    // Random Generator
    public static double randomGenerator() {
        Random random = new Random();
        return random.nextDouble();
    }
}