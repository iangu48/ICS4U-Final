public class Scene
{
	private Enemy[] enemies; //pass in enemies from the event you are executing
	private ItemDrop[] lootOptions; //pass in the potential item drops from the event
	
	public Enemy generateEnemy()
	{
		return enemies[range(enemies.length - 1)]; //generate enemies to return to Events so they can use
		
	}
	
	public Loot obtainLoot(ItemDrop[] items)
	{
		 //give back a loot that is obtained from the Event
		 Item [] itemsGained = new Item[items.length];
		 for (int i = 0; i < items.length; i++)
		 {
		 	itemsGained[i] = items[i].generageItem();
		 }
		 
		 return (new Loot(itemsGained));
	}
}
