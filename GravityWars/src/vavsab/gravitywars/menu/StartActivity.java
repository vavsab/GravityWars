package vavsab.gravitywars.menu;


import vavsab.gravitywars.R;
import vavsab.gravitywars.game.MainActivity;
import vavsab.gravitywars.game.maps.LevelManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.badlogic.gdx.utils.Array;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}
	
	public void StartButton_Click(View v) {
			Intent intent = new Intent(this, MapsActivity.class);
			startActivity(intent);
	    }
	
	public void SettingsButton_Click(View v) {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
		
	}
	
	public void QuitButton_Click(View v) {
			finish();
		}
	

}
