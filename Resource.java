/*
	ICS4U
	Ian Gu
	6/2/2017
	AY Jackson SS
	
	This class keeps the item class in an array, as a
	way to return resources from the workers to the
	player 
 */

public class Resource {
	
	private Item[] items;
	
	public Resource (Item[] items) {
		this.items = items;
	}
	
	//Combines implicit and explicit Resource into one
	public boolean combineResources(Resource other) {
		int thisLength = this.items.length;
		int otherLength = other.items.length;
		int length = thisLength + otherLength;
		
		Item[] newItems = new Item[length];
		
		for (int i = 0; i < thisLength; i++)
			newItems[i] = this.items[i];
		for (int i = thisLength; i < length; i++)
			newItems[i] = other.items[i-thisLength];
		
		int currentItemCode;
		
		for (int i = 0; i < length-1; i++) {
			currentItemCode = newItems[i].getItemCode();
			for (int j = i+1; j < length; j++) {
				if (newItems[j].getItemCode() == currentItemCode) {
					newItems[i].add(newItems[j]);
				}
			}
		}
		
		boolean valid = true;
		for (int i = 0; i < 6 && valid; i++) {
			if (newItems[i].getAmount() <= 0)
				valid = false;
		}
		if (valid) {
			int lengthCount = 0; 
			for (int i = 0; i < length; i++) {
				if (newItems[i].getAmount() == 0) 
				lengthCount++;
			}
			
			Item[] finalItems = new Item[lengthCount];
			
			int itemIndex = 0;
			for (int i = 0; i < length; i++) {
				
				if (newItems[i].getAmount()!= 0) {
					finalItems[itemIndex] = newItems[i];
					itemIndex++;
				}
			}
			
			this.items = finalItems;
		}
		return valid;
	}
	//Overridden method, does same thing except takes in item
	public boolean combineResources(Item otherItem) {
		Item[] otherItems = {otherItem};
		Resource other = new Resource(otherItems);
		int thisLength = this.items.length;
		int otherLength = other.items.length;
		int length = thisLength + otherLength;
		
		Item[] newItems = new Item[length];
		
		for (int i = 0; i < thisLength; i++)
			newItems[i] = this.items[i];
		for (int i = thisLength; i < otherLength + thisLength; i++)
			newItems[i] = other.items[i-thisLength];
		
		int currentItemCode;
		
		for (int i = 0; i < length-1; i++) {
			currentItemCode = newItems[i].getItemCode();
			for (int j = i+1; j < length; j++) {
				if (newItems[j].getItemCode() == currentItemCode) {
					newItems[i].add(newItems[j]);
					
				}
			}
		}
		
		boolean valid = true;
		for (int i = 0; i < 6 && valid; i++) {
			if (newItems[i].getAmount() <= 0)
				valid = false;
		}
		if (valid) {
			int lengthCount = 0; 
			for (int i = 0; i < length; i++) {
				if (newItems[i].getAmount() == 0) 
				lengthCount++;
			}
			
			Item[] finalItems = new Item[lengthCount];
			
			int itemIndex = 0;
			for (int i = 0; i < length; i++) {
				
				if (newItems[i].getAmount()!= 0) {
					finalItems[itemIndex] = newItems[i];
					itemIndex++;
				}
			}
			
			this.items = finalItems;
		}
		return valid;
	}
	
	//accessor
	public Item[] getItems() {
		return items;
	}
	
	//returns item based on item code
	public Item findItemById(int searchId) {
		for (int i = 0; i < items.length; i++) {
			if (items[i].getItemCode() == searchId)
				return items[i];
		}
		return null;
	}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < items.length; i++)
			s += "\n" + items[i].toString();
		
		return s;
	}
}
