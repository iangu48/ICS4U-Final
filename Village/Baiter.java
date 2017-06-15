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

public class Baiter extends Workers {

	 //constants
    public static final Material PLUSBAIT = new Material(GameMechanics.BAITID, GameMechanics.BAITERBAIT);
    public static final Material MINUSMEAT = new Material(GameMechanics.MEATID, GameMechanics.BAITERMEAT);

    //Returns final resources of each type
    public Resource gatherResources() {
        int workers = getNumWorkers();
        Item[] items = {new Material(PLUSBAIT, PLUSBAIT.getAmount() * workers), new Material(MINUSMEAT, MINUSMEAT.getAmount() * workers)};
        return new Resource(items);
    }

    public String toString() {
        return "Baiter";
    }
	 
    //Returns types of resources that gatherResources gives
    public Resource getUnitResource() {
        Item[] items = {new Material(PLUSBAIT), new Material(MINUSMEAT)};
        return new Resource(items);
    }
}
