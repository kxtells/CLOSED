package avideogame.present;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
	int histid;
	int slideindex = 0;
	int textindex = 0;
	MediaPlayer mp = new MediaPlayer();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slides);
        
        histid = this.getIntent().getIntExtra("history", -1);
        //no sceneid found, finish
        if(histid==-1){this.finish();}
        
        mainimg = (ImageView) findViewById(R.id.slide);
        maintv = (TextView)findViewById(R.id.slidetext);
        
        mainimg.setBackgroundResource(histid);
        slideanimation = (AnimationDrawable) mainimg.getBackground();
        
        SlidePack sp = DomainController.getSlideTextData(getResources(), histid, slideindex, textindex);
        if(sp.bgmusic!=-1) this.mp = Utilities.playMusic(sp.bgmusic, getBaseContext());
        maintv.setBackgroundColor(sp.background);
        maintv.setTextColor(sp.textcolor);
        maintv.setText(sp.text);
        
    }
    
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			textindex++;
			SlidePack sp = DomainController.getSlideTextData(getResources(), histid, slideindex, textindex);
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
	        	sp = DomainController.getSlideTextData(getResources(), histid, slideindex, textindex);
        		this.mp.stop();
	        	if(sp==null){
	        		startCreditsIfGameOver();
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
	
	/**
	 * Starts the credits Activity if game finished
	 * @NOTE Now the GameOver is checked with a constant, in the future better with XML config
	 */
	private void startCreditsIfGameOver() {
		if(DomainController.isGameover()){
			Intent creditsIntent = new Intent(getBaseContext(), InfoActivity.class);
			startActivity(creditsIntent);
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if(hasFocus){
			this.slideanimation.start();
		}
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onStop() {
		Log.d("Slides","onStop");
		mp.stop();
		this.finish();
		super.onStop();
	}
	
	
}
