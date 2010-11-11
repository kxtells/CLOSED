/**
 * Class Scene contains info from
 * a static scene.
 */

package avideogame.domain;

import java.util.ArrayList;

import android.util.Log;

public class Scene {
	private int id;
	//Llista d'imatges
	private ArrayList<Integer> images = new ArrayList<Integer>();
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

	public void addHotSpot(SceneHotSpot shs){
		this.hotspots.add(shs);
	}
	
	public void dropHotSpot(SceneHotSpot shs){
		this.hotspots.remove(shs);
	}
	
	/**
	 * Sets the image to view to the next image
	 */
	public void skipSceneImage(){
		this.current_scene++;
	}
	public Integer getCurrentBackgound(){
		return images.get(current_scene);
	}
	
	public void addImageBackground(int imgid){
		this.images.add(imgid);
	}
	
	//Getters & Setters
	public void setImages(ArrayList<Integer> images) {
		this.images = images;
	}

	public ArrayList<SceneHotSpot> getHotspots() {
		return hotspots;
	}

	public void setHotspots(ArrayList<SceneHotSpot> hotspots) {
		this.hotspots = hotspots;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getId() {
		return id;
	}


	public int getCurrent_scene() {
		return current_scene;
	}


	public void setCurrent_scene(int currentScene) {
		current_scene = currentScene;
	}

}
