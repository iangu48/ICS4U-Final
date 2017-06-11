public class TheRoom
{
	private static final int MAXLEVEL = 3;
	private static Resource inventory;
	private static int  armorLevel = 0;
	private static int storageLevel = 0;
	private static int arrowLevel = -1;
	private static int healthLevel = 0;
	private static int waterLevel = 0;
	
	public static void changeResources(Resource other)
	{
		inventory.combineResources(other);
	}
	
	public static Resource getInventory()
	{
		return inventory;
	}
	
	public static void gatherWood()
	{
		Item wood = GameMechanics.WOOD.drop();
		inventory.combineResources(wood);
	}
	
	public static void checkTrap()
	{
		Item trap = inventory.findItemById(GameMechanics.TRAPID);
		int numTraps = trap.getAmount();
		
		for (int i = 0; i < numTraps; i++)
		{
			Item meat = GameMechanics.MEAT.drop();
			Item scales = GameMechanics.SCALES.drop();
			Item teeth = GameMechanics.TEETH.drop();
			
			Item[] gains = {meat, scales, teeth};
			Resource obtained = new Resource(gains);
			
			inventory.combineResources(obtained);
		}
	}
	
	public static boolean buildTrap()
	{
		if (inventory.combineResources(GameMechanics.trapCost[0]))
		{
			inventory.combineResources(GameMechanics.trap);
			return true;
		}
		return false;
	}
	
	public static boolean upgradeHealth(int level)
	{
		if (level > healthLevel && level <= MAXLEVEL)
		{
			if (inventory.combineResources(GameMechanics.healthUpgrades[level - 1]))	
			{
				healthLevel = level;
				return true;
			}
		}
		return false;
	}
	
	public static boolean upgradeArmor(int level)
	{
		if (level > armorLevel && level <= MAXLEVEL)
		{
			if (inventory.combineResources(GameMechanics.armorUpgrades[level - 1]))	
			{
				armorLevel = level;
				return true;
			}
		}
		return false;
	}
	
	public static boolean upgradeStorage(int level)
	{
		if (level > storageLevel && level <= MAXLEVEL)
		{
			if (inventory.combineResources(GameMechanics.storageUpgrades[level - 1]))	
			{
				storageLevel = level;
				return true;
			}
		}
		return false;
	}
	
	public static boolean upgradeWater(int level)
	{
		if (level > waterLevel && level <= MAXLEVEL)
		{
			if (inventory.combineResources(GameMechanics.waterUpgrades[level - 1]))	
			{
				waterLevel = level;
				return true;
			}
		}
		return false;
	}
	
	public static boolean buildSword(int level)
	{
		if (inventory.combineResources(GameMechanics.swordUpgrades[level - 1]))
		{
			inventory.combineResources(GameMechanics.swords[level]);
			return true;
		}
		return false;
	}
	
	public static boolean buildBow()
	{
		if (inventory.combineResources(GameMechanics.bowCost[0]))
		{
			inventory.combineResources(GameMechanics.compoundBow);
			arrowLevel = 0;
			return true;
		}
		return false;
	}
	
   public static boolean upgradeArrows(int level)
   {
      if (arrowLevel >-1 && level > arrowLevel && level <  MAXLEVEL)
      {
         if (inventory.combineResources(GameMechanics.arrowUpgrades[level - 1]))
         {
            arrowLevel = level;
            return true;
         }
      } 
      return false;
   }
	
}