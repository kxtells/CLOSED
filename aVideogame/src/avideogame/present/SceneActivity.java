package avideogame.present;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import avideogame.domain.CollectableObject;
import avideogame.domain.DomainController;
import avideogame.domain.Scene;
import avideogame.domain.SceneHotSpot;
import avideogame.utils.Constants;
import avideogame.utils.Utilities;

public class SceneActivity extends Activity {
	private DomainController dc;
	private SceneView view;
	private Scene sc;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dc = DomainController.instance(getResources());
        
        int scene_index = this.getIntent().getIntExtra("SceneIndex", -1);
        view = new SceneView(this);
        sc = dc.getSceneById(scene_index);
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
			Log.d("a","a");
			if(shs!=null){
				String text="";
				switch(DomainController.getPlayer().getCurrent_action()){
					case Constants.MENU_INFO:
						text = shs.getInfo();
						break;
					case Constants.MENU_GRAB:
						text = shs.getGrabtext();
						Utilities.drawText(text,getBaseContext());
						if(shs.getS()!=null){
							int id = shs.getS().getId();
							Intent sceneIntent = new Intent(getBaseContext(), SceneActivity.class);
							sceneIntent.putExtra("SceneIndex", id);
							startActivity(sceneIntent);
						}
						if(shs.getObject()!=null){
							text = shs.getGrabtext();
							Utilities.drawText(text,getBaseContext()); //escriure text
							sc.skipSceneImage(); //passa a seguent imatge (imatge sense objecte)
							DomainController.getPlayer().addObject(shs.getObject()); //afegeix objecte a jugador
							sc.dropHotSpot(shs); //treu el hotspot
							view.invalidate(); //invalida la vista per repintar-la
						}
						
						break;
					case Constants.MENU_OBJ:
						text = "No puc usar això aquí";
						break;
				}
				Utilities.drawText(text,getBaseContext());
				
			}
			else{
				Utilities.drawText("x:"+x+" y:"+y,getBaseContext());
			}
		}
		return true;
	}



    /**
     * The basic menu creation function
     */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, Constants.MENU_INFO, 0, "info").setIcon(R.drawable.info);
		menu.add(0, Constants.MENU_GRAB, 0, "Interactuar").setIcon(R.drawable.grab);
		SubMenu s = menu.addSubMenu(0, Constants.MENU_OBJ, 0,"UsarObjecte").setIcon(R.drawable.usar);
		menu.add(0, Constants.MENU_BAG,  0, "Veure Objectes").setIcon(R.drawable.briefcase);
		
		ArrayList<CollectableObject> objects = DomainController.getPlayer().getBag();
		for(int i=0;i<objects.size();i++){
			s.add(1, objects.get(i).getId(), i, objects.get(i).getName());
		}
		
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
		menu.removeItem(Constants.MENU_OBJ);
		
		SubMenu s = menu.addSubMenu(0, Constants.MENU_OBJ, 0,"UsarObjecte").setIcon(R.drawable.usar);
		ArrayList<CollectableObject> objects = DomainController.getPlayer().getBag();
		for(int i=0;i<objects.size();i++){
			s.add(1, objects.get(i).getId(), i, objects.get(i).getName());
		}
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
		Log.d("item:",""+itemid);
		if(groupid==0){ //es tracta d'una acció bàsica (mirar,agafar,veure objectes)
			switch(itemid){
			case Constants.MENU_INFO:
				DomainController.getPlayer().setCurrent_action(Constants.MENU_INFO);
				break;
			case Constants.MENU_GRAB:
				DomainController.getPlayer().setCurrent_action(Constants.MENU_GRAB);
				break;
			case Constants.MENU_OBJ:
				DomainController.getPlayer().setCurrent_action(Constants.MENU_OBJ);
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
    
}
