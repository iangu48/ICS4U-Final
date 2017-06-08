/*
	ICS4U
	Ian Gu
	6/2/2017
	AY Jackson SS
	
	This is an abstract class which represents an item,
	with the three subclasses of types of items
 */

public abstract class Item {
	
	private int amount;
	protected int itemCode;
	private String name;
	
	//Constructor, used by subclasses
	public Item(int amount, int id, String name) {
		this.amount = amount;
		this.itemCode = id;
		this.name = name;
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

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	//
	public int compareToItem(Item other) {
		return this.amount - other.amount;
	}
	
	public boolean equals(Item other) {
		return this.itemCode == other.itemCode;
	}
	
	public String toString() {
		return itemCode + " " + name + " x" + amount; 
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
			if (this instanceof Material)
				return new Material(num, this.getItemCode(), ((Material)this).getName());
			else if (this instanceof Weapon)
				return new Weapon(num, this.getItemCode(), name, ((Weapon)this).getStrength());
			else
				return new Healing(num, this.getItemCode(), name, ((Healing)this).getHealed());
		} else
			return null;
	}
	
	public void add(Item other) {
		if (this.equals(other)) {
			this.amount += other.amount;
			other.amount = 0;
		}
	}

}
