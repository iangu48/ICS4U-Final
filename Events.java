/*
	Events is to generate encounters and discoveries for the player while on The Dusky Path
*/

public class Events
{
	private String name; 
	private Player user; //guy who's playing
	private TheRoom room;
	
	
	public boolean StartFight (Enemy monster)
	{
		Fight combat = new Fight(user, monster);
		return combat.battle();
	}
	
	public void discoverOutpost()
	{
		Loot loot = RandomGenerator.lootGenerator(CUREDMEAT, 5, 10, 1.0)//loot generator uses item given, min number to give, max numbe to give, and chance to generate a loot
		user.receiveLoot(loot);
		
	}
	
	public void discoverCave()
	{
		
	}
	
	
}