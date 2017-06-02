
public class Weapon extends Item{

	private int strength;
	
	public Weapon(int amount, int strength) {
		super(amount);
		this.setStrength(strength);
		// TODO Auto-generated constructor stub
	}
	
	public boolean equals (Weapon other) {
		if (this.strength == other.strength)
			return true;
		else
			return false;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getStrength() {
		return strength;
	}
	
}
