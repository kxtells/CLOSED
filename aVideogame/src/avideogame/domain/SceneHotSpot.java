package avideogame.domain;

public class SceneHotSpot extends HotSpot {
	private boolean isobject;
	private boolean isinfo;
	private boolean isgrab;
	//string a mostrar quan es demani informació sobre el punt
	private String  info;
	private String  grabtext;
	private CollectableObject co;
	
	
	/**
	 * @ToImplement
	 * Returns a List with all the possible actions for this HotSpot
	 * @return
	 */
	public boolean getPossibleActions(){
		return false;
	}


	//getters and setters
	public boolean isIsobject() {
		return isobject;
	}


	public void setIsobject(boolean isobject) {
		this.isobject = isobject;
	}


	public boolean isIsinfo() {
		return isinfo;
	}


	public void setIsinfo(boolean isinfo) {
		this.isinfo = isinfo;
	}


	public boolean isIsgrab() {
		return isgrab;
	}


	public void setIsgrab(boolean isgrab) {
		this.isgrab = isgrab;
	}


	public String getInfo() {
		return info;
	}


	public void setInfo(String info) {
		this.info = info;
	}


	public void setGrabtext(String grabtext) {
		this.grabtext = grabtext;
	}


	public String getGrabtext() {
		return grabtext;
	}
	
	
}
