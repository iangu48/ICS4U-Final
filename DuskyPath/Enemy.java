   package Game.DuskyPath;

   import Game.Loot.*;
   import Game.Room.Item;

    public class Enemy extends Entity {       //This is an enemy entity
      private String name;                           //name of enemy entity
      private int damage;                            //damage enemy deals
      private ItemDrop[] lootDrop;                   //array of possible items that defeated monsters can drop
      private String description;                    //Description of enemy
   
       public Enemy() {
         super ("Default", 1);
         name = null;
         damage = 0;
         lootDrop = null;
         description = "Debug enemy";
      } 
   
       public Enemy(int hp, String name, int damage, ItemDrop[] drops, String description) {
         super (name, hp);
         this.name = name;
         this.damage = damage;
         lootDrop = drops;
         this.description = description;
      }
   
       public Enemy(Enemy other)
      {
         super(other.name, other.getMaxHealth());
         this.damage = other.damage;
         this.lootDrop = other.lootDrop;
         this.description = other.description;
      }
   
       public void attack(Entity other) {
         other.receiveDamage(damage);         //enemy "receives" attack	
      }
   
       public void giveLoot(Player other) {
         Item[] items = new Item[lootDrop.length];
         for (int i = 0; i < lootDrop.length; i++)
            items[i] = lootDrop[i].drop();
         other.receiveLoot(new Loot(items));        //work in progress
      }
   
       public String getDescription()
      {
         return description;
      }
   
       public String toString() {
         return name;
      }
   }