
public abstract class Workers {
	
	protected int numWorkers = 0;
	
	public abstract Resource gatherResources();

	public abstract void setNumWorkers(int numWorkers);

	public abstract int getNumWorkers();
}
