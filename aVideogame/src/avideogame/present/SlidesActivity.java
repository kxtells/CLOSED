package avideogame.present;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import avideogame.utils.Utilities;

public class SlidesActivity extends Activity {
	AnimationDrawable slideanimation;
	View v;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slides);
        
        ImageView mainimg = (ImageView) findViewById(R.id.slide);
        mainimg.setBackgroundResource(R.drawable.animslideh1s1);
        slideanimation = (AnimationDrawable) mainimg.getBackground();
    }
    
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if(hasFocus){
			Utilities.playSound(R.raw.tecles, getBaseContext());
			this.slideanimation.start();
		}
		super.onWindowFocusChanged(hasFocus);
	}
}
