
public class Trapper extends Workers{
	
	private static final int PLUSBAIT = 1;
	private static final int MINUSMEAT = -1;
	
	public Resource gatherResources() {
		Item[] items = {
				new Store(this.getNumWorkers() * PLUSBAIT, 8, "bait"),
				new Store(this.getNumWorkers() * MINUSMEAT, 6, "meat")
						};
		
		return new Resource(items);
	}

	public int getNumWorkers() {
		return numWorkers;
	}

	public void setNumWorkers(int numWorkers) {
		// TODO Auto-generated method stub
		this.numWorkers = numWorkers;
	}

	
}