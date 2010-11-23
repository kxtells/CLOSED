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
	 * @ToImplement
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
	 * @ToImplement
	 * @param imgmappath path to the image map (black&white)
	 * 
	 * Aqui s'hauria de carregar la imatge del mapa de manera que es
	 * pugui consultar a nivell de píxel si hi ha paret o no.
	 */
	public void loadImageMap(String imgmappath){
		
	}
	
	/**
	 * Returns the nearest hot spot given a position
	 * @ToImplement
	 * @param x position x
	 * @param y position y
	 * @return MapHotSpot returns the NearestHotSpot in a given radius, null if there's no near hot spot
	 * @note Potser seria interessant que retornés directament la escena associada al hotspot
	 */
	public MapHotSpot getNearestHotSpot(int x, int y){
		return null;
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
