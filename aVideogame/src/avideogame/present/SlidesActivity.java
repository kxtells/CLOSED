package avideogame.present;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import avideogame.domain.DomainController;
import avideogame.utils.SlidePack;

public class SlidesActivity extends Activity {
	AnimationDrawable slideanimation;
	TextView maintv;
	ImageView mainimg;
	View v;
	int sceneid;
	int slideindex = 0;
	int textindex = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slides);
        
        mainimg = (ImageView) findViewById(R.id.slide);
        maintv = (TextView)findViewById(R.id.slidetext);
        
        mainimg.setBackgroundResource(R.drawable.animslideh1s1);
        slideanimation = (AnimationDrawable) mainimg.getBackground();
        
        SlidePack sp = DomainController.getSlideString(getResources(), R.drawable.animslideh1s1, slideindex, textindex);
        maintv.setBackgroundColor(sp.background);
        maintv.setTextColor(sp.textcolor);
        maintv.setText(sp.text);
    }
    
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			textindex++;
			SlidePack sp = DomainController.getSlideString(getResources(), R.drawable.animslideh1s1, slideindex, textindex);
	        if(sp!=null){
				maintv.setBackgroundColor(sp.background);
		        maintv.setTextColor(sp.textcolor);
		        maintv.setText(sp.text);
		        maintv.invalidate();
	        }
	        else{
	        	textindex = 0;
	        	slideindex++;
	        	sp = DomainController.getSlideString(getResources(), R.drawable.animslideh1s1, slideindex, textindex);
	        	if(sp==null){
	        		//finish();
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
