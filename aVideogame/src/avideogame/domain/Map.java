package avideogame.domain;

import java.util.ArrayList;

/**
 * A class that represents the game map
 * 
 * @author Jordi Castells
 *
 */
public class Map {
	//un atribut per a la imatge que pot ser l'string de l'ID de Android Resources
	String image;
	String imagemap;
	ArrayList<MapHotSpot> mhs = new ArrayList<MapHotSpot>();
	
	
	/**
	 * @ToImplement
	 * @param x position x
	 * @param y position y
	 * @return boolean saying if this map point is wall or not
	 */
	public boolean isWall(int x, int y){
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
}
