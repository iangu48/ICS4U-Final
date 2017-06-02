
public abstract class Item {
	
	private int amount;
	protected int itemCode;
	
	//Constructor, used by subclasses
	public Item(int amount, int id) {
		this.amount = amount;
		this.itemCode = id;
	}
	
	//Accessor and mutator
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getItemCode() {
		return itemCode;
	}

	//
	public int compareToItem(Item other) {
		return this.amount - other.amount;
	}
	
	public boolean equals(Item other) {
		return false;
	}
	
	//used by retrieve method to deduct amount from implicit item
	private boolean deduct(int num) {
		if (num <= amount) {
			amount -= num;
			return true;
		} else
			return false;
	}
	
	//returns new instance of item with amount, and deducts amount from implicit item; null if num > amount
	public Item retrieve(int num) {
		if (deduct(num)) {
			if (this instanceof Store)
				return new Store(num, this.getItemCode(), ((Store)this).getName());
			else if (this instanceof Weapon)
				return new Weapon(num, this.getItemCode(), ((Weapon)this).getStrength());
			else
				return new Healing(num, this.getItemCode(), ((Healing)this).getHealed());
		} else
			return null;
	}
	
	

}
