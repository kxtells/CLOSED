package avideogame.present;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

public class SlidesActivity extends Activity {
	private GridView view;
	protected int current_action;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        current_action = Constants.OBJ_INFO;
        
        setContentView(R.layout.baglayout);
        view = (GridView) findViewById(R.id.gridview);
        view.setBackgroundResource(R.drawable.bagactbackground);
        view.setAdapter(new ImageAdapter(this));
        
        
        
    }

    /**
     * The basic menu creation function
     */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, Constants.OBJ_INFO, 0, getString(R.string.menu_info)).setIcon(R.drawable.magnifier_menu);
		menu.add(0, Constants.OBJ_INTE, 0, getString(R.string.menu_touch)).setIcon(R.drawable.grab);
		menu.add(0, Constants.OBJ_COMB, 0, getString(R.string.menu_comb)).setIcon(R.drawable.combine);
		menu.add(0, Constants.OBJ_DROP,  0, getString(R.string.menu_drop)).setIcon(R.drawable.trash);
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
		                	Utilities.drawText("No Implementat", mContext);
	                		break;
	                	case Constants.OBJ_DROP:
		                	Utilities.drawText("No Implementat", mContext);
	                		break;
	                	case Constants.OBJ_INFO:
	                		String text = DomainController.getPlayer().getBagObjectById(bag_items_id.get(position)).getInfo();
		                	Utilities.drawText(text, mContext);
	                		break;
	                	case Constants.OBJ_INTE: //Interactuar amb objecte recollit
	                		int objectid = bag_items_id.get(position);
	                		Utilities.drawText(DomainController.getObjectById(objectid).getInteracttext(), getBaseContext());
	                		//si no combina amb res
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

	                		break;
	                	}
	                }

	              });

	        imageView.setImageResource(bag_items_img_id.get(position));
	        return imageView;

		}
		
		
	
	}
}
