public class Player extends Entity 
{
   private int maxWater;
   private int currentWater;
   private int food;
   private int maxStorage;
   private int currentStorage;
   private int damage = 10;
   private Item[] inventory;
	
   public Player(String name, int maxHealth) 
   {
      super(name, maxHealth);
      maxWater = 15;
      currentWater = 15;
      maxStorage = 10;
      currentStorage = 0;
      food = 10;
      inventory = new Item[3];
      inventory[0] = new Item ("Item 1", 10);
      inventory[1] = new Item ("Item 2", 10);
      inventory[2] = new Item ("Item 3", 10);
   }
   
   public int getMaxWater()
   {
      return maxWater;
   }
   
   public int getWater()
   {
      return currentWater;
   }
   
   public int getFood()
   {
      return food;
   }
		
   public void attack(Entity other) {
      other.receiveDamage(10);
   }
	
   public Item displayInventory(int i) 
   {
      if (i >= 0 && i < inventory.length)
         return inventory[i];
      return null;
   }
	
   public String[] explore ()
   {
      food --;
      String[] text;
      
      if (food > 0)
      {
         text = new String[2];
         text[0] = "Explored some ruins.";
         text[1] = "Placeholder texts.";
      }
      else if (food == 0)
      {
         text = new String[2];
         text[0] = "Explored some ruins.";
         text[1] = "Food has run out.";
      }
      else if (food >= -2)
      {
         text = new String[2];
         text[0] = "Explored some ruins.";
         text[1] = "You are starving.";
      }
      else
      {
         text = new String[1];
         text[0] = "You starved to death.";
         setHealth(0);
      }
      return text;
   }
   
   public boolean isAlive()
   {
      return getHealth() != 0;
   }
}