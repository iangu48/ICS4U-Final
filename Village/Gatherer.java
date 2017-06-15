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

public class Gatherer extends Workers {

    public static final Material PLUSWOOD = new Material(GameMechanics.WOODID, GameMechanics.GATHERERWOOD);

    public Resource gatherResources() {
        int workers = getNumWorkers();
        Item[] items = {new Material(PLUSWOOD, PLUSWOOD.getAmount() * workers)};
        return new Resource(items);
    }

    public String toString() {
        return "Gatherer";
    }

    public Resource getUnitResource() {
        Item[] items = {new Material(PLUSWOOD)};
        return new Resource(items);
    }
}
