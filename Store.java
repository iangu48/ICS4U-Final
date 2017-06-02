
public class Store extends Item{

	private String name;
	
	public Store(int amount, String name) {
		super(amount);
		this.setName(name);
		// TODO Auto-generated constructor stub
	}
	
	public boolean equals(Store other) {
		if ((this.name).equals(other.name))
			return true;
		else
			return false;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	

}
