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
	private static ArrayList<Scene> scenes = new ArrayList<Scene>();
	
	
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

	public Scene getSceneById(int sceneid){
		int ln = scenes.size();
		for(int i=0;i<ln;i++){
			if(scenes.get(i).getId() == sceneid){
				return scenes.get(i);
			}
		}
		return null; 
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
		c.setImage(R.drawable.hammer);
		c.setName("Martell");
		c.setInfo("Un Martell vell però ben conservat");
		c.setId(0);
		CollectableObject c2 = new CollectableObject();
		c2.setImage(R.drawable.zippo);
		c2.setName("Encenedor");
		c2.setInfo("Un encenedor força bonic d'aquells que fan tanta pudor");
		c2.setId(1);
		
		//Define all player data
		setPlayer(new Player(0,0));
		//getPlayer().addObject(c);
		//getPlayer().addObject(c2);

		getPlayer().setCurrent_action(0);
		
		//Define Scenes
		  //Kitchen Scene apples
		Scene appl = new Scene();scenes.add(appl);
		ArrayList<Bitmap> images_appl = new ArrayList<Bitmap>();
		images_appl.add(BitmapFactory.decodeResource(resources, R.drawable.cuinapomes1));
		images_appl.add(BitmapFactory.decodeResource(resources, R.drawable.cuinapomes2));
		appl.setImages(images_appl);
		appl.setId(1);
		
		SceneHotSpot applhs1 = new SceneHotSpot();
		applhs1.setX(190);
		applhs1.setY(84);
		applhs1.setWidth(110);
		applhs1.setHeight(160);
		applhs1.setInfo("Un pot ple de pomes verdes");
		applhs1.setGrabtext("Ara mateix no tinc gana, si em servís per algo ja sé on és");
		appl.addHotSpot(applhs1);
		SceneHotSpot applhs2 = new SceneHotSpot();
		applhs2.setX(160);
		applhs2.setY(180);
		applhs2.setWidth(30);
		applhs2.setHeight(20);
		applhs2.setInfo("Un encenedor");
		applhs2.setObject(c2);
		applhs2.setGrabtext("Això ha de ser per mi");
		

		appl.addHotSpot(applhs2);
		
		 //Kitchen Scene General
		Scene s1 = new Scene();scenes.add(s1);
		ArrayList<Bitmap> images = new ArrayList<Bitmap>();
		images.add(BitmapFactory.decodeResource(resources, R.drawable.cuina));
		s1.setImages(images);
		s1.setId(0);
		SceneHotSpot shs1 = new SceneHotSpot();
		s1.addHotSpot(shs1);
		shs1.setX(286);
		shs1.setY(15);
		shs1.setWidth(193);
		shs1.setHeight(80);
		shs1.setInfo("Sembla ser un armari");
		shs1.setGrabtext("T'asseguro que està buit, ho he vist");
		
		SceneHotSpot shs2 = new SceneHotSpot();
		s1.addHotSpot(shs2);
		shs2.setX(298);
		shs2.setY(121);
		shs2.setWidth(50);
		shs2.setHeight(70);
		shs2.setInfo("Una aixeta Podrida");
		shs2.setGrabtext("No ho puc arrencar pas!");
		
		SceneHotSpot shs3 = new SceneHotSpot();
		s1.addHotSpot(shs3);
		shs3.setX(200);
		shs3.setY(121);
		shs3.setWidth(20);
		shs3.setHeight(30);
		shs3.setInfo("Un pot amb pomes");
		shs3.setGrabtext("A veure que hi trobem");
		shs3.setS(appl);
		
		
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
