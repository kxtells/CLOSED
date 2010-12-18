package avideogame.present;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
	Resources res;
	
	public SceneView(Context context) {
		super(context);
		DomainController.instance(this.getResources());
		res = getResources();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//canvas.drawBitmap(scene.getImages().get(scene.getCurrentScene()),0,0, null);
		
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), scene.getCurrentBackgound()),0,0,null);
		drawInfoSquare(canvas);

		//drawHintSquares(canvas);
		
		super.onDraw(canvas);
	}

	/**
	 * This function draws squares where the HotSpots are for easy debugging
	 * @param canvas the canvas where the squares are drawn
	 */
	private void drawHintSquares(Canvas canvas){
		int nhs = scene.getHotspots().size();
		ArrayList<SceneHotSpot> sch = scene.getHotspots();
		paint.setColor(res.getColor(R.color.HIGHLIGHT_HINT_COLOR));
		for(int i=0;i<nhs;i++){
			canvas.drawRect((float)sch.get(i).getX(), 
							(float)sch.get(i).getY(), 
							(float)sch.get(i).getX() + (float)sch.get(i).getWidth(), 
							(float)sch.get(i).getY() + (float)sch.get(i).getHeight(), 
							paint);	
		}
	}


	/**
	 * Draws the top-left square with the current option.
	 * @param canvas
	 */
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
		
		/*If there's an error with an identifier, simply return to INFO*/
		if(draw_identifier == -1) {
			DomainController.getPlayer().setCurrent_action(Constants.MENU_INFO);
			draw_identifier = R.drawable.magnifier;
		}
		paint.setColor(res.getColor(R.color.BUTTON));
		RectF rd = new RectF(0,0,Constants.BUTTON_W_PX,Constants.BUTTON_H_PX);
		Rect rect = new Rect(0,0,Constants.BUTTON_W_PX,Constants.BUTTON_H_PX);
		canvas.drawRoundRect(rd, Constants.BUTTON_W, Constants.BUTTON_H, paint);
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), draw_identifier), null, rect, null);
	}
	
	//GETTERS and SETTERS
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
}
