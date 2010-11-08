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
	private int current_scene;
	
	
	public Scene() {
		super();
		current_scene = 0;
	}


	/**
	 * @ToImplement
	 * @param x position x
	 * @param y position y
	 * @return SceneHotSpot The nearest SceneHotSpot in a defined radius, null if no hotspot found 
	 */
	public SceneHotSpot getSceneHotSpot(double x, double y){		
		int ln = hotspots.size();
		for(int i=0;i<ln;i++){
			if(hotspots.get(i).isInside(x, y)){return hotspots.get(i);}
		}
		return null;
	}

	
	public Bitmap getCurrentBackgound(){
		return images.get(current_scene);
	}
	
	//Getters & Setters
	public void setImages(ArrayList<Bitmap> images) {
		this.images = images;
	}

	public ArrayList<SceneHotSpot> getHotspots() {
		return hotspots;
	}

	public void setHotspots(ArrayList<SceneHotSpot> hotspots) {
		this.hotspots = hotspots;
	}

}
