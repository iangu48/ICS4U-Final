/*
	ICS4U
	Ian Gu
	6/2/2017
	AY Jackson SS
	
	This class is a subclass of worker
 */

public class Chef extends Workers{

	public static final int PLUSCOOKEDMEAT = 1;
	public static final int MINUSWOOD = -5;
	public static final int MINUSMEAT = -5;
	
	public Resource gatherResources() {
		Item[] items = {
				(new Material(this.getNumWorkers() * PLUSCOOKEDMEAT, 7, "cooked meat")),
				(new Material(this.getNumWorkers() * MINUSWOOD, 0, "wood")),
				(new Material(this.getNumWorkers() * MINUSMEAT, 6, "meat"))		
						};
		
		return new Resource(items);
	}
}
