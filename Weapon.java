
public class Weapon extends Item{

	private int strength;
	
	public Weapon(int amount, int id, int strength) {
		super(amount, id);
		this.setStrength(strength);
		// TODO Auto-generated constructor stub
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getStrength() {
		return strength;
	}
	
}
