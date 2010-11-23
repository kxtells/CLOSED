package avideogame.present;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import avideogame.domain.DomainController;
import avideogame.domain.Scene;
import avideogame.domain.SceneHotSpot;
import avideogame.utils.Constants;
import avideogame.utils.Utilities;

public class SceneActivity extends Activity {
	private SceneView view;
	private Scene sc;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        int scene_index = this.getIntent().getIntExtra("SceneIndex", -1);
        view = new SceneView(this);
        sc = DomainController.getSceneById(scene_index);
        view.setScene(sc);
        DomainController.getPlayer().setCurrent_action(Constants.MENU_INFO);
        setContentView(view);
        
    }
	
    @Override
	public boolean onTouchEvent(MotionEvent event) {
		double x = event.getX();
		double y = event.getY();
    	if(event.getAction() == MotionEvent.ACTION_DOWN){
			SceneHotSpot shs = sc.getSceneHotSpot(x, y);
			if(shs!=null){
				switch(DomainController.getPlayer().getCurrent_action()){
					case Constants.MENU_INFO:
						Utilities.drawText(shs.getInfo(),getBaseContext());
						break;
					case Constants.MENU_GRAB:
						menuGrabAction(shs);
						break;
					case Constants.MENU_OBJ:
						boolean done = menuUseAction(shs,DomainController.getPlayer().getCurrent_object());
						if(!done)Utilities.drawText(getString(R.string.no_hs_object_text),getBaseContext());
						break;
				}				
			}
			else{
				Utilities.drawText("x:"+x+" y:"+y,getBaseContext());
			}
		}
		return true;
	}
    
    /**
     * Action to do when a HotSpot is clicked and the action is a specific object
     * @param shs SceneHotSpot touched
     * @param object Object selected
     */
    public boolean menuUseAction(SceneHotSpot shs, int objectid){
    	if(shs.getUseobj() != null && shs.getUseobj().getId() == objectid){
    		if(sc.skipSceneImage()){
    			if(sc.isFinalImage()){
    				if(sc.getSound_final()!= -1) Utilities.playSound(sc.getSound_final(), getBaseContext());
    				applyCustomChanges(shs);
    			}
    			else{
    				Utilities.playSound(shs.getUsesoundres(), getBaseContext());
    			}
				view.invalidate();
    		}
    		return true;
    	}
    	return false;
    }
    
    private void applyCustomChanges(SceneHotSpot shs) {
		switch(sc.getId()){
		case R.drawable.schabporta:
			sc.dropHotSpot(shs);
		case R.drawable.habfosca:
			sc.dropHotSpot(shs);
		default:
		}
	}

	/**
     * Action to do when a HotSpot is clicked and the action is Grab/Interact
     * @param shs
     */
    public void menuGrabAction(SceneHotSpot shs){
    	Utilities.drawText(shs.getGrabtext(),getBaseContext());
		if(shs.getScene()!=null){ //el hotspot té una escena per activar
			int id = shs.getScene().getId();
			Intent sceneIntent = new Intent(getBaseContext(), SceneActivity.class);
			sceneIntent.putExtra("SceneIndex", id);
			startActivity(sceneIntent);
		}
		if(shs.getObject()!=null){ //el hot spot té un objecte per agafar
			sc.skipSceneImage(); //passa a seguent imatge (imatge sense objecte)
			DomainController.getPlayer().addObject(shs.getObject()); //afegeix objecte a jugador
			sc.dropHotSpot(shs); //treu el hotspot
			Utilities.playSound(R.raw.ohhh,getBaseContext());
			view.invalidate(); //invalida la vista per repintar-la
		}
		if(shs.getSound() != -1){
			Utilities.playSound(shs.getSound(), getBaseContext());
		}
    }



    /**
     * The basic menu creation function
     */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, Constants.MENU_INFO, 0, getString(R.string.menu_info)).setIcon(R.drawable.magnifier_menu);
		menu.add(0, Constants.MENU_GRAB, 0, getString(R.string.menu_touch)).setIcon(R.drawable.grab);
		menu.add(0, Constants.MENU_BAG,  0, getString(R.string.menu_bag)).setIcon(R.drawable.briefcase);
		SubMenu s = menu.addSubMenu(1, Constants.MENU_OBJ, 1, getString(R.string.menu_use)).setIcon(R.drawable.usar);

		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * Called everytime the menu appears on screen.
	 * 
	 * Needed to avoid problems when returning from BagView and maybe
	 * some objects have changed or have been dropped
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		//menu.removeItem(Constants.MENU_OBJ);
		menu.removeItem(Constants.MENU_OBJ);
		int ln = DomainController.getPlayer().getBag().size();
		if(ln>0){
			SubMenu s = menu.addSubMenu(0,Constants.MENU_OBJ,0,getString(R.string.menu_use)).setIcon(R.drawable.usar);
			
			for(int i=0;i<ln;i++){
				int id = DomainController.getPlayer().getBag().get(i).getId();
				String name = DomainController.getPlayer().getBag().get(i).getName();
				s.add(1,id,0,name);
			}
		}
		else{
			menu.add(0, Constants.MENU_OBJ, 0, "Usar Objecte").setIcon(R.drawable.usar).setEnabled(false);
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("MENU","itemid: "+item.getItemId()+"|groupid: "+item.getGroupId());
		switchPlayerAction(item.getItemId(),item.getGroupId());
		view.invalidate(); //Repaint screen
		return true;
	}
	
	/**
	 * Switches the player action to the corresponding value.
	 * Except for MENU_BAG, then it simply opens bag Activity
	 * @param itemid
	 */
	private void switchPlayerAction(int itemid,int groupid){
		if(groupid==0){ //es tracta d'una acció bàsica (mirar,agafar,veure objectes)
			switch(itemid){
			case Constants.MENU_INFO:
				DomainController.getPlayer().setCurrent_action(Constants.MENU_INFO);
				break;
			case Constants.MENU_GRAB:
				DomainController.getPlayer().setCurrent_action(Constants.MENU_GRAB);
				break;
			case Constants.MENU_OBJ:
				break;
			case Constants.MENU_BAG: //Anar a la vista de Bag
				Intent sceneIntent = new Intent(getBaseContext(), BagActivity.class);
				startActivity(sceneIntent);
				break;
			}
		}
		else if(groupid == 1){//es tracta d'un submenu
			DomainController.getPlayer().setCurrent_action(Constants.MENU_OBJ); //object use action
			DomainController.getPlayer().setCurrent_object(itemid); //object to use
		}
	}

	/**
	 * Used for playing a sound when leaving the scene
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(sc.getSound_exit() != -1){
				Utilities.playSound(sc.getSound_exit(), getBaseContext());
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	
    
}
