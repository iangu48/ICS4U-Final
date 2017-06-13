   package Game.Room;

    public class Healing extends Item{
   
      private int healed;
   
       public Healing(int id, int amount, int healed) {
         super(id, amount);
         this.setHealed(healed);
      // TODO Auto-generated constructor stub
      }
       
       public Healing (Healing healing)
       {
           super (healing);
           healed = healing.healed;
       }
       
       public Healing (Healing healing, int amount)
        {
            super (healing, amount);
        }
   
       public void setHealed(int healed) {
         this.healed = healed;
      }
   
       public int getHealed() {
         return healed;
      }
   
       public int compareTo(Item other) {
         if (other instanceof Weapon)
            return -1;
         else if (other instanceof Healing)
            return this.healed - ((Healing)other).getHealed();
         else             
            return 1;
      }
      
       public Item retrieve (int amount)
      {
         if (deduct(amount))
            return new Healing(this, amount);
         return null;
      }
   }