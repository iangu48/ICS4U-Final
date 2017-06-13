package Game.Village;

import Game.Room.Resource;

public abstract class Workers {

    protected int numWorkers = 0;

    public abstract Resource gatherResources();

    public void setNumWorkers(int numWorkers) {
        this.numWorkers = numWorkers;
    }

    public void addWorkers() {
        numWorkers += 1;
    }

    public void removeWorkers() {
        numWorkers -= 1;
    }

    public int getNumWorkers() {
        return numWorkers;
    }

    public abstract String toString();
    public abstract Resource getUnitResource();
}
