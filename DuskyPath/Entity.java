/*
	File Name:	 Entity.java
	Name:		 Tongli Han, Shawn Wang(GUI/editing)
	Class:		 ICS4U1
	Date:		 June 15, 2017
	Description: Parent class of Player and Enemy
                 Keeps track of their name, and health
                
*/
package Game.DuskyPath;

public abstract class Entity {

    private String name;
    private final int maxHealth;
    private int health;
    //Constructor
    public Entity(String name, int maxHealth) {
        this.name = name;
        this.maxHealth = maxHealth;
        health = maxHealth;
    }
    
    //Accessors
    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }
    
    //Mutators
    public void setHealth(int health) {
        this.health = health;
    }
    
    //heals entity for set amount
    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }
    
    //Accessor
    public String getName() {
        return name;
    }
    
    //Receives set amount of damage
    public void receiveDamage(int amount) {
        health -= amount;
    }
    //Abstract attack method
    public abstract void attack(Entity other);
    
    //check if entity is alive
    public boolean isAlive() {
        return health > 0;
    }
}
