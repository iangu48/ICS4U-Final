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
    
    public ItemDrop(int id, String name, int min, int max, double chance)
    {
        this.id = id;
        this.name = name;
        this.min = min; //Get min
        this.max = max; //Get max
        this.chance = chance; //Get chance
        drop = new Material(1, id, name);
    }

    public Item drop() {
        Item newItem = null;
        if (RandomGenerator.trueFalse(chance)) { //If item is dropped
            int amount = RandomGenerator.range(min, max); // Calculate number to drop
            if (drop instanceof Material) { // If instance of material
                newItem = new Material(amount, id, name); //Give material of x amount
            }
            else if (drop instanceof Weapon) { //If drop is weapon
                int strength = ((Weapon)drop).getStrength();
                newItem = new Weapon(amount, id, name, strength); //Give weapon of x amount
            }
            else if (drop instanceof Healing) { //If drop is Healing
                int healing = ((Healing)drop).getHealed();
                newItem = new Healing(amount, id, name, healing); //Give healing of x amount
            }
        }
        return newItem; //If no quantity of item is dropped
    }

}