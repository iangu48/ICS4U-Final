/*
	ICS4U
	Ian Gu
	6/2/2017
	AY Jackson SS
	
	This class keeps track of the "village" of the game,
	which includes all the villagers, and their jobs
 */


public class TheVillage {
	
	public static int numVillagers;
	public static Workers[] jobs = {	new Gatherer(),
										new Trapper(),
										new Hunter(),
										new Chef(),
										new Ironworker(),
										new Steelworker()
										};
	
	//Constructor
	public TheVillage(int numberOfVillagers) {
		numVillagers = numberOfVillagers;
		jobs[0].setNumWorkers(numVillagers);
	}
	
	//Change jobs method: assigns certain number of villages to a job
	// public boolean changeJobs(int index, int number) {
// 		if(index >= 0 && index <= 5) {
// 			int count = 0;
// 			for (int i = 0; i < 6; i++) {
// 				if (i != index)
// 					count += jobs[i].getNumWorkers();
// 			}
// 			
// 			if (count + number <= numVillagers) {
// 				jobs[index].setNumWorkers(number);
// 				return true;
// 			} else
// 				return false;
// 		}else
// 			return false;
// 	}

	public boolean addWorker(int index)
	{
		if (jobs[0].getNumWorkers() > 0)
		{
			if (index >=0 && index <=jobs.length)
			{
				jobs[0].setNumWorkers(jobs[0].getNumWorkers() - 1);
				jobs[index].setNumWorkers(jobs[index].getNumWorkers() + 1);
				return true;
			}
			return false;
		}
		
		return false;
	}
			
	public boolean removeWorker(int index)
	{
		if (jobs[index].getNumWorkers() > 0)
		{
			if (index >=0 && index <=jobs.length)
			{
				jobs[0].setNumWorkers(jobs[0].getNumWorkers() + 1);
				jobs[index].setNumWorkers(jobs[index].getNumWorkers() - 1);
				return true;
			}
			return false;
		}
		
		return false;
	}
	

	//Returns the number of villagers without jobs
	public int numWorkersAvailable() {
		int count = 0;
		for (int i = 0; i < 6; i++)
			count += jobs[i].getNumWorkers();
		return numVillagers - count;
	}
	
	//Uses gatherResources() from each worker, combines each resource into one, and returns it
	public Resource gatherResources() {
		Resource resource = jobs[0].gatherResources();
		for (int i = 1; i < 6; i++)
			resource.combineResources(jobs[i].gatherResources());
		return resource;
	}
	
}