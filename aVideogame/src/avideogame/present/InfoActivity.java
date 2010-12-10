package avideogame.present;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import avideogame.utils.Utilities;

public class InfoActivity extends Activity {
	private String[] credits;
	private String[] titlecredits;
	private int current = 0;
	private int currenttitle = 0;
	TextView infotext;
	TextView titletext;
	Animation titleAnim,textAnim;
	
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
			if(current++ > ln-1) current = 0;
			
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
}
