package avideogame.present;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import avideogame.domain.DomainController;
import avideogame.domain.Scene;
import avideogame.domain.SceneHotSpot;
import avideogame.utils.Constants;

public class SceneView extends View {
	private Paint paint = new Paint();
	private Scene scene;
	
	public SceneView(Context context) {
		super(context);
		DomainController.instance(this.getResources());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//canvas.drawBitmap(scene.getImages().get(scene.getCurrentScene()),0,0, null);
		
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), scene.getCurrentBackgound()),0,0,null);
		
		drawInfoSquare(canvas);
		
		paint.setColor(Color.parseColor("#44FF0000"));

		//drawHintSquares(canvas);
		
		super.onDraw(canvas);
	}

	/**
	 * This function draws squares where the HotSpots are
	 * @param canvas the canvas where the squares are drawn
	 */
	private void drawHintSquares(Canvas canvas){
		int nhs = scene.getHotspots().size();
		ArrayList<SceneHotSpot> sch = scene.getHotspots();
		
		for(int i=0;i<nhs;i++){
			canvas.drawRect((float)sch.get(i).getX(), 
							(float)sch.get(i).getY(), 
							(float)sch.get(i).getX() + (float)sch.get(i).getWidth(), 
							(float)sch.get(i).getY() + (float)sch.get(i).getHeight(), 
							paint);	
		}
	}
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	
	private void drawInfoSquare(Canvas canvas){
		int draw_identifier = 0;
		switch(DomainController.getPlayer().getCurrent_action()){
		case Constants.MENU_INFO:
			draw_identifier = R.drawable.magnifier;
			break;
		case Constants.MENU_GRAB:
			draw_identifier = R.drawable.grab;		
			break;
		case Constants.MENU_OBJ:
			draw_identifier = DomainController.getPlayer().getCurrentObjectDrawResource();
			break;
		}
		
		if(draw_identifier == -1) draw_identifier = R.drawable.error;
		paint.setColor(Color.parseColor("#770011FF"));
		RectF rd = new RectF(0,0,65,65);
		Rect rect = new Rect(0,0,64,64);
		canvas.drawRoundRect(rd, 10, 10, paint);
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), draw_identifier), null, rect, null);
	}
	
}
