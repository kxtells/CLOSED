package avideogame.present;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import avideogame.domain.DomainController;

public class MapView extends View{
	private Paint paint = new Paint();
	private DomainController dc;

	public MapView(Context context) {
		super(context);
        setFocusable(true);
        dc = DomainController.instance(this.getResources());
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		paint.setColor(Color.BLUE);
		canvas.drawRect(new Rect(0,0,10,10), paint);
		canvas.drawBitmap(dc.map.getImage(), 0, 0, null);
		
	}

}
