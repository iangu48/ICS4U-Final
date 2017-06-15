/*
Class Name: ItemDrop
Authors: Michael and Raghav
Date: June 15, 2017
School: AY Jackson SS
Purpose: To package together loot chances and items into one object, to randomly generate items for the player while
         on expeditions or after defeating an enemy.
*/

package Game.Loot;

import Game.Room.*;
import Game.GameMechanics.GameMechanics;

public class ItemDrop
{
    private int id;
    private int min; //min amount of items that may be obtained 
    private int max; //max amount of items that may be obtained
    private double chance;
    private Item drop; //Item type of the drop
    private String name;

    //This constructor takes in the type of Item in the parameters to instantiate it
    public ItemDrop(Item drop, int min, int max, double chance) {
        id = drop.getItemCode(); //Get itemcode from drop
        name = drop.getName(); //Get name from drop
        this.drop = drop; //Get Drop
        this.min = min; //Get min
        this.max = max; //Get max
        this.chance = chance; //Get chance
    }
    
    //This constructor takes in the id of the Item in the parameters to instantiate it
    public ItemDrop(int id, int min, int max, double chance){
        this.id = id;
        this.min = min;
        this.max = max;
        this.chance = chance;
        //instantiates item type using codes:
        if (id >=GameMechanics.WOODID && id <=GameMechanics. || id == 8 || id == GameMechanics.TRAPID)
      	{
            drop = new Material(id, 1);
         }
      	 else if (id >= GameMechanics.FISTID && id <= GameMechanics.STEELSWORDID)
         {
             drop = new Weapon(id, 1, GameMechanics.SWORDDAMAGE[id - 9]);
         }
         else if (id == GameMechanics.BOWID)
         {
             drop = new Weapon(id, 1, GameMechanics.BOWDAMAGE);
         }
         else
         {
             drop = new Healing(id, 1, GameMechanics.MEATHEAL);
         }
    }

    //This method utilizes the fields of the ItemDrop class to return an item that is randomly generated
    public Item drop() {
        Item newItem = null;
        if (RandomGenerator.trueFalse(chance)) { //If item is dropped
            int amount = RandomGenerator.range(min, max); // Calculate number to drop
            if (drop instanceof Material) { // If instance of material
                newItem = new Material((Material)drop, amount); //Give material of x amount
            }
            else if (drop instanceof Weapon) { //If drop is weapon
                newItem = new Weapon((Weapon)drop, amount); //Give weapon of x amount
            }
            else if (drop instanceof Healing) { //If drop is Healing
                newItem = new Healing((Healing)drop, amount); //Give healing of x amount
            }
        }
        return newItem; //If no quantity of item is dropped
    }

}
