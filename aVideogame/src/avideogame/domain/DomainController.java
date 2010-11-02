package avideogame.domain;

import android.content.res.Resources;
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

	/**
	 * Initializes all the Domain Data
	 * @TODO initialize from a xml, or plain text data file
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
		
		//Define all Map data
		map = new Map();
		map.setImage(BitmapFactory.decodeResource(resources, R.drawable.planta1));
		
		//Define all player data
		player = new Player(0,0);
		player.addObject(c);
		player.addObject(c2);
	}
	
	
}
