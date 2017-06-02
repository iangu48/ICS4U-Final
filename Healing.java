
public class Healing extends Item{

	private int healed;
	
	public Healing(int amount, int healed) {
		super(amount);
		this.setHealed(healed);
		// TODO Auto-generated constructor stub
	}
	
	public boolean equals (Healing other) {
		if (this.healed == other.healed)
			return true;
		else
			return false;
	}

	public void setHealed(int healed) {
		this.healed = healed;
	}

	public int getHealed() {
		return healed;
	}
	
}
