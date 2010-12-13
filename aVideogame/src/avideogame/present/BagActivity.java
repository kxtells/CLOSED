package avideogame.present;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import avideogame.domain.DomainController;
import avideogame.utils.Constants;
import avideogame.utils.Utilities;

public class BagActivity extends Activity {
	private GridView view;
	protected int current_action;
	protected int selected1_id = -1;
	protected int selected2_id = -1;
	protected ArrayList<View> selectedviews = new ArrayList<View>();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        current_action = Constants.OBJ_INFO;
        
        setContentView(R.layout.baglayout);
        view = (GridView) findViewById(R.id.gridview);
        view.setBackgroundResource(R.drawable.bagactbackground);
        view.setAdapter(new ImageAdapter(this));
        view.setBackgroundColor(Color.WHITE);
        
        
    }

    /**
     * The basic menu creation function
     */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, Constants.OBJ_INFO, 0, getString(R.string.menu_info)).setIcon(R.drawable.magnifier_menu);
		menu.add(0, Constants.OBJ_INTE, 0, getString(R.string.menu_touch)).setIcon(R.drawable.grab);
		menu.add(0, Constants.OBJ_COMB, 0, getString(R.string.menu_comb)).setIcon(R.drawable.combine);
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * Called everytime the menu appears on screen.
	 * 
	 * Needed to avoid problems when returning from BagView and maybe
	 * some objects have changed or have been dropped
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		current_action = item.getItemId();
		return true;
	}
	
	
	/**
	 * Subclass ImageAdapter to fill the
	 * grid with player's object images
	 * @author kxtells
	 *
	 */
	public class ImageAdapter extends BaseAdapter {
	    private Context mContext;
		private ArrayList<Integer> bag_items_img_id = new ArrayList<Integer>();
		private ArrayList<Integer> bag_items_id = new ArrayList<Integer>();


	    public ImageAdapter(Context c) {
	        mContext = c;
	        fillImageIds();
	    }

	    /**
	     * Fill the bag_items_img_id with all the object ids
	     */
	    private void fillImageIds(){
	    	bag_items_img_id.clear();
	    	bag_items_id.clear();
	    	for(int i = 0;i<DomainController.getPlayer().getBag().size();i++){
	    		bag_items_img_id.add(DomainController.getPlayer().getBag().get(i).getImage());
	    		bag_items_id.add(DomainController.getPlayer().getBag().get(i).getId());
	    	}
	    }
	    
		@Override
		public int getCount() {
			return this.bag_items_img_id.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		/**
		 * Controls the object combination in the bagView
		 * 
		 * two class attributes selected1_id and selected2_id 
		 * store the selected items (-1 when no selected item)
		 * 
		 * This function recieves the selected_id and checks if there's
		 * another object selected. If that's the case tries the combination.
		 * 
		 * @param selected_id
		 */
		private void objectCombination(int selected_id){
			if(selected1_id == -1) selected1_id = selected_id;
			else selected2_id = selected_id;
			
			if(selected2_id != -1){
				unHighlightAll();
    			//Combinació correcte
    			if(DomainController.getObjectById(selected1_id).getCombines_with() == selected2_id ||
    					DomainController.getObjectById(selected2_id).getCombines_with() == selected1_id){
    				Log.d("COMB","combines");
    				int newobjidA = DomainController.getObjectById(selected1_id).getComb_creates();
    				int newobjidB = DomainController.getObjectById(selected2_id).getComb_creates();
    				int newobjid = 0;
    				
    				//comprovar per quin costat està feta l'assignació de nou objecte
    				if(newobjidA == -1) newobjid = newobjidB;
    				else newobjid = newobjidA;
    				
    				DomainController.getPlayer().dropObject(DomainController.getObjectById(selected2_id));
    				DomainController.getPlayer().dropObject(DomainController.getObjectById(selected1_id));
    				DomainController.getPlayer().addObject(DomainController.getObjectById(newobjid));
    			
        			//regenerar llista d'objectes a mostrar
        			fillImageIds();
    				notifyDataSetChanged();
    			}
    			else{
    				String n1 = DomainController.getObjectById(selected1_id).getName();
    				String n2 = DomainController.getObjectById(selected2_id).getName();
    				Utilities.drawText(getString(R.string.NOCOMB)+" "+n1+" i "+n2, getBaseContext());
    			}
    			//posar la combinació a 0
    			selected1_id = -1;
    			selected2_id = -1;
			}
			
		}
		
		/**
		 * Interaction action with a specific object.
		 * 
		 * There's 2 possible results:
		 *  - Only a message appears
		 *  - A message + the object transforms in another object
		 * @param objectid
		 */
		private void objectInteract(int objectid){
			Utilities.drawText(DomainController.getObjectById(objectid).getInteracttext(), getBaseContext());
    		if(DomainController.getObjectById(objectid).getCombines_with() == -1){
    			//si hi ha objectes en la seva llista de transformacions
    			int size = DomainController.getObjectById(objectid).getTransforms_to().size(); 
    			if(size>0){
    				//afegeix tots els objectes a la bossa del jugador
    				for(int i=0;i<size;i++){
    					int id = DomainController.getObjectById(objectid).getTransforms_to().get(i);
    					DomainController.getPlayer().addObject(DomainController.getObjectById(id));
    				}
    				//esborra l'objecte original de la bossa del jugador
    				DomainController.getPlayer().dropObject(DomainController.getObjectById(objectid));
    				fillImageIds();
    				notifyDataSetChanged();
    			}
    		}			
		}
		
		/**
		 * unhighlight all selected items
		 */
		private void unHighlightAll(){
			int i;
			int ln = selectedviews.size();
			for(i=0;i<ln;i++){
				selectedviews.get(i).setBackgroundColor(Color.TRANSPARENT);
			}
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup arg2) {
	        ImageView imageView;
	            imageView = new ImageView(mContext);
	            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            imageView.setPadding(8, 8, 8, 8);
	            
	            imageView.setOnClickListener(new View.OnClickListener() {

	            	/*Que es fa quan es clica un objecte*/
	                @Override
	                public void onClick(View view) {
	                	//s'haurà de fer un switch segons acció
	                	switch(current_action){
	                	case Constants.OBJ_COMB:
	                		selectedviews.add(view);
	                		view.setBackgroundColor(getResources().getColor(R.color.SELECTED_OBJECT));
	                		objectCombination(bag_items_id.get(position));
	                		break;
	                	case Constants.OBJ_DROP:
		                	Utilities.drawText("No Implementat", mContext);
	                		break;
	                	case Constants.OBJ_INFO:
	                		String text = DomainController.getPlayer().getBagObjectById(bag_items_id.get(position)).getInfo();
		                	Utilities.drawText(text, mContext);
	                		break;
	                	case Constants.OBJ_INTE: //Interactuar amb objecte recollit
	                		objectInteract(bag_items_id.get(position));
	                		break;
	                	}
	                }

	              });

	        imageView.setImageResource(bag_items_img_id.get(position));
	        return imageView;

		}
		
		
	
	}
}
