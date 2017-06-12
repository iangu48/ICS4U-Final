/*
	ICS4U
	Ian Gu
	6/2/2017
	AY Jackson SS
	
	This class is a subclass of worker
 */

public class Hunter extends Workers{

	public Resource gatherResources() {
		Item[] items = {
				new Material(this.getNumWorkers() * GameMechanics.HUNTERFUR, GameMechanics.FURID, "fur"),
				new Material(this.getNumWorkers() * GameMechanics.HUNTERMEAT, GameMechanics.MEATID, "meat")
						};
		
		return new Resource(items);
	}

}
