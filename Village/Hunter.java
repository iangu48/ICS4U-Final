package Game.Village;

import Game.GameMechanics.GameMechanics;
import Game.Room.*;

public class Hunter extends Workers {

    public static final Material PLUSMEAT = new Material(GameMechanics.MEATID, GameMechanics.HUNTERMEAT);
    public static final Material PLUSFUR = new Material(GameMechanics.FURID, GameMechanics.HUNTERFUR);

    public Resource gatherResources() {
        int workers = getNumWorkers();
        Item[] items = {new Material(PLUSMEAT, PLUSMEAT.getAmount() * workers), new Material(PLUSFUR, PLUSFUR.getAmount() * workers)};
        return new Resource(items);
    }

    public String toString() {
        return "Hunter";
    }
    
    public Resource getUnitResource()
    {
        Item[] items = {new Material(PLUSMEAT), new Material(PLUSFUR)};
        return new Resource(items);
    }
}
