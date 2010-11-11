package avideogame.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Utilities {
	
	public static void drawText(String text,Context context){
		Log.d("Toast",""+text);
		Toast.makeText(context, text, text.length() * 20).show();
	}
}
