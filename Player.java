public class Player extends Entity {
	private int maxWater;
	private int currentWater;
	private int maxStorage;
	private int currentStorage;
	private Loot store;
	private Item strongestWep;
	
	public Player() {
		maxWater = 1;
		currentWater = 0;
		maxStorage = 1;
		currentStorage = 0;
		hpMax = 10;
		strongestWep = null;
	}
	public void equipArmor(int i) {
		hpMax = i;
	}
	public void equipWaterStorage(int i) {
		maxWater = i;
	}
	public void equipStorage(int i) {
		maxStorage = i;
	}
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
	
	public void attack(Entity other) {
		int dmg = ((Weapon)strongestWep).getStrength();   //reads weapon dmg number (assuming index 0 of inventory is weapon)
		other.receiveDamage(dmg);         //enemy "receives" attack	
	}
	
	public void heal(int i) {
		int healing = ((Healing)store.getLoot(4)).getHealed();   //reads healing strength (assuming index of healing is 4)
		
		if (healing > hpMax - hpLeft)
			hpLeft = hpMax;
		else
			hpLeft += healing;
	}
	public boolean buttonPressed() {
		//return true when button pressed
	}
	public int buttonPressed2(){
		//return index of resource
	}
	
	public void prepare(Item wep, Loot other) {  
		
		strongestWep = wep; //button click for weapon (returns weapon itself as item)
		currentStorage += 1;
		
		/*index 0 = Weapon
		  Storage cannot be found
		  Armor cannot be found
		  Waterskin cannot be found */
		  
		  /* FIXXXX
		  	  So basically ur gonna have to make it able for player to add or subtract items from store
		  	  And retrieve returns an item with that amount of items
		  */	
		
		
		while (!buttonPressed()) {
			display(other);
			display(store);
			//After button +1 clicked for certain resource 
			if (currentStorage < maxStorage) {
				Item temp = resource[buttonPressed2()].retrieve(1); 	//deduct from resource put into temp
				for (int i = 0; i < store.length(); i++)
					if (store.getLoot(i).equals(temp))
						store.add(i);
			}
			//After button -1 clicked
			if (store.getLoot(buttonPressed2()).getAmount() > 0) {
				Item temp = resource[buttonPressed2()].retrieve(-1); 	//add from resource put -1 into temp
				for (int i = 0; i < store.length(); i++)
					if (store.getLoot(i).equals(temp))
						store.minus(i);
			}
		}
	}
			
	public void receiveLoot(Loot other) {
	
	/*		Screw the entire code
			U KNOW WHAT U FOKING GOTTA DO
			ONLY INTERACT BETWEEN LOOT AND INVENTORY MUST BE SEPARATE 
			
			do something similar to prepare in which u can add and subtract except u need a retrieve method somewhere else.
	
	*/ 
		
		while (!buttonPressed()) {
			display(other);
			display(store);
			//After button +1 clicked for certain resource 
			if (currentStorage < maxStorage) {
				Item temp = resource[buttonPressed2()].retrieve(1); 	//deduct from resource put into temp
				for (int i = 0; i < store.length(); i++)
					if (store.getLoot(i).equals(temp))
						store.add(i);
			}
			//After button -1 clicked
			if (store.getLoot(buttonPressed2()).getAmount() > 0) {
				Item temp = resource[buttonPressed2()].retrieve(-1); 	//add from resource put -1 into temp
				for (int i = 0; i < store.length(); i++)
					if (store.getLoot(i).equals(temp))
						store.minus(i);
			}
		}
		//2nd prepare class for when loot is picked up :)
	}
	
	public void display(Loot other) {
	//displays loot or inventory, if inventory will be: display(Player.getStore)
	}
	
	
	
	public void selectAll(Loot other) { //picks up all loot if possible (ease of use)
		store.addInventories(other);
	}
	
	public void wipeInventory() {      //necessary for recieve loot logic and death
		for (int i = 0; i < store.length(); i++) 
			store.getLoot(i).setAmount(0);
	}
	
	public boolean isAlive(){	
		if (hpLeft > 0)
			return true;
		else
			return false;
	}
}