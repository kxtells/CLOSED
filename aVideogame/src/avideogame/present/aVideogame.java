package avideogame.present;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import avideogame.domain.DomainController;
import avideogame.utils.Utilities;


public class aVideogame extends Activity {
	private DomainController dc;
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        dc = DomainController.instance(getResources());
        setContentView(new MapView(this));
    }

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.d("OnTouchEvent","ZZ");
		
		/*Intent sceneIntent = new Intent(getBaseContext(), SceneActivity.class);
		sceneIntent.putExtra("SceneIndex", R.drawable.scestanteriafront); //the main drawable id is the scene id
		startActivity(sceneIntent);*/
		
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0,R.drawable.sccuina,0,"Cuina");
		menu.add(0,R.drawable.scestanteriafront,0,"Lleixes");
		menu.add(0,R.drawable.schabllit,0,"Llit");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		Intent sceneIntent = new Intent(getBaseContext(), SceneActivity.class);
		sceneIntent.putExtra("SceneIndex", item.getItemId()); //the main drawable id is the scene id
		startActivity(sceneIntent);
		return true;
	}

	
    
}