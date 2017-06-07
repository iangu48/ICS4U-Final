public class Loot {
	Item[] items;
	
	public Loot(Item[] other) {
		items = other;
	}
	
	public Item getLoot(int i) {
		return items[i];
	} 
	
	public int length() {
		return items.length;
	}
	
	public void minus(int i) {
		items[i].setAmount(items[i].getAmount()-1);
	}
	
	public void add(int i) {
		items[i].setAmount(items[i].getAmount()+1);
	}
	
	public int totalInventory() {
		int count = 0;
		for (int i = 0; i < items.length; i++) 
			count += items[i].getAmount();
		return count;
	}
	public void addInventories(Loot other) {
		for (int i = 0; i < items.length; i++) 
			for (int j = 0; j < other.length(); j++) 
				if (items[i].equals(other.getLoot(j))) 
					items[i].setAmount(items[i].getAmount() + other.getLoot(j).getAmount());
	}
}//Loot class