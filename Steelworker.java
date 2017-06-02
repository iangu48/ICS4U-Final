
public class Steelworker extends Workers{
	
	private static final int PLUSSTEEL = 1;
	private static final int MINUSIRON = -1;
	private static final int MINUSWOOD = -2;

	public Resource gatherResources() {
		Item[] items = {
				new Store(this.getNumWorkers() * PLUSSTEEL, 2, "steel"),
				new Store(this.getNumWorkers() * MINUSIRON, 1, "iron"),
				new Store(this.getNumWorkers() * MINUSWOOD, 0, "wood")
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
