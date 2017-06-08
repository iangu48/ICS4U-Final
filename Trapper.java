/*
	ICS4U
	Ian Gu
	6/2/2017
	AY Jackson SS
	
	This class is a subclass of worker
 */

public class Trapper extends Workers{
	
	private static final int PLUSBAIT = 1;
	private static final int MINUSMEAT = -1;
	
	public Resource gatherResources() {
		Item[] items = {
				new Material(this.getNumWorkers() * PLUSBAIT, 8, "bait"),
				new Material(this.getNumWorkers() * MINUSMEAT, 6, "meat")
						};
		
		return new Resource(items);
	}

	
}
