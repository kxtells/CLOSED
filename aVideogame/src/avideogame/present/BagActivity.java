package avideogame.present;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import avideogame.domain.DomainController;

public class BagActivity extends Activity {
	//DomainController dc;
	View view;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //dc = DomainController.instance(getResources());
        
        view = new BagView(this);
        setContentView(view);
    }
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			DomainController.player.dropObject(0);
		}
		
		//Invalidates the whole view forcing a redraw
		view.invalidate();
		return super.onTouchEvent(event);
	}
}
