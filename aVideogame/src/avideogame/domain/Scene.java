/**
 * Class Scene contains info from
 * a static scene.
 */

package avideogame.domain;

import java.util.ArrayList;

public class Scene {
	
	//Llista d'imatges
	ArrayList<String> images = new ArrayList<String>();
	//
	ArrayList<SceneHotSpot> hotspots = new ArrayList<SceneHotSpot>();
	
	/**
	 * @ToImplement
	 * @param x position x
	 * @param y position y
	 * @return SceneHotSpot The nearest SceneHotSpot in a defined radius, null if no hotspot found 
	 */
	public SceneHotSpot getNearestSceneHotSpot(int x, int y){		
		return null;
	}
}
