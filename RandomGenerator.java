/*
	Random Generation
	Class: ICS4U1
	Author: Michael Chang
	Date: May 31 2017
	School: A.Y. Jackson S.S.
	Purpose: Random Number generation and boolean based on chance double
*/

import java.util.Random; //Import random

public class RandomGenerator{
	// Base Random Generator
	private static double randomGenerator() {
		Random random = new Random();
		return random.nextDouble();
	}

	//Returns True or False based on chance
	//Also used for hit is Successful
	public static boolean trueFalse (double chance) {
		return (randomGenerator() <= chance);
	}
	// Returns range from 0 to max
	public static int range(int max) {
		return (int)randomGenerator()*(max);
	}
	// Returns range from min to max
	public static int range (int min, int max) {
		return (int)(randomGenerator()*(max)+min);
	}

	public static Item itemDrop (Item drop, int min, int max, double chance) {
		int id = drop.getItemCode(); //Get itemcode from drop
		String name = drop.getName(); //Get name from drop
		Item newItem = null;
		if (trueFalse(chance)) { //If item is dropped
			int amount = range(min, max); // Calculate number to drop		
			if (drop instanceof Store) { // If instance of store
				newItem = new Store(amount, id, name); //Give store of x amount
			}
			else if (drop instanceof Weapon) { //If drop is store
				int strength = ((Weapon)drop).getStrength();
				newItem = new Weapon(amount, id, name, strength); //Give weapon of x amount
			}
			else if (drop instanceof Healing) { //If drop is Healing
				int healing = ((Healing)drop).getHealed();
				newItem = new Healing(amount, id, name, healing); //Give healing of x amount
			}
		}
		return newItem; //If no quantity of item is dropped
	}
}