/*
	ICS4U
	Ian Gu
	6/2/2017
	AY Jackson SS
	
	This class is a subclass of worker
 */

public class Ironworker extends Workers{ 
	
	public Resource gatherResources() {
		Item[] items = {
				(new Material(this.getNumWorkers() * GameMechanics.MINERIRON, GameMechanics.IRONID, "iron")),
				(new Material(this.getNumWorkers() * GameMechanics.MINERMEAT,  GameMechanics.COOKEDMEATID, "cooked meat"))
						};
		
		return new Resource(items);
	}
}
