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
import avideogame.domain.DomainController;
import avideogame.domain.MapHotSpot;
import avideogame.utils.Constants;
import avideogame.utils.GameControls;
import avideogame.utils.Utilities;

/**
 * Activity to show the Map and control the movement
 * @author Jordi Castells
 *
 */
public class MapActivity extends Activity {
	private MapView view;
	private GameControls gc;
	private DomainController dc;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dc = DomainController.instance(getResources());
        
        //int type = this.getIntent().getIntExtra("type", -1);
        /*if(type==0 && dc.showHistory()){
			Intent sceneIntent = new Intent(getBaseContext(), SlidesActivity.class);
			sceneIntent.putExtra("history", R.drawable.animslideh1s1);
			startActivity(sceneIntent);
        }*/
        
        view = new MapView(this); 
        setContentView(view);
    }

    
    /**
     * When the window recieves the focus hasFocus = true,
     * so we can check view size
     */
	public void onWindowFocusChanged(boolean hasFocus) {
		if(hasFocus){
	        gc = new GameControls(view.getWidth(),view.getHeight());
	        view.setGameControls(gc);
		}
	}
	
	/**
	 * Captures the touch event and process it:
	 *  - Touched info square button -> Open Scene
	 *  - Touched screen, move user
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float tx = event.getX();
		float ty = event.getY();
		
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				
				//Only active if player is over a hotspot, change to specific scene
				if(Utilities.touchedInfoButton((int)tx, (int)ty)){
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
				else{ //movement
					doMovement(gc.movementHzTouched((int)tx, (int)ty));
				}
				break;
			case MotionEvent.ACTION_MOVE:
					doMovement(gc.movementHzTouched((int)tx, (int)ty));
				break;
			default:
				break;
		}
	
		
		return super.onTouchEvent(event);
	}

	/**
	 * Create the movement, depending on which part of the screen is touched
	 * @param mov
	 */
	public void doMovement(int mov){
		int newx = (int) dc.getPlayer().getX();
		int newy = (int) dc.getPlayer().getY();
		
		switch(mov){
		case GameControls.DOWNBUTTON:
			newy += dc.getPLAYER_SINGLE_MOVE();
			break;
		case GameControls.UPBUTTON:
			newy -= dc.getPLAYER_SINGLE_MOVE();
			break;
		case GameControls.LEFTBUTTON:
			newx -= dc.getPLAYER_SINGLE_MOVE();
			break;
		case GameControls.RIGHTBUTTON:
			newx += dc.getPLAYER_SINGLE_MOVE();
			break;
		case GameControls.UPRIGHTBUTTON:
			newx += dc.getPLAYER_SINGLE_MOVE()/2;
			newy -= dc.getPLAYER_SINGLE_MOVE()/2;
			break;
		case GameControls.UPLEFTBUTTON:
			newx -= dc.getPLAYER_SINGLE_MOVE()/2;
			newy -= dc.getPLAYER_SINGLE_MOVE()/2;
			break;
		case GameControls.DOWNLEFTBUTTON:
			newx -= dc.getPLAYER_SINGLE_MOVE()/2;
			newy += dc.getPLAYER_SINGLE_MOVE()/2;
			break;
		case GameControls.DOWNRIGHTBUTTON:
			newx += dc.getPLAYER_SINGLE_MOVE()/2;
			newy += dc.getPLAYER_SINGLE_MOVE()/2;
			break;
		}
		
		if(!dc.getMap().collides(newx, newy, dc.getPlayer().getRadius())){
			dc.getPlayer().setX(newx);
			dc.getPlayer().setY(newy);
		}
		view.invalidate();
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

	/**
	 * Key Handling
	 * 
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		int movement = dc.getPLAYER_SINGLE_MOVE();
		int radius = dc.getPlayer().getRadius();
		double px = dc.getPlayer().getX();
		double py = dc.getPlayer().getY();
		
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
		
		if(!dc.getMap().collides((int)px, (int)py, radius)){
			dc.getPlayer().setX(px);
			dc.getPlayer().setY(py);
		}
		view.invalidate();
		return super.onKeyDown(keyCode, event);
	}


	@Override
	protected void onRestart() {
		if(dc.isGameover()) this.finish();
		super.onRestart();
	}


	@Override
	protected void onResume() {
		if(dc.isGameover()) this.finish();
		super.onResume();
	}	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
}