/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.Room;

import Game.GameMechanics.GameMechanics;

public abstract class Item {

    private int amount;
    private int itemCode;

    //Constructor, used by subclasses
    public Item(int id, int amount) {
        this.amount = amount;
        this.itemCode = id;
    }

    public Item(Item item) {
        amount = item.getAmount();
        itemCode = item.getItemCode();
    }

    public Item(Item item, int amount) {
        this.amount = amount;
        itemCode = item.getItemCode();
    }

    //Accessor and mutator
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getItemCode() {
        return itemCode;
    }

    public String getName() {
        return GameMechanics.itemNames[itemCode];
    }

    public abstract int compareTo(Item other);

    public boolean equals(Item other) {
        return other != null && itemCode == other.itemCode;
    }

    public boolean addItem(Item other) {
        if (equals(other)) {
            this.amount += other.amount;
            other.amount = 0;
            return true;
        }
        return false;
    }

    public String toString() {
        return getName();
    }

    //used by retrieve method to deduct amount from implicit item
    public boolean deduct(int num) {
        if (num <= amount) {
            amount -= num;
            return true;
        } else {
            return false;
        }
    }

    //returns new instance of item with amount, and deducts amount from implicit item; null if num > amount
    public abstract Item retrieve(int num);
}