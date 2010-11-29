package avideogame.present;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import avideogame.domain.DomainController;
import avideogame.utils.Utilities;


public class aVideogame extends Activity {	
	AnimationDrawable backanimation;
	View v;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        DomainController.instance(getResources());
        
        ImageView mainimg = (ImageView) findViewById(R.id.ImageView01);
        mainimg.setBackgroundResource(R.drawable.animmaintitle);
        backanimation = (AnimationDrawable) mainimg.getBackground();
        
        TextView maintext = (TextView) findViewById(R.id.pressmenutext);
        Animation textAnim = AnimationUtils.loadAnimation(this, R.anim.animmaintext);
        maintext.startAnimation(textAnim);

    }

	@Override
	public boolean onTouchEvent(MotionEvent event) {	
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0,R.string.NEW_GAME,0,getString(R.string.NEW_GAME));
		menu.add(0,R.string.CONTINUE_GAME,0,getString(R.string.CONTINUE_GAME));
		menu.add(0,R.string.HELP,0,getString(R.string.HELP));
		menu.add(0,R.string.CONFIGURATION,0,getString(R.string.CONFIGURATION));
		menu.add(0,R.string.ABOUT,0,getString(R.string.ABOUT));
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent sceneIntent;
		switch(item.getItemId()){
		case R.string.NEW_GAME:
			sceneIntent = new Intent(getBaseContext(), MapActivity.class);
			startActivity(sceneIntent);
			break;
		case R.string.CONTINUE_GAME:
			Utilities.drawText("No implementat", getBaseContext());
			break;
		case R.string.HELP:
			Utilities.drawText("No implementat", getBaseContext());
			break;
		case R.string.CONFIGURATION:
			//Utilities.drawText("No implementat", getBaseContext());
			sceneIntent = new Intent(getBaseContext(), SlidesActivity.class);
			startActivity(sceneIntent);
			break;
		case R.string.ABOUT:
			sceneIntent = new Intent(getBaseContext(), InfoActivity.class);
			startActivity(sceneIntent);
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if(hasFocus){
			Utilities.playSound(R.raw.tecles, getBaseContext());
			this.backanimation.start();
		}
		super.onWindowFocusChanged(hasFocus);
	}


	
    
}