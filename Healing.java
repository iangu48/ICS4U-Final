
public class Healing extends Item{

	private int healed;
	
	public Healing(int amount, int id, int healed) {
		super(amount, id);
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
