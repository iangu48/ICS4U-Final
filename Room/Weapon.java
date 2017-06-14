   package Game.Room;

    public class Weapon extends Item{
   
      private int strength;
   
       public Weapon(int id, int amount, int strength) {
         super(id, amount);
         this.setStrength(strength);
      // TODO Auto-generated constructor stub
      }
       
       public Weapon (Weapon weapon)
       {
           super (weapon);
           strength = weapon.strength;
       }
       
       public Weapon (Weapon item, int amount)
        {
            super (item, amount);
        }
   
       public void setStrength(int strength) {
         this.strength = strength;
      }
   
       public int getStrength() {
         return strength;
      }
   
       public int compareTo(Item other) {
         if (other instanceof Weapon)
            return strength - ((Weapon)other).getStrength();
         else
            return 1;
      }
      
       public Item retrieve (int amount)
      {
         if (deduct(amount))
            return new Weapon(this, amount);
         return null;
      }
   }
