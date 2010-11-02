package avideogame.domain;

public class HotSpot {
	private int x;
	private int y;
	private int width;
	private int height;
	
	
	/**
	 * NEEDS REVISION, NOT CHECKED for ANDROID
	 * @param px position x to check
	 * @param py position y to check
	 * @return true if position is inside hotspot, false otherwise
	 */
	public boolean isInside(int px, int py){
		if(px < x+width && px > x && py < y+height && py > y) return true;
		else return false;
	}
	
	//GETTERS & SETTERS
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
