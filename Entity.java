public abstract class Entity {
	int hpMax; //max health of entity
	int hpLeft; //current health of entity during fight
	
	public Entity() {
		hpMax = 1;
		hpLeft = 1;
	}
	public int getHpMax(){
		return hpMax;
	}
	public int getHpLeft() {
		return hpLeft;
	}
	public void setHpMax(int i){
		hpMax = i;
	}
	public void setHpLeft(int i){
		hpLeft = i;
	}
	final double HITCHANCE = 0.8;
	
	public boolean receiveDamage(int i) {
		if (i > hpLeft)
			return false;
		else
			hpLeft = hpLeft - i;
			return true;
	}
	
	public abstract void attack(Entity other);
	
	
}//Entity Class