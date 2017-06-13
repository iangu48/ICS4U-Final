package Game.Village;

import Game.Room.Resource;

public class Village {

    private static int numVillagers;
    private static Workers[] jobs = {new Gatherer(), new Baiter(), new Hunter(), new Chef(), new IronWorker(), new SteelWorker()};

    //Constructor
    public Village(int numberOfVillagers) {
        numVillagers = numberOfVillagers;
        jobs[0].setNumWorkers(numVillagers);
    }

    public static void load(int[] workers) {
        if (workers.length == jobs.length) {
            for (int i = 0; i < jobs.length; i++) {
                jobs[i].setNumWorkers(workers[i]);
            }
        }
    }

    public static int length() {
        return jobs.length;
    }

    public static boolean addWorker(int index) {
        if (jobs[0].getNumWorkers() > 0) {
            if (index >= 0 && index <= jobs.length) {
                jobs[0].setNumWorkers(jobs[0].getNumWorkers() - 1);
                jobs[index].setNumWorkers(jobs[index].getNumWorkers() + 1);
                return true;
            }
            return false;
        }

        return false;
    }

    public static boolean removeWorker(int index) {
        if (jobs[index].getNumWorkers() > 0) {
            if (index >= 0 && index <= jobs.length) {
                jobs[0].setNumWorkers(jobs[0].getNumWorkers() + 1);
                jobs[index].setNumWorkers(jobs[index].getNumWorkers() - 1);
                return true;
            }
            return false;
        }

        return false;
    }

    //Returns the number of villagers without jobs
    public static int numWorkersAvailable() {
        int count = 0;
        for (int i = 0; i < 6; i++) {
            count += jobs[i].getNumWorkers();
        }
        return numVillagers - count;
    }

    //Returns a worker from the jobs array
    public static Workers getWorker(int index) {
        if (index >= 0 && index < jobs.length) {
            return jobs[index];
        }
        return null;
    }

    //Returns the number of workers for one job
    public static int getNumWorkers(int index) {
        if (index >= 0 && index < jobs.length) {
            return jobs[index].getNumWorkers();
        }
        return -1;
    }

    //Uses gatherResources() from each worker, combines each resource into one, and returns it
    public static Resource gatherResources() {
        Resource resource = jobs[0].gatherResources();
        for (int i = 1; i < 6; i++) {
            resource.addResources(jobs[i].gatherResources());
        }
        return resource;
    }

}
