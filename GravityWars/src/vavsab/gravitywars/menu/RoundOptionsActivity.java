package vavsab.gravitywars.menu;

import java.io.Serializable;
import java.util.ArrayList;

import vavsab.gravitywars.R;
import vavsab.gravitywars.game.MainActivity;
import vavsab.gravitywars.game.maps.LevelManager;
import vavsab.gravitywars.game.maps.RoundConfig;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.badlogic.gdx.scenes.scene2d.ui.List;

public class RoundOptionsActivity extends Activity {

	RoundConfig cfg;
	SeekBar playersBar;
	TextView playersView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_round_options);
		Intent intent = getIntent();
		String selectedMap = intent.getStringExtra("selected-map");
		
		cfg = LevelManager.getRoundConfig(selectedMap);
		playersView = (TextView) findViewById(R.id.textViewPlayers);
		playersBar = (SeekBar) findViewById(R.id.seekBarPlayers);
		playersBar.setMax(cfg.maxPlayers - 2);
		playersBar.setProgress(0);
		playersView.setText("2");
		playersBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				playersView.setText(Integer.toString((arg1 + 2)));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.round_options, menu);
		return true;
	}
	
	public void FightButton_Click(View v) {
			cfg.players = playersBar.getProgress() + 2;
			Intent intent = new Intent(this, MainActivity.class);
			
			intent.putExtra("map", cfg.path);
			intent.putExtra("players", cfg.players);
			startActivity(intent);
	    }

}
