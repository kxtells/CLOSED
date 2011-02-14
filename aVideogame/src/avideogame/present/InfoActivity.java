package avideogame.present;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import avideogame.utils.Utilities;

/**
 * Credit Show Activity
 * @author Jordi Castells
 *
 */
public class InfoActivity extends Activity {
	private String[] credits;
	private String[] titlecredits;
	private int current = 0;
	private int currenttitle = 0;
	TextView infotext;
	TextView titletext;
	Animation titleAnim,textAnim;
	MediaPlayer mp = new MediaPlayer();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        credits = getResources().getStringArray(R.array.CREDITS);
        titlecredits = getResources().getStringArray(R.array.TITLECREDITS);
        
        
        titletext = (TextView) findViewById(R.id.titletext);
        titletext.setText(titlecredits[currenttitle]);
        
        infotext = (TextView) findViewById(R.id.namestext);
        infotext.setText(credits[current]);
        
        titleAnim = AnimationUtils.loadAnimation(this, R.anim.animtitletext);
        titletext.startAnimation(titleAnim);
        
        textAnim = AnimationUtils.loadAnimation(this, R.anim.animinfotext);
        infotext.startAnimation(textAnim);
        
        this.mp = Utilities.playMusic(R.raw.song_info,getBaseContext());
        
    }
    
    
    
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if(hasFocus){
			//Utilities.playSound(R.raw.tecles, getBaseContext());
			//this.backanimation.start();
		}
		super.onWindowFocusChanged(hasFocus);
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			int ln = credits.length;
			current++;
			if(current >= ln-1) {
				this.finish();
				return false;
			}
			
			String s = credits[current];
			if(s.equals(getString(R.string.JUMP_SIGNAL_STRING))){
				current++;
				currenttitle++;
				titletext.startAnimation(titleAnim);
				Utilities.playSound(R.raw.arrow, getBaseContext());
				
			}
			infotext.startAnimation(textAnim);
			infotext.setText(credits[current]);
			titletext.setText(titlecredits[currenttitle]);
		}
		return true;
	}
	
	@Override
	protected void onStop() {
		mp.stop();
		super.onStop();
	}



	@Override
	protected void onDestroy() {
		mp.stop();
		super.onDestroy();
	}



	@Override
	protected void onPause() {
		mp.stop();
		super.onPause();
	}
	
	
}
