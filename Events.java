/*
	Events is to generate encounters and discoveries for the player while on The Dusky Path
*/

public abstract class Events
{
	final static double BASECHANCE = 0.1;
	private double lootDrop;
	private Player user;
	private String description; //description of what is actually happening
	private Enemy[] enemies; //pass in enemies from the event you are executing
	private ItemDrop[] lootOptions; //pass in the potential item drops from the event
	
	public boolean encounterEnemy()
	{
		Enemy monster = enemies[RandomGenerator.range(enemies.length - 1)]; //generate enemies to return to Events so they can use
		Fight combat = new Fight(user, monster);
		return combat.battle();
	}
	
	public Loot obtainLoot()
	{
		 //give back a loot that is obtained from the Event
		 Item [] itemsGained = new Item[items.length];
		 for (int i = 0; i < items.length; i++)
		 {
		 	itemsGained[i] = items[i].generageItem(); //if no item obtained, instance is a null
		 }
		 
		 return (new Loot(itemsGained));
	}
	
	public boolean executeEvent()//supposed to trigger an encounter in the Dusky Path
	{
		if (RandomGenerator.trueFalse(lootDrop)) //checks if player receives loot or not
		{
			lootDrop = BASECHANCE //after obaining loot, loot chance reverts to base
			user.receiveLoot(obtainLoot());
			return true;
		}
		else
		{
			boolean living = encounterEnemy();
			if (living)
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
}