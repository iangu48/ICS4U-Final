/*
	ICS4U
	Ian Gu
	6/2/2017
	AY Jackson SS
	
	This class is a subclass of worker
 */
package Game.Village;

import Game.GameMechanics.GameMechanics;
import Game.Room.*;

public class IronWorker extends Workers {

    public static final Material PLUSIRON = new Material(GameMechanics.IRONID, GameMechanics.MINERIRON);
    public static final Material MINUSCOOKEDMEAT = new Material(GameMechanics.COOKEDMEATID, GameMechanics.MINERMEAT);

    public Resource gatherResources() {
        int workers = getNumWorkers();
        Item[] items = {new Material(PLUSIRON, PLUSIRON.getAmount() * workers), new Material(MINUSCOOKEDMEAT, MINUSCOOKEDMEAT.getAmount() * workers)};
        return new Resource(items);
    }

    public String toString() {
        return "Iron worker";
    }

    public Resource getUnitResource() {
        Item[] items = {new Material(PLUSIRON), new Material(MINUSCOOKEDMEAT)};
        return new Resource(items);
    }
}
