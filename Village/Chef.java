/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.Village;

import Game.GameMechanics.GameMechanics;
import Game.Room.*;

public class Chef extends Workers {

    public static final Material PLUSCOOKEDMEAT = new Material(GameMechanics.COOKEDMEATID, GameMechanics.CHEFCOOKEDMEAT);
    public static final Material MINUSWOOD = new Material(GameMechanics.WOODID, GameMechanics.CHEFWOOD);
    public static final Material MINUSMEAT = new Material(GameMechanics.MEATID, GameMechanics.CHEFMEAT);

    public Resource gatherResources() {
        int workers = getNumWorkers();
        Item[] items = {new Material(MINUSWOOD, MINUSWOOD.getAmount() * workers), new Material(MINUSMEAT, MINUSMEAT.getAmount() * workers), new Material(PLUSCOOKEDMEAT, PLUSCOOKEDMEAT.getAmount() * workers)};
        return new Resource(items);
    }

    public String toString() {
        return "Chef";
    }
    
    public Resource getUnitResource()
    {
        Item[] items = {new Material(PLUSCOOKEDMEAT), new Material(MINUSWOOD), new Material(MINUSMEAT)};
        return new Resource(items);
    }
}
