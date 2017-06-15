package Game.Loot;

import java.util.Arrays;
import Game.Room.*;

public class Loot {

    private Item[] items;

    public Loot(Item[] other) {
        items = other;
    }

    public Loot(int i) {
        items = new Item[i];
    }

    public Loot(Resource resource) {
        items = resource.getItems();
    }

    public Item getLoot(int i) {
        if (i >= 0 && i < length()) {
            return items[i];
        }
        return null;
    }

    public Item getLoot(Item item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(item)) {
                return items[i];
            }
        }
        return null;
    }
    
    public Item[] getItems()
    {
        return items;
    }

    public void setLoot(int i, Item other) {
        items[i] = other;
    }

    public int length() {
        return items.length;
    }

    public Item take(Item item) {
        for (int i = 0; i < items.length; i++) {
            if (item.equals(items[i])) {
                Item temp = items[i].retrieve(1);
                updateArray();
                return temp;
            }
        }
        return null;
    }

    public void add(Item other) {
        boolean added = false;
        for (int i = 0; i < items.length && !added; i++) {
            if (items[i].addItem(other)) {
                added = true;
            }
        }
        if (!added) {
            items = Arrays.copyOf(items, items.length + 1);
            items[items.length - 1] = other;
        }
    }

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

    public int totalInventory() {
        int count = 0;
        for (Item item : items) {
            count += item.getAmount();
        }
        return count;
    }

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
    }

    public void sortLoot() {
        boolean sorted = false;
        for (int i = 0; i < items.length && !sorted; i++) {
            sorted = true;
            for (int j = items.length - 1; j > i; j--) {
                if (items[j].compareTo(items[j - 1]) > 0) {
                    sorted = false;
                    Item temp = items[j - 1];
                    items[j - 1] = items[j];
                    items[j] = temp;
                }
            }
        }
    }
}//Loot class
