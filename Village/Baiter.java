package Game.Village;

import Game.GameMechanics.GameMechanics;
import Game.Room.*;

public class Baiter extends Workers {

    public static final Material PLUSBAIT = new Material(GameMechanics.BAITID, GameMechanics.BAITERBAIT);
    public static final Material MINUSMEAT = new Material(GameMechanics.MEATID, GameMechanics.BAITERMEAT);

    public Resource gatherResources() {
        int workers = getNumWorkers();
        Item[] items = {new Material(PLUSBAIT, PLUSBAIT.getAmount() * workers), new Material(MINUSMEAT, MINUSMEAT.getAmount() * workers)};
        return new Resource(items);
    }

    public String toString() {
        return "Baiter";
    }

    public Resource getUnitResource() {
        Item[] items = {new Material(PLUSBAIT), new Material(MINUSMEAT)};
        return new Resource(items);
    }
}
