package avideogame.present;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import avideogame.domain.DomainController;
import avideogame.domain.MapHotSpot;
import avideogame.utils.Constants;
import avideogame.utils.GameControls;

public class MapView extends View{
	private Paint paint = new Paint();
	private MapHotSpot mhs = null;
	Resources res;
	GameControls gc = null;
	
	public MapView(Context context) {
		super(context);
        setFocusable(true);
        res = getResources();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		double x = DomainController.getPlayer().getX();
		double y = DomainController.getPlayer().getY();
		int tx = (int)x-getWidth()/2;
		mhs = DomainController.getMap().getMapHotSpot((int)x, (int)y);
		
		paint.setColor(Color.RED);
		if(tx+getWidth()>=DomainController.getMap().getMapWidth()){
			tx = DomainController.getMap().getMapWidth() - getWidth();
		}
		else if(tx<=0){
			tx = 0;
		}
		
		/*Paint scrolled map*/
		canvas.save();
		canvas.translate((float) -tx, 0);
		
		canvas.drawBitmap(DomainController.getMap().getImage(), 0, 0, null);
		
		
		int pradius = DomainController.getPlayer().getRadius();
		canvas.drawCircle((int)x, (int)y, pradius, paint);

		
		drawHintSquares(canvas);
		
		/*Restore the matrix*/
		canvas.restore();
		//drawControls(canvas);
		drawInfoSquare(canvas,mhs);
		
	}

	/**
	 * Draw every hotspot in the map
	 * @param canvas
	 */
	private void drawHintSquares(Canvas canvas) {
		ArrayList<MapHotSpot> mhs = DomainController.getMap().getMhs();
		int ln = mhs.size();
		int i;
		paint.setColor(res.getColor(R.color.HIGHLIGHT_HINT_COLOR));
		
		for(i=0;i<ln;i++){
			canvas.drawRect((float)mhs.get(i).getX(), 
					(float)mhs.get(i).getY(), 
					(float)mhs.get(i).getX() + (float)mhs.get(i).getWidth(), 
					(float)mhs.get(i).getY() + (float)mhs.get(i).getHeight(), 
					paint);	
		}
		
	}
	
	/**
	 * Draw the clickable info button
	 * @param canvas
	 */
	private void drawInfoSquare(Canvas canvas,MapHotSpot mhs2){
		paint.setColor(res.getColor(R.color.BUTTON));
		RectF rd = new RectF(Constants.BUTTON_X_PX,Constants.BUTTON_Y_PX,Constants.BUTTON_W_PX,Constants.BUTTON_H_PX);
		canvas.drawRoundRect(rd, 10, 10, paint);
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.magnifier), null, rd, null);
		if(mhs2==null){
			paint.setColor(res.getColor(R.color.BUTTON_OFF_SHADOW));
			canvas.drawRoundRect(rd, Constants.BUTTON_W, Constants.BUTTON_H, paint);
		}
	}

	/**
	 * Draw the touch controls on the screen
	 * @param canvas
	 */
	private void drawControls(Canvas canvas){
		paint.setColor(res.getColor(R.color.CONTROL_BUTTONS));
		
		canvas.drawRoundRect(gc.getLeftbutton(), 10,10, paint);		
		canvas.drawRoundRect(gc.getRightbutton(), 10,10, paint);
		canvas.drawRoundRect(gc.getUpbutton(), 10,10, paint);
		canvas.drawRoundRect(gc.getDownbutton(),10,10,paint);
		canvas.drawRoundRect(gc.getDownleftbutton(), 10,10, paint);
		canvas.drawRoundRect(gc.getDownrightbutton(), 10,10, paint);
		canvas.drawRoundRect(gc.getUpleftbutton(), 10,10, paint);
		canvas.drawRoundRect(gc.getUprightbutton(),10,10,paint);
	}
	
	
	public MapHotSpot getMapHotSpot(){
		return this.mhs;
	}
	
	public void setMapHotSpot(MapHotSpot mhsparam){
		this.mhs = mhsparam;
	}
	
	public void setGameControls(GameControls sgc){
		this.gc = sgc;
	}

}
