
public class Resource {
	private Item[] items;
	
	public Resource (Item[] items) {
		this.items = items;
	}
	
	//Combines implicit and explicit Resource into one
	public Resource combineResources(Resource other) {
		int thisLength = this.items.length;
		int otherLength = other.items.length;
		int length = thisLength + otherLength;
		
		Item[] newItems = new Item[length];
		
		for (int i = 0; i < thisLength; i++)
			newItems[i] = this.items[i];
		for (int i = thisLength; i < otherLength + thisLength; i++)
			newItems[i] = other.items[i];
		
		return new Resource(newItems);
	}
	
}
