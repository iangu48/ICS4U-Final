/*
	ICS4U
	Ian Gu
	6/2/2017
	AY Jackson SS
	
	This class is a subclass of worker
 */

public class Steelworker extends Workers{
	
	private static final int PLUSSTEEL = 1;
	private static final int MINUSIRON = -1;
	private static final int MINUSWOOD = -2;

	public Resource gatherResources() {
		Item[] items = {
				new Material(this.getNumWorkers() * PLUSSTEEL, 2, "steel"),
				new Material(this.getNumWorkers() * MINUSIRON, 1, "iron"),
				new Material(this.getNumWorkers() * MINUSWOOD, 0, "wood")
						};
		
		return new Resource(items);
	}


}
