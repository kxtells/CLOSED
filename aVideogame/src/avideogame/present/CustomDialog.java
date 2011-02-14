package avideogame.present;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialog extends Dialog implements OnClickListener {
	
	public CustomDialog(Context context) {
        super(context);
        /** 'Window.FEATURE_NO_TITLE' - Used to hide the title */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /** Design the dialog in main.xml file */
        setContentView(R.layout.dialog);
       
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		dismiss();
		return true;
	}


	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Sets the text of the dialog
	 * @param text
	 */
	public void setText(String text){
		((TextView) findViewById(R.id.TextView02)).setText(text);
	}


}
