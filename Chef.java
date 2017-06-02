
public class Chef extends Workers{

	public static final int PLUSCOOKEDMEAT = 1;
	public static final int MINUSWOOD = -5;
	public static final int MINUSMEAT = -5;
	
	public Resource gatherResources() {
		Item[] items = {
				(new Store(this.getNumWorkers() * PLUSCOOKEDMEAT, 7, "cooked meat")),
				(new Store(this.getNumWorkers() * MINUSWOOD, 0, "wood")),
				(new Store(this.getNumWorkers() * MINUSMEAT, 6, "meat"))		
						};
		
		return new Resource(items);
	}

	public int getNumWorkers() {
		// TODO Auto-generated method stub
		return numWorkers;
	}

	public void setNumWorkers(int numWorkers) {
		// TODO Auto-generated method stub
		this.numWorkers = numWorkers;
	}
}
