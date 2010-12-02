package avideogame.present;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import avideogame.domain.DomainController;
import avideogame.utils.SlidePack;
import avideogame.utils.Utilities;

public class SlidesActivity extends Activity {
	AnimationDrawable slideanimation;
	TextView maintv;
	ImageView mainimg;
	View v;
	int sceneid;
	int slideindex = 0;
	int textindex = 0;
	MediaPlayer mp = new MediaPlayer();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slides);
        
        mainimg = (ImageView) findViewById(R.id.slide);
        maintv = (TextView)findViewById(R.id.slidetext);
        
        mainimg.setBackgroundResource(R.drawable.animslideh1s1);
        slideanimation = (AnimationDrawable) mainimg.getBackground();
        
        SlidePack sp = DomainController.getSlideTextData(getResources(), R.drawable.animslideh1s1, slideindex, textindex);
        if(sp.bgmusic!=-1) this.mp = Utilities.playMusic(sp.bgmusic, getBaseContext());
        maintv.setBackgroundColor(sp.background);
        maintv.setTextColor(sp.textcolor);
        maintv.setText(sp.text);
        
    }
    
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			textindex++;
			SlidePack sp = DomainController.getSlideTextData(getResources(), R.drawable.animslideh1s1, slideindex, textindex);
	        if(sp!=null){
		        if(sp.sound!=-1)Utilities.playSound(sp.sound, getBaseContext());
				maintv.setBackgroundColor(sp.background);
		        maintv.setTextColor(sp.textcolor);
		        maintv.setText(sp.text);
		        maintv.invalidate();
	        }
	        else{
	        	textindex = 0;
	        	slideindex++;
	        	sp = DomainController.getSlideTextData(getResources(), R.drawable.animslideh1s1, slideindex, textindex);
        		this.mp.stop();
	        	if(sp==null){
	        		this.finish();
	        		return true;
	        	}
	        	
	        	if(sp.bgmusic!=-1){
	        		this.mp.stop();
	        		this.mp = Utilities.playMusic(sp.bgmusic, getBaseContext());
	        	}
	        	
	            mainimg.setBackgroundResource(sp.slideanimid);
	            slideanimation = (AnimationDrawable) mainimg.getBackground();
	            slideanimation.start();
				maintv.setBackgroundColor(sp.background);
		        maintv.setTextColor(sp.textcolor);
		        maintv.setText(sp.text);
		        maintv.invalidate();
	        }
		}
		return true;
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if(hasFocus){
			this.slideanimation.start();
		}
		super.onWindowFocusChanged(hasFocus);
	}
}
