public class Resource
{
   private String name;
   private int amount;
	
   public Resource(String name) 
   {
      this.name = name;
      amount = 0;
   }
   
   public Resource(String name, int amount) 
   {
      this.name = name;
      this.amount = amount;
   }
   
   public String getName()
   {
      return name;
   }
	
   public int getAmount() 
   {
      return amount;
   }
   
   public void addAmount (int amount)
   {
      this.amount += amount;
   }
   
   public boolean reduceAmount (int amount)
   {
      if (this.amount >= amount)
      {
         this.amount -= amount;
         return true;
      }
      return false;
   }
   
}