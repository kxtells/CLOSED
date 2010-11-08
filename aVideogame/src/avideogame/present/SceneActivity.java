package avideogame.present;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Toast;
import avideogame.domain.DomainController;
import avideogame.domain.Scene;
import avideogame.domain.SceneHotSpot;

public class SceneActivity extends Activity {
	DomainController dc;
	SceneView view;
	Scene sc;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dc = DomainController.instance(getResources());
        
        int scene_index = this.getIntent().getIntExtra("SceneIndex", -1);
        view = new SceneView(this);
        sc = dc.getScene(scene_index);
        view.setScene(sc);
        setContentView(view);
    }
	
    @Override
	public boolean onTouchEvent(MotionEvent event) {
		double x = event.getX();
		double y = event.getY();
    	if(event.getAction() == MotionEvent.ACTION_DOWN){
			SceneHotSpot shs = sc.getSceneHotSpot(x, y);
			if(shs!=null){
	    		Toast.makeText(getBaseContext(), shs.getInfo(), 100).show();
			}
			else{
	    		Toast.makeText(getBaseContext(), "x:"+x+" y:"+y, 100).show();
			}
		}
		return true;
	}
    
    
    
    
}
