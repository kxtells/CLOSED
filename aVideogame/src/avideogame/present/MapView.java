package avideogame.present;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import avideogame.domain.DomainController;
import android.graphics.Point;

public class MapView extends View{
	private Paint paint = new Paint();
	private ArrayList<Point> pointlst = new ArrayList<Point>();
	private boolean moving = false;
	private CountDownTimer cdt = null;

	public MapView(Context context) {
		super(context);
        setFocusable(true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(Color.RED);
		double x = DomainController.getPlayer().getX();
		double y = DomainController.getPlayer().getY();
		int tx = (int)x-getWidth()/2;
		
		if(tx+getWidth()>=DomainController.getMap().getMapWidth()){
			tx = DomainController.getMap().getMapWidth() - getWidth();
		}
		if(tx<=0){
			tx = 0;
		}
		/*Scroll map*/
		canvas.translate((float) -tx, 0);
		
		canvas.drawBitmap(DomainController.getMap().getImage(), 0, 0, null);
		
		
		int pradius = DomainController.getPlayer().getRadius();
		canvas.drawCircle((int)x, (int)y, pradius, paint);
		
		paint.setColor(Color.DKGRAY);
		paint.setStrokeWidth(10);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeJoin(Paint.Join.ROUND);
		int ln = pointlst.size();
		if(ln > 0){
			if(!moving){
				for(int i = 0;i<ln-1;i++){
					float x1 = pointlst.get(i).x;
					float y1 = pointlst.get(i).y;
					float x2 = pointlst.get(i+1).x;
					float y2 = pointlst.get(i+1).y;					
					//canvas.drawCircle(x1, y1, 5, paint);
					
					canvas.drawLine(x1, y1, x2, y2, paint);
				}
			}
		}
		
		
		
	}

	/**
	 * Start a coundownTimer that updates the
	 * player position
	 */
	public void startWalk(){
		int ln = pointlst.size();
		cdt = new CountDownTimer((ln+1)*100, 10) {
			int z = 0;
		     public void onTick(long millisUntilFinished) {
		         if (z<pointlst.size()){
			    	 int x = pointlst.get(z).x;
			         int y = pointlst.get(z).y;
			         z++;
			         int pradius = DomainController.getPlayer().getRadius();
			         
			         if(DomainController.getMap().collides(x,y,pradius)){
			        	 clearPointList();
			        	 cancel();
			         }
			         
			         DomainController.getPlayer().setX(x);
			         DomainController.getPlayer().setY(y);
			         Log.d("A","A");
			         invalidate();
		         }
		         else{
			    	 clearPointList();
		        	 cancel();
		         }
		     }

		     public void onFinish() {
		    	 clearPointList();
		     }
		  }.start();

	}
	
	public void stopWalk(){
		if(cdt!=null)cdt.cancel();
	}
	/**
	 * Adds a point to the point list
	 * @param p
	 */
	public void addPoint(Point p) {
		this.pointlst.add(p);
	}

	/**
	 * Removes all points from the pointlist
	 */
	public void clearPointList(){
		this.pointlst.clear();
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}

}
