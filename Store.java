
public class Store extends Item{

	private String name;
	
	public Store(int amount, int id, String name) {
		super(amount, id);
		this.setName(name);
		// TODO Auto-generated constructor stub
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	

}
