/**
 * Class Scene contains info from
 * a static scene.
 */

package avideogame.domain;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class Scene {
	
	//Llista d'imatges
	private ArrayList<Bitmap> images = new ArrayList<Bitmap>();
	//
	private ArrayList<SceneHotSpot> hotspots = new ArrayList<SceneHotSpot>();
	private int currentScene;
	
	
	public Scene() {
		currentScene = 0;
	}

	/**
	 * @ToImplement
	 * @param x position x
	 * @param y position y
	 * @return SceneHotSpot The nearest SceneHotSpot in a defined radius, null if no hotspot found 
	 */
	public SceneHotSpot getNearestSceneHotSpot(int x, int y){		
		return null;
	}

	
	//Getters & Setters
	public ArrayList<Bitmap> getImages() {
		return images;
	}

	public void setImages(ArrayList<Bitmap> images) {
		this.images = images;
	}

	public ArrayList<SceneHotSpot> getHotspots() {
		return hotspots;
	}

	public void setHotspots(ArrayList<SceneHotSpot> hotspots) {
		this.hotspots = hotspots;
	}

	public int getCurrentScene() {
		return currentScene;
	}

	public void setCurrentScene(int currentScene) {
		this.currentScene = currentScene;
	}

}
