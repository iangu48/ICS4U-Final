/*
	ICS4U
	Ian Gu
	6/2/2017
	AY Jackson SS
	
	This class is a subclass of worker
 */

public class Trapper extends Workers{
	
	public Resource gatherResources() {
		Item[] items = {
				new Material(this.getNumWorkers() * GameMechanics.TRAPPERBAIT, GameMechanics.BAITID, "bait"),
				new Material(this.getNumWorkers() * GameMechanics.TRAPPERMEAT, GameMechanics.MEATID, "meat")
						};
		
		return new Resource(items);
	}

	
}
