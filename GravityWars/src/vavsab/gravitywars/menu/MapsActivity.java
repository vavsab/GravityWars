package vavsab.gravitywars.menu;

import java.io.IOException;
import java.io.InputStream;

import vavsab.gravitywars.R;
import vavsab.gravitywars.game.maps.LevelManager;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.badlogic.gdx.utils.Array;

public class MapsActivity extends Activity {

	Array<String> pathes;
	Array<String> names;
	Array<String> iconsPath;
	Array<Bitmap> icons;
	Intent mapsIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		
		mapsIntent = new Intent(this, RoundOptionsActivity.class);
		LevelManager.setContext(this);
		pathes = LevelManager.getMapsPathList();
		iconsPath = LevelManager.getMapsIconsList();
		names = LevelManager.getMapsNamesList();
		icons = new Array<Bitmap>();
		for (int i = 0; i < iconsPath.size; i++) {
			icons.add(getBitmapFromAsset("maps/" + pathes.get(i) + "/" +iconsPath.get(i)));
		}
		
		
		
		
		// находим список
	    ListView lvMaps = (ListView) findViewById(R.id.mapsListView);

	    // создаем адаптер
	    ArrayAdapter<String> adapter = new MapsListAdapter(this, names, pathes, icons, arrayToNativeArray(pathes));
	    // присваиваем адаптер списку
	    lvMaps.setAdapter(adapter);	    
	    lvMaps.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				mapsIntent.putExtra("selected-map", pathes.get(position));
				startActivity(mapsIntent);
			}
	    	
		});
	}
	
	private Bitmap getBitmapFromAsset(String strName)
    {
        AssetManager assetManager = getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }
	
	private String [] arrayToNativeArray(Array<String> arr) {
		String [] nar = new String[arr.size];
		for (int i = 0; i < arr.size; i++) {
			nar[i] = arr.get(i);
		}
		return nar;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maps, menu);
		return true;
	}
	
	
	
	

}
