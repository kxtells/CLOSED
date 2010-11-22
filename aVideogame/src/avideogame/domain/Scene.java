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
	private int sound_exit = -1;
	private int sound_final = -1;
	
	
	public Scene() {
		super();
		current_scene = 0;
	}

	public boolean isFinalImage(){
		return (this.current_scene == this.images.size()-1);
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
	public boolean skipSceneImage(){
		if(this.current_scene + 1 < this.images.size()){
			this.current_scene++;
			return true;
		}
		else{
			return false;
		}
		
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


	public void setSound_exit(int sound_exit) {
		this.sound_exit = sound_exit;
	}


	public int getSound_exit() {
		return sound_exit;
	}

	public void setSound_final(int sound_final) {
		this.sound_final = sound_final;
	}

	public int getSound_final() {
		return sound_final;
	}

}
