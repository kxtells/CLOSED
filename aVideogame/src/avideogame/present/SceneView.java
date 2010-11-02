package avideogame.present;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import avideogame.domain.DomainController;
import avideogame.domain.Scene;

public class SceneView extends View {
	private Paint paint = new Paint();
	private DomainController dc;
	private Scene scene;
	
	public SceneView(Context context) {
		super(context);
		dc = DomainController.instance(this.getResources());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//canvas.drawBitmap(scene.getImages().get(scene.getCurrentScene()),0,0, null);
		super.onDraw(canvas);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onTrackballEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTrackballEvent(event);
	}

	
}
