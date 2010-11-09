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
	private static Player player;
	
	
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
		Scene s = map.getMhs().get(i).s;
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
		c.setImage(R.drawable.cofeecup);
		c.setName("Tassa cafè");
		c.setInfo("Una tassa de cafè de vidre normal i corrent");
		c.setId(0);
		CollectableObject c2 = new CollectableObject();
		c2.setImage(R.drawable.lighter);
		c2.setName("Encenedor");
		c2.setInfo("Un encenedor força bonic eternament encès, deu ser màgic");
		c2.setId(1);
		
		//Define all player data
		setPlayer(new Player(0,0));
		getPlayer().addObject(c);
		getPlayer().addObject(c2);

		getPlayer().setCurrent_action(0);
		
		//Define Scenes
		 //Kitchen Scene 1
		Scene s1 = new Scene();
		ArrayList<Bitmap> images = new ArrayList<Bitmap>();
		images.add(BitmapFactory.decodeResource(resources, R.drawable.cuina));
		s1.setImages(images);
		SceneHotSpot shs1 = new SceneHotSpot();
		shs1.setX(286);
		shs1.setY(15);
		shs1.setWidth(193);
		shs1.setHeight(80);
		shs1.setInfo("Sembla ser un armari");
		shs1.setGrabtext("T'asseguro que està buit, ho he vist");
		
		SceneHotSpot shs2 = new SceneHotSpot();
		shs2.setX(298);
		shs2.setY(121);
		shs2.setWidth(50);
		shs2.setHeight(70);
		shs2.setInfo("Una aixeta Podrida");
		shs2.setGrabtext("No ho puc arrencar pas!");
		
		ArrayList<SceneHotSpot> arshs1 = new ArrayList<SceneHotSpot>();
		arshs1.add(shs1);arshs1.add(shs2);
		s1.setHotspots(arshs1);
		
		
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
		 armhs1.add(mhs1);
		 map.setMhs(armhs1);
		 
	}

	public static void setPlayer(Player player) {
		DomainController.player = player;
	}

	public static Player getPlayer() {
		return player;
	}
	
	
	
}
