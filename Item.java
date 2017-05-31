
public abstract class Item {
	private int amount;
	
	public Item(int amount) {
		this.amount = amount;
	}
	
	public int compareToItem(Item other) {
		return this.amount - other.amount;
	}
	
	public boolean deduct(int num) {
		if (num <= amount) {
			amount -= num;
			return true;
		} else
			return false;
	}
	
	//returns null if num > amount
	public Item retrieve(int num) {
		if (deduct(num)) {
			if (this instanceof Store)
				return new Store(num,((Store)this).getName());
			else if (this instanceof Weapon)
				return new Weapon(num, ((Weapon)this).getStrength());
			else
				return new Healing(num, ((Healing)this).getHealed());
		} else
			return null;
	}

}
