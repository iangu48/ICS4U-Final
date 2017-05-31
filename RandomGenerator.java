import java.util.Random;
public class RandomGenerator{
    public static void main (String[] args){
        System.out.println(randomGenerator());
    }
    public static boolean hitChance () {
        return (randomGenerator()>=0.8);
    }
    public static boolean sceneChance (double firstCondition) {
        return (randomGenerator()>firstCondition);
    }
    public static double randomGenerator() {
        Random random = new Random();
        return random.nextDouble();
    }
}