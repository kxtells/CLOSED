package avideogame.present;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import avideogame.domain.DomainController;
import avideogame.domain.MapHotSpot;
import avideogame.utils.Constants;
import avideogame.utils.Utilities;
import android.graphics.Point;
import android.os.CountDownTimer;


public class MapActivity extends Activity {
	private MapView view;
	private boolean touchedplayer;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new MapView(this); 
        setContentView(view);
    }

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				Log.d("MapActivity","DOWN");
				float tx = event.getX();
				float ty = event.getY();
				
				//Only active if player is over a hotspot
				if(tx < Constants.BUTTON_W_PX && ty < Constants.BUTTON_H_PX){
					MapHotSpot mh = view.getMapHotSpot();
					if(mh!=null){
						// Get instance of Vibrator from current Context
						Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
						v.vibrate(100);
						
						Intent sceneIntent = new Intent(getBaseContext(), SceneActivity.class);
						sceneIntent.putExtra("SceneIndex", mh.getScene().getId()); //the main drawable id is the scene id
						startActivity(sceneIntent);
					}
				}
				else{
					touchedplayer = DomainController.isPlayer(Utilities.ScreenXtoMapX((int)event.getX(),
																					(int)DomainController.getPlayer().getX(), 
																					view.getWidth()), event.getY());
					view.setMoving(false);
					view.clearPointList();
					view.stopWalk();
				}
				break;
			case MotionEvent.ACTION_UP:
				Log.d("MapActivity","UP");
				if(touchedplayer){
					view.setMoving(true);
					view.startWalk();
				}
				break;
			case MotionEvent.ACTION_MOVE:
				Log.d("MapActivity","MOVE");
				if(touchedplayer){
					int npoints = event.getHistorySize();
					int i;
					for(i=0;i<npoints;i++){
						Point p = new Point((int)event.getHistoricalX(i),(int)event.getHistoricalY(i));
						view.addPoint(p);
					}
					view.invalidate();
				}
				break;
			default:
				break;
		}
	
		
		return super.onTouchEvent(event);
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, Constants.MENU_BAG,  0, getString(R.string.menu_bag)).setIcon(R.drawable.briefcase);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent sceneIntent = new Intent(getBaseContext(), BagActivity.class);
		startActivity(sceneIntent);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		int movement = DomainController.getPLAYER_SINGLE_MOVE();
		int radius = DomainController.getPlayer().getRadius();
		double px = DomainController.getPlayer().getX();
		double py = DomainController.getPlayer().getY();
		
		if(keyCode == KeyEvent.KEYCODE_DPAD_UP || keyCode == KeyEvent.KEYCODE_O){
			py -= movement;
		}
		if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_Z){
			py += movement;
		}
		if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT || keyCode == KeyEvent.KEYCODE_L){
			px += movement;			
		}
		if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_A){
			px -= movement;	
		}
		
		if(!DomainController.getMap().collides((int)px, (int)py, radius)){
			DomainController.getPlayer().setX(px);
			DomainController.getPlayer().setY(py);
		}
		view.invalidate();
		return super.onKeyDown(keyCode, event);
	}

	
	
    
}