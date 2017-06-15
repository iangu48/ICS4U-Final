/*
	File Name:	 Loot.java
	Name:			 Tong Li Han
	Class:		 ICS4U1
	Date:			 June 15, 2017
	Description: Represents an array of items in the dusky path, used by enemies, loot drops, and player. 
                Organizes the items owned by said entity.
               
*/
package Game.Loot;

//import needed classes
import java.util.Arrays;
import Game.Room.*;

public class Loot {

   private Item[] items;
   //Referencing Constructor 1
   public Loot(Item[] other) {
      items = other;
   }
   //Initialzing Constructor
   public Loot(int i) {
      items = new Item[i];
   }
   //Referencing Constructor 2
   public Loot(Resource resource) {
      items = resource.getItems();
   }
   //returns item in the array with i index
   public Item getLoot(int i) {
      if (i >= 0 && i < length()) {
         return items[i];
      }
      return null;
   }
   //returns item in array by comparing id
   public Item getLoot(Item item) {
      for (int i = 0; i < items.length; i++) {
         if (items[i] != null && items[i].equals(item)) {
            return items[i];
         }
      }
      return null;
   }
   //Accessor
   public Item[] getItems()
   {
      return items;
   }
   //Mutator
   public void setLoot(int i, Item other) {
      items[i] = other;
   }
   //returns length of array
   public int length() {
      return items.length;
   }
   //return item that is taken from inventory
   public Item take(Item item) {
      for (int i = 0; i < items.length; i++) {
         if (item.equals(items[i])) {
            Item temp = items[i].retrieve(1);
            return temp;
         }
      }
      return null;
   }
   //add item to array
   public void add(Item other) {
      boolean added = false;
      for (int i = 0; i < items.length && !added; i++) {
         if (items[i] != null && items[i].addItem(other)) {
            added = true;
         }
      }
      if (!added) {
         items = Arrays.copyOf(items, items.length + 1);
         items[items.length - 1] = other;
      }
   }
   //increases array if necessary when item is not present in array
   public void updateArray() {
      int empty = 0;
      for (int i = 0; i < items.length; i++) {
         if (items[i].getAmount() == 0) {
            empty++;
            for (int j = i; j < items.length - 1; j++) {
               items[j] = items[j + 1];
            }
         }
      }
      items = Arrays.copyOf(items, items.length - empty);
      sortLoot();
   }
   //returns inventory count
   public int totalInventory() {
      int count = 0;
      for (Item item : items) {
         count += item.getAmount();
      }
      return count;
   }
   //merges two inventories
   public void addInventories(Loot other) {
      for (int i = 0; i < items.length; i++) {     //cycles through other Loot
         Item tempItem = other.getLoot(i); 			//store item into a temp variable
         boolean added = false;                  //check if other Loot has been added
         for (int j = 0; j < other.length() && !added; j++) { //cycles through implicit Loot
            if (items[j].equals(tempItem)) {
               items[j].addItem(items[j]); //adds them
               added = true;
            }
         }
         if (!added) {
            tempItem = other.getLoot(items.length);
            Item[] newItems = Arrays.copyOf(items, items.length + 1);       //adds that unadded item to end of store and that is placed in a new Loot item
            newItems[items.length] = tempItem;
            items = newItems;
         }
      }
      updateArray();
   }
   //sorts array
   public void sortLoot() {
      for (int i = 0; i < items.length; i++) {
         for (int j = 0; j < items.length - 1; j++) {
            if (items[j].compareTo(items[j + 1]) < 0) {
               Item temp = items[j + 1];
               items[j + 1] = items[j];
               items[j] = temp;
            }
         }
      }
   }
   //to String
   public String toString() {
      String s = "";
      for (int i = 0; i < items.length - 1; i++) {
         s += items[i] + " " + items[i].getAmount() + ", ";
      }
      s += items[items.length - 1] + " " + items[items.length - 1].getAmount();
      return s;
   }
     
}//Loot class
