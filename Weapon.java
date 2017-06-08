/*
	ICS4U
	Ian Gu
	6/2/2017
	AY Jackson SS
	
	This class is a subclass item, which represents
	weapons
 */

public class Weapon extends Item{

	private int strength;
	
	public Weapon(int amount, int id, String name, int strength) {
		super(amount, id, name);
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
