package avideogame.present;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import avideogame.domain.DomainController;

public class SceneActivity extends Activity {
	DomainController dc;
	View view;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dc = DomainController.instance(getResources());
        
        //Remove window title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //int scene_index = this.getIntent().getIntExtra("SceneIndex", -1);
        
        view = new SceneView(this);
        setContentView(view);
    }
    
    
}
