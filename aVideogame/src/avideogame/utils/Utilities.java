package avideogame.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;
import avideogame.domain.DomainController;

public class Utilities {
	
	public static void drawText(String text,Context context){
		Log.d("Toast",""+text);
		Toast.makeText(context, text, text.length() * 20).show();
	}
	
	public static void playSound(int resourceid,Context context){
		MediaPlayer mp = MediaPlayer.create(context, resourceid);
		mp.start();
	}
	
	/**
	 * This function given a touched screen point, the actual Map position
	 * of the player and the width returns the Map Point touched.
	 * 
	 * The player x is needed because it deremines the window scroll position
	 * @param sc
	 * @param px
	 * @param screenWidth
	 * @return
	 */
	public static int ScreenXtoMapX(int scx, int px, int screenWidth){
		int tx = (int)px-screenWidth/2;
		
		if(tx+screenWidth>=DomainController.getMap().getMapWidth()){
			tx = DomainController.getMap().getMapWidth() - screenWidth;
		}
		else if(tx<=0){
			tx = 0;
		}
		
		return scx + tx;
	}
	
	
	/**
	 * NOT TESTED
	 * 
	 * @param mpx
	 * @param px
	 * @param screenWidth
	 * @return
	 */
	public static int MapXtoScreenX(int mpx, int px, int screenWidth){
		int tx = (int)px-screenWidth/2;
		
		if(tx+screenWidth>=DomainController.getMap().getMapWidth()){
			tx = DomainController.getMap().getMapWidth() - screenWidth;
		}
		else if(tx<=0){
			tx = 0;
		}		
		
		return mpx - tx;	
	}
}
