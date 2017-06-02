
public class Hunter extends Workers{
	
	private static final int PLUSWOOD = 1;
	private static final int PLUSMEAT = 1;

	public Resource gatherResources() {
		Item[] items = {
				new Store(this.getNumWorkers() * PLUSWOOD, 0, "wood"),
				new Store(this.getNumWorkers() * PLUSMEAT, 6, "meat")
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
