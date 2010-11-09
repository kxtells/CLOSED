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
	//llista amb els objectes que pots combinar-ho i llista amb el resultat de tal combinació
	private ArrayList<CollectableObject> combwith = new ArrayList<CollectableObject>();
	private ArrayList<CollectableObject> transformsto = new ArrayList<CollectableObject>();
	
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
	public ArrayList<CollectableObject> getCombwith() {
		return combwith;
	}
	public void setCombwith(ArrayList<CollectableObject> combwith) {
		this.combwith = combwith;
	}
	public ArrayList<CollectableObject> getTransformsto() {
		return transformsto;
	}
	public void setTransformsto(ArrayList<CollectableObject> transformsto) {
		this.transformsto = transformsto;
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
	
	
}
