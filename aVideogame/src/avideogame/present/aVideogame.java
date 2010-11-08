package avideogame.present;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import avideogame.domain.DomainController;


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
		
		Intent sceneIntent = new Intent(getBaseContext(), BagActivity.class);
		sceneIntent.putExtra("SceneIndex", (int)0);
		startActivity(sceneIntent);
		
		return super.onTouchEvent(event);
	}

    
}