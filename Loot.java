   import java.util.Arrays;
   
   public class Loot {
      Item[] items;
   
       public Loot(Item[] other) {
         items = other;
      }
       public Loot(int i) {
         items = new Item[i];
      }
   
       public Item getLoot(int i) {
         return items[i];
      } 
       public void setLoot(int i, Item other) {
         items[i] = other;
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
         for (int i = 0; i < items.length; i++) {     //cycles through other Loot
            Item tempItem = other.getLoot(i); 			//store item into a temp variable
            int unadded;
            boolean added = false;                  //check if other Loot has been added
            for (int j = 0; j < other.length() && !added; j++) { //cycles through implicit Loot
               if (items[j].equals(tempItem)) {  
                  items[j].add(items[j]); //adds them
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
      }
   }//Loot class