package avideogame.domain;

import java.util.ArrayList;

public class CollectableObject {
	//A image for the Object
	private String image;
	//String a mostrar a la vista d'objectes, NO a l'escena
	private String info;
	//llista amb els objectes que pots combinar-ho i llista amb el resultat de tal combinació
	private ArrayList<CollectableObject> combwith = new ArrayList<CollectableObject>();
	private ArrayList<CollectableObject> transformsto = new ArrayList<CollectableObject>();
	
}
