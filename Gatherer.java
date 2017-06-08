/*
	ICS4U
	Ian Gu
	6/2/2017
	AY Jackson SS
	
	This class is a subclass of worker
 */

public class Gatherer extends Workers{
	
	private static final int PLUSWOOD = 1;
	
	public Resource gatherResources() {
		Item[] items = {
				new Material(PLUSWOOD * this.getNumWorkers(), 0, "wood")
						};
		
		return new Resource(items);
		
	}


}
