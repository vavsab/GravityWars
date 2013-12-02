package vavsab.gravitywars.game;



import vavsab.gravitywars.game.maps.LevelManager;
import vavsab.gravitywars.game.maps.RoundConfig;
import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;


public class MainActivity extends AndroidApplication {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
		config.useCompass = false;
		config.useWakelock = true;
		config.useGL20 = true;
		
		Intent intent=this.getIntent();
		int players = intent.getIntExtra("players", 2);
		String path = intent.getStringExtra("map");
		RoundConfig roundConfig = LevelManager.getRoundConfig(path); 
		roundConfig.players = players;
		initialize(new MyGame(this.getApplicationContext(), roundConfig), config);
    }
    
	 
}