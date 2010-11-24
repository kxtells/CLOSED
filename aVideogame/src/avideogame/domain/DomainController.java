package avideogame.domain;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import avideogame.present.R;

public class DomainController {
	protected static DomainController dc;
	public static Map map;
	private static Player player;
	private static ArrayList<Scene> scenes = new ArrayList<Scene>();
	private static ArrayList<CollectableObject> objects = new ArrayList<CollectableObject>();
	private static int TOUCH_ERROR = 20;
	private static int PLAYER_SINGLE_MOVE = 5;
	
	public DomainController() {
		super();
	}
	
	public static DomainController instance(Resources resources){
		if(dc == null){
			dc = new DomainController();
			try {
				InitializeDomainController(resources);
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				dc = null;
			}
		}
		return dc;
	}

	public static Scene getSceneById(int sceneid){
		int ln = scenes.size();
		for(int i=0;i<ln;i++){
			if(scenes.get(i).getId() == sceneid){
				return scenes.get(i);
			}
		}
		return null; 
	}
	
	public static CollectableObject getObjectById(int objectid){
		int ln = objects.size();
		for(int i=0;i<ln;i++){
			if(objects.get(i).getId() == objectid){
				return objects.get(i);
			}
		}
		return null;	
	}
	
	/**
	 * Initializes all the Domain Data
	 * @TODO initialize from a xml, or plain text data file for easy changing
	 * @param resources Resources given from android front-end, needed to assign bitmaps
	 * @throws XmlPullParserException 
	 */
	private static void InitializeDomainController(Resources resources) throws XmlPullParserException {
		Log.d("INITDATA","INITDATA");
		setPlayer(new Player(50,300));
		getPlayer().setCurrent_action(0);
		
		parseObjectsXML(resources);
		parseScenesXML(resources);
		
		//Define all Map data
		map = new Map();
		map.setImage(BitmapFactory.decodeResource(resources, R.drawable.planta1));
		map.setImagemap(BitmapFactory.decodeResource(resources, R.drawable.walkability));
	}

	public static void setPlayer(Player player) {
		DomainController.player = player;
	}

	public static Player getPlayer() {
		return player;
	}
	
	public static Map getMap(){
		return map;
	}
	
	
	private static void parseObjectsXML(Resources resources) throws XmlPullParserException{
		XmlResourceParser xrp = resources.getXml(R.xml.collectableobjects);
		CollectableObject c = null;
		while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
			try {
			
				if(xrp.getEventType() == XmlResourceParser.START_TAG){
			
					String s = xrp.getName();
					if(s.equals("c_object")){
						int id 			= xrp.getAttributeResourceValue(null, "id",-1);
						String sname 	= xrp.getAttributeValue(null, "name");
						String sinfo 	= xrp.getAttributeValue(null, "infotext");
						String sinter 	= xrp.getAttributeValue(null, "interacttext");
						
						
						c = new CollectableObject();
						c.setImage(id);
						c.setName(sname);
						c.setInfo(sinfo);
						c.setId(id);
						c.setInteracttext(sinter);
						
						objects.add(c);
					}
					else if(s.equals("transformsto")){
						int newobjid = xrp.getAttributeResourceValue(null, "newobj",-1);
						c.addTransformstoObjectId(newobjid);
					}
				}
				xrp.next();
			
			} catch (IOException e) {
				//if error simply log
				Log.d("xrp exception",e.getMessage());
			}
		}
		xrp.close();
	}
	
	private static void parseScenesXML(Resources resources) throws XmlPullParserException{
		XmlResourceParser xrp = resources.getXml(R.xml.scenes);
		Scene currentscene = null;
		while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
			try {
			
				if(xrp.getEventType() == XmlResourceParser.START_TAG){
					String s = xrp.getName();
					if(s.equals("scene")){
						int id   = xrp.getAttributeResourceValue(null, "id",-1);
						int sound_exit = xrp.getAttributeResourceValue(null, "sound_exit",-1);
						int sound_final = xrp.getAttributeResourceValue(null, "sound_final",-1);
						currentscene = new Scene();
						currentscene.setId(id);
						currentscene.setSound_exit(sound_exit);
						currentscene.setSound_final(sound_final);
						scenes.add(currentscene);
					}
					if(s.equals("image")){ //per a cada tag d'imatge afegir
						int imgid = xrp.getAttributeResourceValue(null, "image",-1);
						currentscene.addImageBackground(imgid);
					}
					if(s.equals("hs")){ //els hotspots de la imatge
						double x = Double.parseDouble(xrp.getAttributeValue(null, "x"));
						double y = Double.parseDouble(xrp.getAttributeValue(null, "y"));
						double w = Double.parseDouble(xrp.getAttributeValue(null, "w"));
						double h = Double.parseDouble(xrp.getAttributeValue(null, "h"));
						int hsid = Integer.parseInt(xrp.getAttributeValue(null,"id"));
						String sinfo 	= xrp.getAttributeValue(null, "infotext");
						String ginfo 	= xrp.getAttributeValue(null, "grabtext");
						int sceneid 	= xrp.getAttributeResourceValue(null, "sceneid",-1);
						int objid 		= xrp.getAttributeResourceValue(null, "objectid",-1);
						int soundid		= xrp.getAttributeResourceValue(null,"sound",-1);
						int useobjid	= xrp.getAttributeResourceValue(null,"useobject",-1);
						int usesoundid  = xrp.getAttributeResourceValue(null,"useobjectsound",-1);
						
						SceneHotSpot shs = new SceneHotSpot();
						currentscene.addHotSpot(shs);
						shs.setId(hsid);
						shs.setX(x);
						shs.setY(y);
						shs.setWidth(w);
						shs.setHeight(h);
						shs.setInfo(sinfo);
						shs.setGrabtext(ginfo);
						shs.setObject(getObjectById(objid));
						shs.setScene(getSceneById(sceneid));
						shs.setSound(soundid);
						shs.setUseobj(getObjectById(useobjid));
						shs.setUsesoundres(usesoundid);
						
					}
				}
				xrp.next();
			
			} catch (IOException e) {
				//if error simply log
				Log.d("xrp exception",e.getMessage());
			}
		}
		xrp.close();
	}
	
	/**
	 * Checks if a touched point is touching the player
	 * @param x Touched Point x
	 * @param y Touched Point y
	 * 
	 * @NOTE: This function uses a TOUCH ERROR attribute defined
	 * in DomainController.
	 * @return
	 */
	public static boolean isPlayer(double x, double y){
		double px = player.getX();
		double py = player.getY();
		
		double distance = Math.sqrt(Math.pow((y-py), 2) + Math.pow((x-px), 2));
		Log.d("DomainController","y:"+y+" x:"+x+"  px:"+px+" py:"+py);
		Log.d("DomainController","distance:"+distance);
		if(distance < TOUCH_ERROR) return true;
		else return false;
	}

	public static void setPLAYER_SINGLE_MOVE(int pLAYER_SINGLE_MOVE) {
		PLAYER_SINGLE_MOVE = pLAYER_SINGLE_MOVE;
	}

	public static int getPLAYER_SINGLE_MOVE() {
		return PLAYER_SINGLE_MOVE;
	}
	
}
