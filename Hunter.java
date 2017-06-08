/*
	ICS4U
	Ian Gu
	6/2/2017
	AY Jackson SS
	
	This class is a subclass of worker
 */

public class Hunter extends Workers{
	
	private static final int PLUSWOOD = 1;
	private static final int PLUSMEAT = 1;

	public Resource gatherResources() {
		Item[] items = {
				new Material(this.getNumWorkers() * PLUSWOOD, 0, "wood"),
				new Material(this.getNumWorkers() * PLUSMEAT, 6, "meat")
						};
		
		return new Resource(items);
	}

}
