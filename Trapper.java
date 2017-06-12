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
				new Material(this.getNumWorkers() * GameMechanics.TRAPPERBAIT, 8, "bait"),
				new Material(this.getNumWorkers() * GameMechanics.TRAPPERMEAT, 6, "meat")
						};
		
		return new Resource(items);
	}

	
}
