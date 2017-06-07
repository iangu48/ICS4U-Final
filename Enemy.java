public class Enemy extends Entity {       //This is an enemy entity
	String name;                           //name of enemy entity
	int damage;                            //damage enemy deals
	Loot lootDrop;                   //array of items that defeated monsters drop
	String description;                    //
	
	public Enemy() {
		hpMax = 1;
		hpLeft = 1;
		name = null;
		damage = 0;
		lootDrop = null;
	} 
	
	public Enemy(int hp, String name, int damage, Loot drops) {
		hpMax = hp;
		hpLeft = hp;
		this.name = name;
		this.damage = damage;
		lootDrop = drops;
	}
	
	public void attack(Entity other) {
		other.receiveDamage(damage);         //enemy "receives" attack	
	}
	
	public void giveLoot(Player other) {
		other.receiveLoot(lootDrop);        //work in progress
	}
	
	public String toString() {
		return name;
	}
}
	