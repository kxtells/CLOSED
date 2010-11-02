package avideogame.domain;

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import avideogame.present.R;

public class DomainController {
	protected static DomainController dc;
	public static Map map;
	public static Player player;
	
	
	public DomainController() {
		super();
	}
	
	public static DomainController instance(Resources resources){
		if(dc == null){
			dc = new DomainController();
			InitializeDomainController(resources);
		}
		return dc;
	}

	public Scene getScene(int i){
		Scene s = map.mhs.get(i).s;
		int x = 3;
		int j = 4;
		int w = 4+x;
		return s; 
	}
	
	/**
	 * Initializes all the Domain Data
	 * @TODO initialize from a xml, or plain text data file for easy changing
	 * @param resources Resources given from android front-end, needed to assign bitmaps
	 */
	private static void InitializeDomainController(Resources resources) {
		Log.d("INITDATA","INITDATA");
		//Define all objects
		CollectableObject c = new CollectableObject();
		c.setImage(BitmapFactory.decodeResource(resources, R.drawable.caja));
		c.setName("Caja");
		CollectableObject c2 = new CollectableObject();
		c.setImage(BitmapFactory.decodeResource(resources, R.drawable.caja));
		c.setName("Caja2");
		
		//Define all player data
		player = new Player(0,0);
		player.addObject(c);
		player.addObject(c2);
		
		//Define Scenes
		 //Kitchen Scene 1
		Scene s1 = new Scene();
		ArrayList<Bitmap> images = new ArrayList<Bitmap>();
		images.add(BitmapFactory.decodeResource(resources, R.drawable.cuina));
		s1.setImages(images);
		SceneHotSpot shs1 = new SceneHotSpot();
		shs1.setX(0);
		shs1.setY(0);
		shs1.setWidth(10);
		shs1.setHeight(10);
		
		SceneHotSpot shs2 = new SceneHotSpot();
		shs2.setX(30);
		shs2.setY(40);
		shs2.setWidth(20);
		shs2.setHeight(10);
		
		ArrayList<SceneHotSpot> arshs1 = new ArrayList<SceneHotSpot>();
		arshs1.add(shs1);arshs1.add(shs2);
		
		
		//Define all Map data
		map = new Map();
		map.setImage(BitmapFactory.decodeResource(resources, R.drawable.planta1));
		 //HotSpots
		 MapHotSpot mhs1 = new MapHotSpot(s1);
		 mhs1.setX(0);
		 mhs1.setY(0);
		 mhs1.setWidth(10);
		 mhs1.setHeight(10);
		 
		 ArrayList<MapHotSpot> armhs1 = new ArrayList<MapHotSpot>();
		 map.setMhs(armhs1);
		 
	}
	
	
}
