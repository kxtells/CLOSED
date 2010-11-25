package avideogame.utils;

import android.graphics.RectF;

public class GameControls {
	public static final int UPBUTTON = 11;
	public static final int DOWNBUTTON = 12;
	public static final int LEFTBUTTON = 13;
	public static final int RIGHTBUTTON = 14;
	public static final int UPRIGHTBUTTON = 15;
	public static final int UPLEFTBUTTON = 16;
	public static final int DOWNRIGHTBUTTON = 17;
	public static final int DOWNLEFTBUTTON = 18;
	public int width;
	public int height;
	private RectF upbutton;
	private RectF downbutton;
	private RectF leftbutton;
	private RectF rightbutton;
	private RectF uprightbutton;
	private RectF upleftbutton;
	private RectF downrightbutton;
	private RectF downleftbutton;
	
	
	public GameControls(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		generateUpButton();
		generateDownButton();
		generateLeftButton();
		generateRightButton();
		generateUpRightButton();
		generateUpLeftButton();
		generateDownRightButton();
		generateDownLeftButton();
	}
	
	private void generateUpButton(){
		int x1,x2,y1,y2;
		x1 = (width/3);
		x2 = (width/3)*2;
		y1 = 0;
		y2 = (height/3);
		this.upbutton = new RectF(x1,y1,x2,y2);
	}
	private void generateDownButton(){
		int x1,x2,y1,y2;
		x1 = (width/3);
		x2 = (width/3)*2;
		y1 = (height/3)*2;
		y2 = height;
		this.downbutton = new RectF(x1,y1,x2,y2);
	}
	private void generateLeftButton(){
		int x1,x2,y1,y2;
		x1 = 0;
		x2 = (width/3);
		y1 = (height/3);
		y2 = (height/3)*2;
		this.leftbutton = new RectF(x1,y1,x2,y2);
	}
	private void generateRightButton(){
		int x1,x2,y1,y2;
		x1 = (width/3)*2;
		x2 = width;
		y1 = (height/3);
		y2 = (height/3)*2;
		this.rightbutton = new RectF(x1,y1,x2,y2);
	}
	private void generateUpRightButton(){
		int x1,x2,y1,y2;
		x1 = (width/3)*2;
		x2 = width;
		y1 = 0;
		y2 = (height/3);
		this.uprightbutton = new RectF(x1,y1,x2,y2);
	}
	private void generateUpLeftButton(){
		int x1,x2,y1,y2;
		x1 = 0;
		x2 = (width/3);
		y1 = 0;
		y2 = (height/3);
		this.upleftbutton = new RectF(x1,y1,x2,y2);
	}
	private void generateDownRightButton(){
		int x1,x2,y1,y2;
		x1 = (width/3)*2;
		x2 = width;
		y1 = (height/3)*2;
		y2 = height;
		this.downrightbutton = new RectF(x1,y1,x2,y2);
	}
	private void generateDownLeftButton(){
		int x1,x2,y1,y2;
		x1 = 0;
		x2 = (width/3);
		y1 = (height/3)*2;
		y2 = height;
		this.downleftbutton = new RectF(x1,y1,x2,y2);
	}	
	/**
	 * Returns the identifier for the movement hot zone touched, -1 if no movmenet hot zone touched
	 * @param tx touched screen x
	 * @param ty touched screen y
	 * @return
	 */
	public int movementHzTouched(int tx, int ty){
		if(Utilities.touchedHotZone(tx,ty,(int)downrightbutton.left,(int)downrightbutton.top,(int)downrightbutton.right,(int)downrightbutton.bottom)){
			return DOWNRIGHTBUTTON;
		}
		else if(Utilities.touchedHotZone(tx,ty,(int)uprightbutton.left,(int)uprightbutton.top,(int)uprightbutton.right,(int)uprightbutton.bottom)){
			return UPRIGHTBUTTON;
		}
		else if(Utilities.touchedHotZone(tx,ty,(int)upleftbutton.left,(int)upleftbutton.top,(int)upleftbutton.right,(int)upleftbutton.bottom)){
			return UPLEFTBUTTON;
		}
		else if(Utilities.touchedHotZone(tx,ty,(int)downleftbutton.left,(int)downleftbutton.top,(int)downleftbutton.right,(int)downleftbutton.bottom)){
			return DOWNLEFTBUTTON;
		}
		else if(Utilities.touchedHotZone(tx,ty,(int)upbutton.left,(int)upbutton.top,(int)upbutton.right,(int)upbutton.bottom)){
			return UPBUTTON;
		}
		else if(Utilities.touchedHotZone(tx,ty,(int)downbutton.left,(int)downbutton.top,(int)downbutton.right,(int)downbutton.bottom)){
			return DOWNBUTTON;
		}
		else if(Utilities.touchedHotZone(tx,ty,(int)leftbutton.left,(int)leftbutton.top,(int)leftbutton.right,(int)leftbutton.bottom)){
			return LEFTBUTTON;
		}
		else if(Utilities.touchedHotZone(tx,ty,(int)rightbutton.left,(int)rightbutton.top,(int)rightbutton.right,(int)rightbutton.bottom)){
			return RIGHTBUTTON;
		}
		
		return -1;	
	}

	
	//GETTERS
	public RectF getUpbutton() {
		return upbutton;
	}

	public RectF getDownbutton() {
		return downbutton;
	}

	public RectF getLeftbutton() {
		return leftbutton;
	}

	public RectF getRightbutton() {
		return rightbutton;
	}

	public RectF getUprightbutton() {
		return uprightbutton;
	}

	public RectF getUpleftbutton() {
		return upleftbutton;
	}

	public RectF getDownrightbutton() {
		return downrightbutton;
	}

	public RectF getDownleftbutton() {
		return downleftbutton;
	}
	
}
