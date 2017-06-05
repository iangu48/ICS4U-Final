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

	public abstract void setNumWorkers(int numWorkers);

	public abstract int getNumWorkers();
}
