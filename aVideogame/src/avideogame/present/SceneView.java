package avideogame.present;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import avideogame.domain.DomainController;
import avideogame.domain.Scene;
import avideogame.domain.SceneHotSpot;
import avideogame.utils.Constants;

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
		
		int nhs = scene.getHotspots().size();
		ArrayList<SceneHotSpot> sch = scene.getHotspots();
		
		canvas.drawBitmap(scene.getCurrentBackgound(),0,0,null);
		
		drawInfoSquare(canvas);
		
		paint.setColor(Color.parseColor("#44FF0000"));
		for(int i=0;i<nhs;i++){
			canvas.drawRect((float)sch.get(i).getX(), 
							(float)sch.get(i).getY(), 
							(float)sch.get(i).getX() + (float)sch.get(i).getWidth(), 
							(float)sch.get(i).getY() + (float)sch.get(i).getHeight(), 
							paint);	
		}
		
		super.onDraw(canvas);
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	
	private void drawInfoSquare(Canvas canvas){
		int draw_identifier = 0;
		switch(DomainController.getPlayer().getCurrent_action()){
		case Constants.MENU_INFO:
			draw_identifier = R.drawable.info;
			break;
		case Constants.MENU_GRAB:
			draw_identifier = R.drawable.grab;		
			break;
		case Constants.MENU_OBJ:
			int object_index = DomainController.getPlayer().getCurrent_object();
			draw_identifier = DomainController.getPlayer().getBag().get(object_index).getImage();		
			break;
		}
		
		paint.setColor(Color.parseColor("#440011FF"));
		RectF rd = new RectF(0,0,65,65);
		canvas.drawRoundRect(rd, 10, 10, paint);
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), draw_identifier),10,10,null);
	}
	
}
