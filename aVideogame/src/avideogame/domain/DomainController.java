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
	
	protected static CollectableObject getObjectById(int objectid){
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
		setPlayer(new Player(0,0));
		getPlayer().setCurrent_action(0);
		
		parseObjectsXML(resources);
		parseScenesXML(resources);
		
		//Define all Map data
		map = new Map();
		map.setImage(BitmapFactory.decodeResource(resources, R.drawable.planta1));
	}

	public static void setPlayer(Player player) {
		DomainController.player = player;
	}

	public static Player getPlayer() {
		return player;
	}
	
	
	private static void parseObjectsXML(Resources resources) throws XmlPullParserException{
		XmlResourceParser xrp = resources.getXml(R.xml.collectableobjects);
		while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
			try {
			
				if(xrp.getEventType() == XmlResourceParser.START_TAG){
			
					String s = xrp.getName();
					if(s.equals("c_object")){
						int id 			= xrp.getAttributeResourceValue(null, "id",-1);
						String sname 	= xrp.getAttributeValue(null, "name");
						String sinfo 	= xrp.getAttributeValue(null, "infotext");
						
						CollectableObject c = new CollectableObject();
						c.setImage(id);
						c.setName(sname);
						c.setInfo(sinfo);
						c.setId(id);
						objects.add(c);
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
						currentscene = new Scene();
						currentscene.setId(id);
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
	
}
