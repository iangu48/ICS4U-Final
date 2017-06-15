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

public class SteelWorker extends Workers {

	 //constants
    private static final Material PLUSSTEEL = new Material(GameMechanics.STEELID, GameMechanics.STEELSTEEL);
    private static final Material MINUSIRON = new Material(GameMechanics.IRONID, GameMechanics.STEELIRON);
    private static final Material MINUSWOOD = new Material(GameMechanics.WOODID, GameMechanics.STEELWOOD);

    //Returns final resources of each type
    public Resource gatherResources() {
        int workers = getNumWorkers();
        Item[] items = {new Material(PLUSSTEEL, PLUSSTEEL.getAmount() * workers), new Material(MINUSIRON, MINUSIRON.getAmount() * workers), new Material(MINUSWOOD, MINUSWOOD.getAmount() * workers)};
        return new Resource(items);
    }

    public String toString() {
        return "Steel worker";
    }

    //Returns types of resources that gatherResources gives
    public Resource getUnitResource() {
        Item[] items = {new Material(PLUSSTEEL), new Material(MINUSIRON), new Material(MINUSWOOD)};
        return new Resource(items);
    }
}
