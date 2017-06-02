
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

	public int getNumWorkers() {
		// TODO Auto-generated method stub
		return numWorkers;
	}

	public void setNumWorkers(int numWorkers) {
		// TODO Auto-generated method stub
		this.numWorkers = numWorkers;
	}

}
