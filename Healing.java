/*
	ICS4U
	Ian Gu
	6/2/2017
	AY Jackson SS
	
	This class is a subclass of item, representing
	the healing items of the game
 */


public class Healing extends Item{

	private int healed;
	
	public Healing(int amount, int id, String name, int healed) {
		super(amount, id, name);
		this.setHealed(healed);
		// TODO Auto-generated constructor stub
	}

	public void setHealed(int healed) {
		this.healed = healed;
	}

	public int getHealed() {
		return healed;
	}
	
}
