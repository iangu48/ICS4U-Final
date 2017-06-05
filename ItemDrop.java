public class ItemDrop
{
    private Item drop;
    private int min;
    private int max;
    private double chance;

    public Item() {
        if RandomGenerator.trueFalse(chance) {
            int amount = RandomGenerator.range(min,max);
            if (drop instanceof Store) {
                newItem = Store(amount); //Give store of x amount
            } else if (drop instanceof Weapon) {
                newItem = Weapon(amount); //Give weapon of x amount
            } else if (drop instanceof Healing) {
                newItem = Healing(amount); //Give Healing item of x amount
            }
            return newItem; //Return the new item
        }
        return null; //If no item is dropped, chance fails
    }

}