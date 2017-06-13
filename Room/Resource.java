package Game.Room;

import java.util.*;
import Game.Loot.Loot;

public class Resource {

    private Item[] items;

    public Resource(Item[] items) {
        this.items = items;
    }
    
    public Resource(Loot loot) {
        this.items = loot.getItems();
    }

    public Resource(Resource resource) {
        items = new Item[resource.items.length];
        for (int i = 0; i < resource.items.length; i++) {
            Item temp = resource.items[i];
            if (temp instanceof Material) { // If instance of material
                items[i] = new Material((Material)temp); //Give material of x amount
            }
            else if (temp instanceof Weapon) { //If drop is weapon
                items[i] = new Weapon((Weapon)temp); //Give weapon of x amount
            }
            else if (temp instanceof Healing) { //If drop is Healing
                items[i] = new Healing((Healing)temp); //Give healing of x amount
            }
        }
    }

    public void addItem(Item item) {
        boolean added = false;
        for (int i = 0; i < items.length && !added; i++) {
            if (items[i].addItem(item)) {
                added = true;
            }
        }
        if (!added) {
            items = Arrays.copyOf(items, items.length + 1);
            items[items.length - 1] = item;
        }
    }

    //Combines implicit and explicit Resource into one, does not check if resulting resouce has items with negative amounts
    public void combineResources(Resource other) {
        for (int i = 0; i < other.items.length; i++) {
            Item temp = other.items[i];
            if (temp != null) {
                boolean added = false;
                for (int j = 0; j < items.length && !added; j++) {
                    if (items[j].addItem(temp)) {
                        added = true;
                    }
                }
                if (!added) {
                    items = Arrays.copyOf(items, items.length + 1);
                    items[items.length - 1] = temp;
                }
            }
        }
    }

    //Combines implicit and explicit Resource into one, checks if resulting resouce has items with negative amounts
    public boolean addResources(Resource other) {
        boolean empty = true;
        for (int i = 0; i < other.items.length && empty; i++) {
            if (other.items[i].getAmount() != 0) {
                empty = false;
            }
        }
        if (empty) {
            return true;
        }
        Item[] copy = new Item[items.length];
        for (int i = 0; i < items.length; i++) {
            if (items[i] instanceof Material) { // If instance of material
                copy[i] = new Material((Material) items[i]); //Give material of x amount
            } else if (items[i] instanceof Weapon) { //If drop is weapon
                copy[i] = new Weapon((Weapon) items[i]); //Give weapon of x amount
            } else if (items[i] instanceof Healing) { //If drop is Healing
                copy[i] = new Healing((Healing) items[i]); //Give healing of x amount
            }
        }

        for (int i = 0; i < other.items.length; i++) {
            Item temp = other.items[i];
            boolean added = false;
            for (int j = 0; j < copy.length && !added; j++) {
                if (copy[j].addItem(temp)) {
                    added = true;
                }
            }
            if (!added) {
                copy = Arrays.copyOf(copy, copy.length + 1);
                copy[copy.length - 1] = temp;
            }
        }

        boolean valid = true;
        for (int i = 0; i < copy.length && valid; i++) {
            if (copy[i].getAmount() < 0) {
                valid = false;
            }
        }
        if (valid) {
            items = copy;
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
            if (items[i].getItemCode() == searchId) {
                return items[i];
            }
        }
        return null;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < items.length - 1; i++) {
            s += items[i] + " " + items[i].getAmount() + ", ";
        }
        s += items[items.length - 1] + " " + items[items.length - 1].getAmount();
        return s;
    }
}
