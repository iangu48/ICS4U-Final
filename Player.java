/* 	Player
	Tong Li Han
	June 12, 2017
	A.Y. Jackson S.S.
	This class represents the player within "the dusky path" and is responsible for the addition of loot from enemies
	or the dusky path itself. The class also has methods to run the fight class.
*/

public class Player extends Entity {
	private int maxWater;
	private int currentWater;
	private int maxStorage;
	private int currentStorage;
	private Loot store;
	private Item strongestWep;
	
	//Player Constructor, initiallizes all values before adding boosts from items
	public Player() {
		maxWater = 1;
		currentWater = 0;
		maxStorage = 1;
		currentStorage = 0;
		hpMax = 10;
		strongestWep = null;
	}
	//mutators
	public void equipArmor(int i) {
		hpMax = i;
	}
	public void equipWaterStorage(int i) {
		maxWater = i;
	}
	public void equipStorage(int i) {
		maxStorage = i;
	}
	//accessors
	public int getMaxWater() {
		return maxWater;
	}
	public int getMaxtStorage() {
		return maxStorage;
	}
	public int getCurrentWater() {
		return currentWater;
	}
	public int getCurrentStorage() {
		return currentStorage;
	}
	public String getStrongestWep() {
		return strongestWep.toString();
	}
	// Attack method for player onto an enemy. Takes in Player implicitely, Enemy explicitely
	public void attack(Entity other) {
		int dmg = ((Weapon)strongestWep).getStrength();   //reads weapon dmg number 
		other.receiveDamage(dmg);         //enemy "receives" attack	
	}
	// Heal method
	public void heal() {
		int healing = ((Healing)store.getLoot(4)).getHealed();   //reads healing strength (assuming index of healing is 4)
		
		if (healing > hpMax - hpLeft)
			hpLeft = hpMax;
		else
			hpLeft += healing;
	}
	public boolean buttonPressed() {
		//return true when button pressed
	}
	public Item buttonPressed2(){
		//return item with amount +1 or -1
	}
	//Method to bring an item from "The Room" to "The Dusky Path" (equiping)
	public void bringItem(int i) {
		if (currentStorage < maxStorage) {
			Item temp = other.getInventory().changeResources(buttonPressed2()); 	//deduct from resource put into temp
			for (int i = 0; i < store.length(); i++)
				if (store.getLoot(i).equals(temp))
					store.add(i);
		}
	}
	//Method to return an item from "The Dusky Path" to "The Room" (dequiping)
	public void returnItem(int i, theRoom other) {
		if (store.getLoot(buttonPressed2()).getAmount() > 0) {
			Item temp = other.getInventory().changeResources(buttonPressed2()); 	//add from resource put -1 into temp
			for (int i = 0; i < store.length(); i++)
				if (store.getLoot(i).equals(temp))
					store.minus(i);
		}
	}
	
	//Display inventories
	public void display(Loot other) {
	//displays loot or inventory, if inventory will be: display(Player.getStore)
	}
	//Take in all loot when found
	public void selectAll(Loot other) { //picks up all loot if possible (ease of use)
		store.addInventories(other);
	}
	//Wipes your inventory
	public void wipeInventory() {      //necessary for death
		for (int i = 0; i < store.length(); i++) 
			store.getLoot(i).setAmount(0);
	}
	//Checks if player is still alive after receiving attack.
	public boolean isAlive(){	
		if (hpLeft > 0)
			return true;
		else
			return false;
	}
}
