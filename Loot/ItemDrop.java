package Game.Loot;

import Game.Room.*;
import Game.GameMechanics.GameMechanics;

public class ItemDrop
{
    private int id;
    private int min;
    private int max;
    private double chance;
    private Item drop;
    private String name;

    public ItemDrop(Item drop, int min, int max, double chance) {
        id = drop.getItemCode(); //Get itemcode from drop
        name = drop.getName(); //Get name from drop
        this.drop = drop; //Get Drop
        this.min = min; //Get min
        this.max = max; //Get max
        this.chance = chance; //Get chance
    }
    
    public ItemDrop(int id, int min, int max, double chance){
        this.id = id;
        this.min = min;
        this.max = max;
        this.chance = chance;
        if (id >=0 && id <=6 || id == 8 || id == 14)
      	{
            drop = new Material(id, 1);
         }
      	 else if (id >= 9 && id <= 12)
         {
             drop = new Weapon(id, 1, GameMechanics.SWORDDAMAGE[id - 9]);
         }
         else if (id == 13)
         {
             drop = new Weapon(id, 1, GameMechanics.BOWDAMAGE);
         }
         else
         {
             drop = new Healing(id, 1, GameMechanics.MEATHEAL);
         }
    }

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
