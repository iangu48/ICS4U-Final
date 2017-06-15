/*
	ICS4U
	Ian Gu
	6/2/2017
	AY Jackson SS
	
	This class is a subclass of item, which represents
	a resource (not the class, but literally a resource
	such as meat, wood, etc..)
 */
package Game.Room;

public class Material extends Item {

    public Material(int id, int amount) {
        super(id, amount);
        // TODO Auto-generated constructor stub
    }

    public Material(Material material) {
        super(material);
    }

    public Material(Material material, int amount) {
        super(material, amount);
    }

    public int compareTo(Item other) {
        if (other instanceof Material) {
            return getItemCode() - other.getItemCode();
        } else {
            return -1;
        }
    }

    public Item retrieve(int amount) {
        if (deduct(amount)) {
            return new Material(this, amount);
        }
        return null;
    }
}
