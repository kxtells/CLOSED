package avideogame.domain;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * A class that represents the game map
 * 
 * @author Jordi Castells
 *
 */
public class Map {
	//un atribut per a la imatge que pot ser l'string de l'ID de Android Resources
	private Bitmap image;
	private Bitmap imagemap;
	private ArrayList<MapHotSpot> mhs = new ArrayList<MapHotSpot>();
	
	
	/**
	 * Checks if a given position with a given radius collides with a Wall
	 * 
	 * @param x position x
	 * @param y position y
	 * @param r distance
	 * @return boolean saying if this map point is wall or not
	 */
	public boolean collides(int x, int y,int r){
		if(isWall(x+r/2,y)) return true;
		else if(isWall(x-r/2,y)) return true;
		else if(isWall(x,y+r/2)) return true;
		else if(isWall(x,y-r/2)) return true;
		return false;
	}
	
	/**
	 * Checks the walkability map in a specified point and 
	 * returns true if this point is a wall.
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isWall(int x, int y){
		if(imagemap.getPixel(x, y) == Color.BLACK) return true;
		return false;
	}
	
	
	/**
	 * Returns the hot spot in a given a position (if exists, null otherwise)
	 * @param x position x
	 * @param y position y
	 * @return MapHotSpot returns the HotSpot in a given point, null if there's no hot spot
	 */
	public MapHotSpot getMapHotSpot(int x, int y){
		int ln = mhs.size();
		for(int i=0;i<ln;i++){
			if(mhs.get(i).isInside(x, y)){return mhs.get(i);}
		}
		return null;
	}
	
	/**
	 * Add a hot spot to the map
	 * @param mhsparam
	 */
	public void addHotSpot(MapHotSpot mhsparam){
		this.mhs.add(mhsparam);
	}
	
	/**
	 * Returns the map width
	 * @return
	 */
	public int getMapWidth(){
		return this.image.getWidth();
	}
	
	//Getters and Setters
	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public Bitmap getImagemap() {
		return imagemap;
	}

	public void setImagemap(Bitmap imagemap) {
		this.imagemap = imagemap;
	}

	public ArrayList<MapHotSpot> getMhs() {
		return mhs;
	}

	public void setMhs(ArrayList<MapHotSpot> mhs) {
		this.mhs = mhs;
	}
	
}
