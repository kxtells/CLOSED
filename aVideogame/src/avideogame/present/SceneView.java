package avideogame.present;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import avideogame.domain.DomainController;
import avideogame.domain.Scene;
import avideogame.domain.SceneHotSpot;

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

	
}
