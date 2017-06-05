public class Enemy extends Entity
{
   private int damage;
   private String[] encounterDescription;
   private String[] deathDescription;
   
   public Enemy(int maxHealth) {
      super("Default enemy", maxHealth);
      damage = 1;
      encounterDescription = new String[3];
      encounterDescription[0] = "You encountered the default enemy.";
      encounterDescription[1] = "Debugging use. Should appear after previous line.";
      encounterDescription[2] = "Someone needs to write this text for every enemy by the way.";
      deathDescription = new String[2];
      encounterDescription[0] = "You defeated the default enemy.";
      encounterDescription[1] = "Someone also needs to write this as well.";
   } 
	
   public Enemy(String name, int maxHealth, int damage, String[] encounterDescription, String[] deathDescription)
   {
      super(name, maxHealth);
      this.damage = damage;
      this.encounterDescription = encounterDescription;
      this.deathDescription = deathDescription;
   }
	
   public void attack(Entity other) {
      other.receiveDamage(damage);
   }
   
   public String[] encounter()
   {
      return encounterDescription;
   }
   
   public String[] death ()
   {
      return deathDescription;
   }
	
}
