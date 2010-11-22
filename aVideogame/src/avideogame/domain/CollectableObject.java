package avideogame.domain;

import java.util.ArrayList;

import android.graphics.Bitmap;

/**
 * A class representing any Object that
 * the player can grab
 * @author Jordi Castells
 *
 */
public class CollectableObject {
	private int id;
	//A name for the object
	private String name;
	//A image for the Object
	private int image;
	//String a mostrar a la vista d'objectes, NO a l'escena
	private String info;
	private String interacttext;
	private int combines_with = -1;
	private ArrayList<Integer> transforms_to = new ArrayList<Integer>();
	
	
	public void addTransformstoObjectId(int newid){
		transforms_to.add(newid);
	}
	
	//GETTERS  & SETTERS
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setCombines_with(int combines_with) {
		this.combines_with = combines_with;
	}
	public int getCombines_with() {
		return combines_with;
	}
	public void setTransforms_to(ArrayList<Integer> transforms_to) {
		this.transforms_to = transforms_to;
	}
	public ArrayList<Integer> getTransforms_to() {
		return transforms_to;
	}

	public void setInteracttext(String interacttext) {
		this.interacttext = interacttext;
	}

	public String getInteracttext() {
		return interacttext;
	}

	
	
}
