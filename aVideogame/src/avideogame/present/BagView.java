package avideogame.present;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import avideogame.domain.DomainController;

public class BagView extends View {
	private Paint paint = new Paint();
	private DomainController dc;
	
	public BagView(Context context) {
		super(context);
		dc = DomainController.instance(this.getResources());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(Color.BLUE);
		int x = DomainController.getPlayer().getBag().size();
		canvas.drawText("num_objects:"+x, 10, 10, paint);
		canvas.drawRect(new Rect(0,0,10,10), paint);
	}	

}
