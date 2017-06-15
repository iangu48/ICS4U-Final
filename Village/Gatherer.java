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

	 //constants
    public static final Material PLUSWOOD = new Material(GameMechanics.WOODID, GameMechanics.GATHERERWOOD);

    //Returns final resources of each type
    public Resource gatherResources() {
        int workers = getNumWorkers();
        Item[] items = {new Material(PLUSWOOD, PLUSWOOD.getAmount() * workers)};
        return new Resource(items);
    }

    public String toString() {
        return "Gatherer";
    }

    //Returns types of resources that gatherResources gives
    public Resource getUnitResource() {
        Item[] items = {new Material(PLUSWOOD)};
        return new Resource(items);
    }
}
