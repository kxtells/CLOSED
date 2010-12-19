package avideogame.domain;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import avideogame.present.R;
import avideogame.utils.SlidePack;

/**
 * A class responsible to hold all the information
 * 
 * @author Controller Class
 *
 */
public class DomainController {
	protected static DomainController dc;
	public static Map map;
	private static Player player;
	private static ArrayList<Scene> scenes = new ArrayList<Scene>();
	private static ArrayList<CollectableObject> objects = new ArrayList<CollectableObject>();
	private static int TOUCH_ERROR = 20;
	private static int PLAYER_SINGLE_MOVE = 4;
	private static int PLAYER_START_X = 354;
	private static int PLAYER_START_Y = 80;
	private static boolean gameover = false;
	
	
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
	 * @throws XmlPullParserException o	
	 */
	private static void InitializeDomainController(Resources resources) throws XmlPullParserException {
		Log.d("INITDATA","INITDATA");
		setPlayer(new Player(PLAYER_START_X,PLAYER_START_Y));
		getPlayer().setCurrent_action(0);
		getPlayer().setRadius(10);
		
		parseObjectsXML(resources);
		parseScenesXML(resources);
		parseMapsXML(resources);

	}

	private static void clearDomainController(){
		dc = null;
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
	
	private static void parseMapsXML(Resources resources) throws XmlPullParserException {
		XmlResourceParser xrp = resources.getXml(R.xml.maps);
		Map c = null;
		while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
			if(xrp.getEventType() == XmlResourceParser.START_TAG){
				String s = xrp.getName();
				if(s.equals("Map")){
					int img 	= xrp.getAttributeResourceValue(null, "image",-1);
					int imgwmap = xrp.getAttributeResourceValue(null, "walkability",-1);
					c = new Map();
					c.setImage(BitmapFactory.decodeResource(resources,img));
					c.setImagemap(BitmapFactory.decodeResource(resources,imgwmap));
					map = c;
				}
				else if(s.equals("hs")){
					double x = Double.parseDouble(xrp.getAttributeValue(null, "x"));
					double y = Double.parseDouble(xrp.getAttributeValue(null, "y"));
					double w = Double.parseDouble(xrp.getAttributeValue(null, "w"));
					double h = Double.parseDouble(xrp.getAttributeValue(null, "h"));
					int sceneid 	= xrp.getAttributeResourceValue(null, "sceneid",-1);
					
					MapHotSpot mhs = new MapHotSpot(getSceneById(sceneid));
					mhs.setX(x);
					mhs.setY(y);
					mhs.setWidth(w);
					mhs.setHeight(h);
					c.addHotSpot(mhs);
				}
			}
			try {
				xrp.next();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Parse the objects XML
	 * @param resources
	 * @throws XmlPullParserException
	 */
	private static void parseObjectsXML(Resources resources) throws XmlPullParserException{
		XmlResourceParser xrp = resources.getXml(R.xml.collectableobjects);
		CollectableObject c = null;
		while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
			try {
			
				if(xrp.getEventType() == XmlResourceParser.START_TAG){
			
					String s = xrp.getName();
					if(s.equals("c_object")){
						int id 			= xrp.getAttributeResourceValue(null, "id",-1);
						int combid 		= xrp.getAttributeResourceValue(null, "combines",-1);
						int createid 	= xrp.getAttributeResourceValue(null, "creates",-1);
						String sname 	= xrp.getAttributeValue(null, "name");
						String sinfo 	= xrp.getAttributeValue(null, "infotext");
						String sinter 	= xrp.getAttributeValue(null, "interacttext");
						
						
						c = new CollectableObject();
						c.setImage(id);
						c.setName(sname);
						c.setInfo(sinfo);
						c.setId(id);
						c.setCombines_with(combid);
						c.setComb_creates(createid);
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
	
	/**
	 * A function to parse scenes.xml and create corresponding classes
	 * @param resources
	 * @throws XmlPullParserException
	 */
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
						int histid 		= xrp.getAttributeResourceValue(null,"historyid",-1);
						int objhistid 		= xrp.getAttributeResourceValue(null,"usehistobj",-1);
						
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
						shs.setHistoryscene(histid);
						shs.setHistobj(getObjectById(objhistid));
						
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
	
	/**
	 * 
	 * @param scene Scene identifier
	 * @param slide Slide index
	 * @param textindex textindex to retrieve  
	 * @return SlidePack pack of data, null if there's no text
	 */
	public static SlidePack getSlideTextData(Resources resources,int scene, int slide, int text){
		XmlResourceParser xrp = resources.getXml(R.xml.slides);
		int scid = 0;
		int slideindex = 0;
		int textindex = 0;
		int slideid = 0;
		int musicid = -1;
		
		try {
			while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if(xrp.getEventType() == XmlResourceParser.START_TAG){
					String s = xrp.getName();

					if(s.equals("scene")){
						scid = xrp.getAttributeResourceValue(null,"id",-1);
						slideindex = -1;
					}
					else if(s.equals("slide")){
						slideid = xrp.getAttributeResourceValue(null,"anim",-1);
						musicid = xrp.getAttributeResourceValue(null,"bgmusic",-1);
						slideindex++;
						textindex = 0;
					}
					else if(s.equals("text")){
						if(scid == scene && slideindex == slide && textindex == text){
							String ginfo 	= xrp.getAttributeValue(null, "string");
							String backcol 	= xrp.getAttributeValue(null, "colorback");
							String textcol 	= xrp.getAttributeValue(null, "colortext");
							int sndid = xrp.getAttributeResourceValue(null,"sound",-1);
							int newmusic = -1;
							if(text==0) newmusic = musicid;
							
							//the last one is -1 because here there's no new bgmusic, when there's a petition for the slidetext0 means that you
							//need all the possible slide info
							SlidePack sp = new SlidePack(ginfo,Color.parseColor(backcol),Color.parseColor(textcol),slideid,sndid,newmusic);
							xrp.close();
							return sp;
						}
						else{
							textindex++;
						}
					}
				}
				try {
					xrp.next();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					xrp.close();
					return null;
				}
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			xrp.close();
			return null;
		}
		
		return null;
	}

	public static void setGameover(boolean gameover) {
		DomainController.gameover = gameover;
	}

	public static boolean isGameover() {
		return gameover;
	}
}

