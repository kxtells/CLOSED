package avideogame.domain;

import java.util.ArrayList;

public class SceneHotSpot extends HotSpot {
	private boolean isobject;
	private boolean isinfo;
	private boolean isgrab;
	//string a mostrar quan es demani informació sobre el punt
	private String  info;
	
	
	/**
	 * @ToImplement
	 * Returns a List with all the possible actions for this HotSpot
	 * @return
	 */
	public boolean getPossibleActions(){
		return false;
	}
}
