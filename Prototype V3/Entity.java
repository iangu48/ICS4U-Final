public abstract class Entity 
{
   private String name;
   private int maxHealth;
   private int health; 
	
   public Entity (String name, int maxHealth)
   {
      this.name = name;
      this.maxHealth = maxHealth;
      health = maxHealth;
   }
   
   public int getMaxHealth ()
   {
      return maxHealth;
   }
   
   public int getHealth ()
   {
      return health;
   }
   
   public void setHealth(int health)
   {
      this.health = health;
   }
   
   public String getName()
   {
      return name;
   }
   
   public boolean receiveDamage(int amount) 
   {
      if (health > amount)
      {
         health -= amount;
         return true;
      }
      return false;
   }
	
   public abstract void attack(Entity other);
}