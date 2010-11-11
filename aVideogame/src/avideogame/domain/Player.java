package avideogame.domain;

import java.util.ArrayList;

import avideogame.present.R;

public class Player {
	private int x;
	private int y;
	private ArrayList<CollectableObject> bag = new ArrayList<CollectableObject>();
	private int current_action; //info,grab o usar objecte (veure constants MENU_XXX)
	private int current_object;
	
	/**
	 * Adds an object to the player's bag
	 * @param co
	 */
	public void addObject(CollectableObject co){
		bag.add(co);
	}
	
	/**
	 * Drops a specific Object from the player's bag
	 * @param co
	 */
	public void dropObject(int co){
		bag.remove(co);
	}
	
	/**
	 * Returns the object with the specified id, null if no object is found
	 * @param index
	 * @return
	 */
	public CollectableObject getBagObjectById(int iditem){
		for(int i=0;i<this.bag.size();i++){
			if(bag.get(i).getId() == iditem){
				return bag.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Returns the id of the object (is the same as the resource image id)
	 * or -1 if the object is not found in the player's bag
	 * @return
	 */
	public int getCurrentObjectDrawResource(){
		int ln = bag.size();
		for(int i=0;i<ln;i++){
			if(bag.get(i).getId() == this.current_object) return bag.get(i).getImage();
		}
		return -1;
	}
	
	//GETTERS & SETTERS
	public Player(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public ArrayList<CollectableObject> getBag() {
		return bag;
	}

	public int getCurrent_action() {
		return current_action;
	}

	public void setCurrent_action(int currentAction) {
		current_action = currentAction;
	}

	public void setCurrent_object(int current_object) {
		this.current_object = current_object;
	}

	public int getCurrent_object() {
		return current_object;
	}
	
	
	
}
