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
	public void combineResources(Resource other) {
		int thisLength = this.items.length;
		int otherLength = other.items.length;
		int length = thisLength + otherLength;
		
		Item[] newItems = new Item[length];
		
		for (int i = 0; i < thisLength; i++)
			newItems[i] = this.items[i];
		for (int i = thisLength; i < otherLength + thisLength; i++)
			newItems[i] = other.items[i];
		
		int currentItemCode;
		
		for (int i = 0; i < length-1; i++) {
			currentItemCode = newItems[i].getItemCode();
			for (int j = i+1; j < length; j++) {
				if (newItems[j].getItemCode() == currentItemCode) {
					newItems[i].setAmount(newItems[i].getAmount() + newItems[j].getAmount());
					newItems[j].setAmount(0);
					
				}
			}
		}
		
		int lengthCount = 0; 
		for (int i = 0; i < length; i++) {
			if (newItems[i].getAmount() == 0) 
			lengthCount++;
		}
		
		Item[] finalItems = new Item[lengthCount];
		
		int itemIndex = 0;
		for (int i = 0; i < lengthCount; i++) {
			
			if (newItems[i].getAmount()!= 0)
		}
		
		
		
		return new Resource(newItems);
	}
	
}
