/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.DuskyPath;

/**
 *
 * @author shawn
 */
public abstract class Entity {

    private String name;
    private final int maxHealth;
    private int health;

    public Entity(String name, int maxHealth) {
        this.name = name;
        this.maxHealth = maxHealth;
        health = maxHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    public String getName() {
        return name;
    }

    public void receiveDamage(int amount) {
        health -= amount;
    }

    public abstract void attack(Entity other);

    public boolean isAlive() {
        return health > 0;
    }
}
