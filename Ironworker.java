/*
	ICS4U
	Ian Gu
	6/2/2017
	AY Jackson SS
	
	This class is a subclass of worker
 */

public class Ironworker extends Workers{

	private static final int PLUSIRON = 1;
	private static final int MINUSCOOKEDMEAT = -1;
	
	public Resource gatherResources() {
		Item[] items = {
				(new Store(this.getNumWorkers() * PLUSIRON, 1, "iron")),
				(new Store(this.getNumWorkers() * MINUSCOOKEDMEAT, 7, "cooked meat"))
						};
		
		return new Resource(items);
	}
}
