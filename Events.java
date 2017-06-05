/*
	Events is to generate encounters and discoveries for the player while on The Dusky Path
*/

public abstract class Events
{
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
	
	
}