package avideogame.present;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ImageView mainimg = (ImageView) findViewById(R.id.ImageView01);
        mainimg.setBackgroundResource(R.drawable.animmaintitle);
        
        TextView maintext = (TextView) findViewById(R.id.infotext);
        Animation textAnim = AnimationUtils.loadAnimation(this, R.anim.animinfotext);
        maintext.startAnimation(textAnim);
    }
}
