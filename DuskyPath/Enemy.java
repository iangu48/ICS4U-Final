/*
	File Name:	 Enemy.java
	Name:			 Tongli Han
	Class:		 ICS4U1
	Date:			 June 15, 2017
	Description: Keeps track of enemy values: current / max: health / damage.
                Controls enemy's attack, attacked methods.
                Controls over loop drops after defeated.
*/


package Game.DuskyPath;
// import needed classes
   import Game.Loot.*;
   import Game.Room.Item;

   public class Enemy extends Entity {                //This is an enemy entity
      private String name;                            //name of enemy entity
      private int damage;                             //damage enemy deals
      private ItemDrop[] lootDrop;                    //array of possible items that defeated monsters can drop
      private String description;                     //Description of enemy
   
      //Initiallizing Constructor
       public Enemy() {
         super ("Default", 1);
         name = null;
         damage = 0;
         lootDrop = null;
         description = "Debug enemy";
      } 
      //Actual Constructor
       public Enemy(int hp, String name, int damage, ItemDrop[] drops, String description) {
         super (name, hp);
         this.name = name;
         this.damage = damage;
         lootDrop = drops;
         this.description = description;
      }
      //Referencing Constructor 
       public Enemy(Enemy other)
      {
         super(other.name, other.getMaxHealth());
         this.damage = other.damage;
         this.lootDrop = other.lootDrop;
         this.description = other.description;
      }
     //Deal damage equivalent to damage
       public void attack(Entity other) {
         other.receiveDamage(damage);                    //player "receives" attack	
      }
   //Gives player loot drop and adds it to their inventory
       public void giveLoot(Player other) {
         Item[] items = new Item[lootDrop.length];
         for (int i = 0; i < lootDrop.length; i++)
            items[i] = lootDrop[i].drop();
         other.receiveLoot(new Loot(items));   
      }
   //Accessor to description
       public String getDescription()
      {
         return description;
      }
   //Accessor to name
       public String toString() {
         return name;
      }
   }
