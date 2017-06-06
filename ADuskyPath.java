/*
    Made by: Michael and Raghav
    Date: June 05, 2017
    Purpose: To manage all the details of player exploration, and to generate all the encounters
             the player will face
*/

public class ADuskyPath {
    int BASECHANCE=0.1; //minimum loot chance
    private double lootDrop; //loot chance
    private Player user; //player who's playing
    private Enemy[] enemies; //pass in enemies from the event you are executing
    private ItemDrop[] lootOptions; //pass in the potential item drops from the event
    private String description; //description of what is actually happening
    
    public ADuskyPath(Player user)
    {
	this.user = user;
	lootDrop = BASECHANCE;
	description = "";
    }
    public String getDescription()
    {
	return description;
    }

    public boolean encounterEnemy() //execute when player runs into a monster
    {
        Enemy monster = enemies[RandomGenerator.range(enemies.length - 1)]; //generate enemies to return to Events so they can use
        //Manage battle between player and enemy:
        Fight newFight = new Fight(user,monster);
        description = "YOU ENCOUNTER A " + monster + ". PREPARE FOR BATTLE";

        if (newFight.startFight()) {
            monster.giveLoot(user);
            return true;
        }
        return false;
    }

    public Loot obtainLoot()
    {
        //give back a loot that is obtained from the Event
        Item [] itemsGained = new Item[items.length];
        for (int i = 0; i < items.length; i++)
        {
            itemsGained[i] = items[i].generageItem(); //if no item obtained, instance is a null
        }

        lootFound = new Loot(itemsGained);
		description = "YOU FIND " + lootFound;
		return (lootFound);
    }
    public boolean executeEvent()//supposed to trigger an encounter in the Dusky Path
    {
        if (RandomGenerator.trueFalse(lootDrop)) //checks if player receives loot or not
        {
            lootDrop = BASECHANCE //after obaining loot, loot chance reverts to base
            user.receiveLoot(Events.obtainLoot());
            return true;
        }
        else
        {
            boolean living = encounterEnemy(); //Takes boolean from encounterEnemy
            if (living) //If Survives
            {
                lootDrop += 0.1; //after defeating enemy, loot chance goes up
            }
            else
            {
                lootDrop = BASECHANCE;//if dead, loot chance reverts to base
            }
            return living;
        }
    }

    public boolean explore ()
    {
        while(continuing){
            executeEvent();
	    description = "";
        }
    }
}
