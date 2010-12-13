package avideogame.utils;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;
import avideogame.domain.DomainController;

public class Utilities {
	
	public static void drawText(String text,Context context){
		if(text.equals("") || text.equals(" ")) return;
		Log.d("Toast",""+text);
		Toast.makeText(context, text, text.length() * 20).show();
	}
	
	public static void playSound(int resourceid,Context context){
		MediaPlayer mp = MediaPlayer.create(context, resourceid);
		mp.start();
	}
	
	public static MediaPlayer playMusic(int resourceid,Context context){
		MediaPlayer mp = MediaPlayer.create(context, resourceid);
		mp.setLooping(true);
		mp.start();
		return mp;
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
	public static int ScreenXtoMapX(int scx, int screenWidth){
		int px = (int) DomainController.getPlayer().getX();
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
	public static int MapXtoScreenX(int mpx, int screenWidth){
		int px = (int) DomainController.getPlayer().getX();
		int tx = (int)px-screenWidth/2;
		
		if(tx+screenWidth>=DomainController.getMap().getMapWidth()){
			tx = DomainController.getMap().getMapWidth() - screenWidth;
		}
		else if(tx<=0){
			tx = 0;
		}		
		
		return mpx - tx;	
	}
	
	/**
	 * Generate a Point arrayList representing a straight line
	 * 
	 * @param sx Source x
	 * @param sy Source y
	 * @param dx Destination x
	 * @param dy Destination y
	 * @return
	 */
	public static ArrayList<Point> generateLinePath(int sx, int sy, int dx, int dy){
		float distance = (int) Math.sqrt(Math.pow((dy-sy), 2) + Math.pow((dx-sx), 2));
		float diffx = dx-sx;
		float diffy = dy-sy;
		float stepx = diffx/distance;
		float stepy = diffy/distance;
		float px = sx;
		float py = sy;
		float i;
		ArrayList<Point> alp = new ArrayList<Point>();
		
		for(i=0;i<distance;i++){
			px += stepx;
			py += stepy;
			Point p = new Point((int)px,(int)py);
			alp.add(p);
		}
		
		return alp;
		
	}
	
	/**
	 * Returns true if any of the specific hot zones have been touched
	 * @param tx  touched point x
	 * @param ty  touched point y
	 * @param hzx Hot zone start x
	 * @param hzy Hot zone start y
	 * @param hzw Hot zone width
	 * @param hzh Hot zone height
	 * @return
	 */
	public static boolean touchedHotZone(int tx, int ty, int hzx, int hzy, int hzw, int hzh){
		if((tx > hzx) && 
				(tx < hzx+hzw) && 
				(ty > hzy) &&
				(ty < hzy+hzh)){
			return true;
		}
		return false;
	}
	
	
	/**
	 * Returns true if the information button has been touched
	 * @param tx
	 * @param ty
	 * @return
	 */
	public static boolean touchedInfoButton(int tx, int ty){
		if(touchedHotZone(tx,ty,Constants.BUTTON_X_PX,Constants.BUTTON_Y_PX, Constants.BUTTON_W_PX, Constants.BUTTON_H_PX)){
			return true;
		}
		return false;
	}
}
