package avideogame.domain;

public class HotSpot {
	private int id;
	private double x;
	private double y;
	private double width;
	private double height;
	protected Scene s = null;
	
	/**
	 * NEEDS REVISION, NOT CHECKED for ANDROID
	 * @param px position x to check
	 * @param py position y to check
	 * @return true if position is inside hotspot, false otherwise
	 */
	public boolean isInside(double px, double py){
		if(px < x+width && px > x && py < y+height && py > y) return true;
		else return false;
	}
	
	//GETTERS & SETTERS
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}

	public Scene getScene() {
		return s;
	}

	public void setScene(Scene s) {
		this.s = s;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
