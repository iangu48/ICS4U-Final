/*
	ICS4U
	Ian Gu
	6/2/2017
	AY Jackson SS
	
	This class is a subclass of worker
 */

public class Steelworker extends Workers{

	public Resource gatherResources() {
		Item[] items = {
				new Material(this.getNumWorkers() * GameMechanics.STEELSTEEL, 2, "steel"),
				new Material(this.getNumWorkers() * GameMechanics.STEELIRON, 1, "iron"),
				new Material(this.getNumWorkers() * GameMechanics.STEELWOOD, 0, "wood")
						};
		
		return new Resource(items);
	}


}
