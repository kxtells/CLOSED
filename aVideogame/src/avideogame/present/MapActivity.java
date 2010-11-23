package avideogame.present;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import avideogame.domain.DomainController;
import avideogame.utils.Constants;
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
				touchedplayer = DomainController.isPlayer(event.getX(), event.getY());
				view.setMoving(false);
				view.clearPointList();
				view.stopWalk();
				break;
			case MotionEvent.ACTION_UP:
				if(touchedplayer){
					view.setMoving(true);
					view.startWalk();
				}
				break;
			case MotionEvent.ACTION_MOVE:
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

	
    
}