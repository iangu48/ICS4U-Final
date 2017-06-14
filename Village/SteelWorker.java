package Game.Village;

import Game.GameMechanics.GameMechanics;
import Game.Room.*;

public class SteelWorker extends Workers {

    private static final Material PLUSSTEEL = new Material(GameMechanics.STEELID, GameMechanics.STEELSTEEL);
    private static final Material MINUSIRON = new Material(GameMechanics.IRONID, GameMechanics.STEELIRON);
    private static final Material MINUSWOOD = new Material(GameMechanics.WOODID, GameMechanics.STEELWOOD);

    public Resource gatherResources() {
        int workers = getNumWorkers();
        Item[] items = {new Material(PLUSSTEEL, PLUSSTEEL.getAmount() * workers), new Material(MINUSIRON, MINUSIRON.getAmount() * workers), new Material(MINUSWOOD, MINUSWOOD.getAmount() * workers)};
        return new Resource(items);
    }

    public String toString() {
        return "Steel worker";
    }

    public Resource getUnitResource() {
        Item[] items = {new Material(PLUSSTEEL), new Material(MINUSIRON), new Material(MINUSWOOD)};
        return new Resource(items);
    }
}
