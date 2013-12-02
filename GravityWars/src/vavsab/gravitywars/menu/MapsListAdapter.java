package vavsab.gravitywars.menu;

import vavsab.gravitywars.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.badlogic.gdx.utils.Array;

public class MapsListAdapter extends ArrayAdapter<String> {
	  private final Context context;
	  private final Array<String> names;
	  private final Array<String> pathes;
	  private final Array<Bitmap> icons;

	  public MapsListAdapter(Context context, Array<String> names, Array<String> pathes, Array<Bitmap> icons, String [] arr) {
		super(context, R.layout.maps_list_item, arr);
	    this.context = context;
	    this.names = names;
	    this.pathes = pathes;
	    this.icons = icons;
	  }
	  

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.maps_list_item, parent, false);
	    TextView nameView = (TextView) rowView.findViewById(R.id.name);
	    TextView pathView = (TextView) rowView.findViewById(R.id.path);
	    ImageView imageView = (ImageView) rowView.findViewById(R.id.image);
	    nameView.setText(names.get(position));
	    pathView.setText(pathes.get(position));
	    imageView.setImageBitmap(icons.get(position));
	    return rowView;
	  }
	} 
