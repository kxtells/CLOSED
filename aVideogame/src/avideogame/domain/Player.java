package avideogame.domain;

import java.util.ArrayList;

public class Player {
	private int x;
	private int y;
	private ArrayList<CollectableObject> bag = new ArrayList<CollectableObject>();
	
	
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
	 * Returns the object with the specified index
	 * @param index
	 * @return
	 */
	public CollectableObject getBagObject(int index){
		return bag.get(index);
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
	
	
	
}
