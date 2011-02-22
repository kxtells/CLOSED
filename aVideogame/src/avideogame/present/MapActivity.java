package avideogame.present;



import java.util.Timer;
import java.util.TimerTask;

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
import android.view.WindowManager.LayoutParams;
import avideogame.domain.DomainController;
import avideogame.domain.MapHotSpot;
import avideogame.utils.Constants;
import avideogame.utils.GameControls;
import avideogame.utils.Utilities;

/**
 * Activity to show the Map and control the movement
 * @author Jordi Castells
 *
 *@TODO Periodically invalidate the view
 */
public class MapActivity extends Activity implements Runnable{
	private MapView view;
	private GameControls gc;
	private DomainController dc;
	private Timer viewupdatetimer;
	
	//Movement Thread variables
	Thread player_moving;
	boolean stop_thread = false;
	boolean touching = false;
	float tx = 0;
	float ty = 0;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dc = DomainController.instance(getResources());
        
        view = new MapView(this); 
        setContentView(view);
        player_moving = new Thread(this);
        //player_moving.start();
        
        viewupdatetimer = new Timer();
        viewupdatetimer.schedule(new TimerTask() {
			@Override
			public void run() {
				TimerMethod();
			}
		}, 0, 100);
        
    }


	/**
     * When the window recieves the focus hasFocus = true,
     * so we can check view size
     */
	public void onWindowFocusChanged(boolean hasFocus) {
		if(hasFocus){
	        gc = new GameControls(view.getWidth(),view.getHeight());
	        view.setGameControls(gc);
	        if(!player_moving.isAlive()) player_moving.start();
		}
	}
	
	
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
						this.stopAllThreads();
						
						// Get instance of Vibrator from current Context
						Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
						v.vibrate(100);
						
						Intent sceneIntent = new Intent(getBaseContext(), SceneActivity.class);
						sceneIntent.putExtra("SceneIndex", mh.getScene().getId()); //the main drawable id is the scene id
						startActivity(sceneIntent);
					}
				}
				else{ //movement
					this.touching = true;
					this.tx = event.getX();
					this.ty = event.getY();
				}
				break;
			case MotionEvent.ACTION_MOVE:
					this.tx = event.getX();
					this.ty = event.getY();
				break;
			case MotionEvent.ACTION_UP:
					this.touching = false;
				break;
			default:
				break;
		}
	
		//notify changes
		synchronized (this) {notify();}
		
		return super.onTouchEvent(event);
	}
	
	/**
	 * Captures the touch event and process it:
	 *  - Touched info square button -> Open Scene
	 *  - Touched screen, move user
	 */
	public boolean onTouchEventOLD(MotionEvent event) {
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


	/**
	 * Start everything on return
	 */
	private void startAllThreads(){
		//this.notifyAll();
	}
	
	/**
	 * Clean everything before leaving
	 */
	private void stopAllThreads(){
		//cancel the update thread
		this.touching = false;
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		if(dc.isGameover()) this.finish();
		startAllThreads();
		
	}


	@Override
	protected void onResume() {
		if(dc.isGameover()) this.finish();
		this.stop_thread = false;
		
		
		//TODO: get Thread state and if it is not running start it
		//player_moving.start();
		
		//startAllThreads();
		super.onResume();
	}	
	
	@Override
	protected void onPause() {
		this.stop_thread = true;
		stopAllThreads();
		super.onPause();
	}


	@Override
	protected void onStart() {
		super.onStart();
	}


	@Override
	protected void onStop() {
		this.stop_thread = true;
		super.onStop();
	}


	/**
	 * Player Movement
	 */
	public void run() {
		while(!stop_thread){
			synchronized (this) {
				if(!this.touching){
					try {
						wait();
					} catch (InterruptedException e) {
						Log.d("THREAD","WaitException");
						e.printStackTrace();
					}
				}
				double px = dc.getPlayer().getX();
				double py = dc.getPlayer().getY();
				int mtx = view.getTranslateX();
				
				double dx = (this.tx + mtx - px)/1000;
				double dy = (this.ty - py)/1000;
				
				Log.d("THREAD","mtx:"+ mtx);
				Log.d("THREAD","y:"+this.ty);

				
				dc.getPlayer().setX(px+dx);
				dc.getPlayer().setY(py+dy);

			}
		}
	}

	
	/**
	 * Timer
	 */
	private void TimerMethod()
	{
		this.runOnUiThread(Timer_Tick);
	}

	private Runnable Timer_Tick = new Runnable() {
		public void run() {
			view.invalidate();
		}
	};

	
}