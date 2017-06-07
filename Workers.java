/*
	ICS4U
	Ian Gu
	6/2/2017
	AY Jackson SS
	This is an abstract class of a worker, with subclasses of each type of worker
 */

public abstract class Workers {
	
	protected int numWorkers = 0;
	
	public abstract Resource gatherResources();

	public void setNumWorkers(int numWorkers) {
		this.numWorkers = numWorkers;
	}
	
	public void addWorkers()
	{
		numWorkers += 1;
	}
	
	public void removeWorkers()
	{
		numWorkers -= 1;
	}

	public int getNumWorkers() {
		return numWorkers;
	}
}
