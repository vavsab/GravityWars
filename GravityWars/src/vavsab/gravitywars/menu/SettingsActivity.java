package vavsab.gravitywars.menu;



import vavsab.gravitywars.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ToggleButton;

public class SettingsActivity extends Activity {

	SharedPreferences.Editor editor;
	boolean vibrator;
	boolean sounds;
	int volume;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		int mode = Activity.MODE_PRIVATE;
		  SharedPreferences mySharedPreferences = getSharedPreferences("gravity-wars", mode); 
		  editor = mySharedPreferences.edit();
		  vibrator = mySharedPreferences.getBoolean("vibrator", false);
		  sounds = mySharedPreferences.getBoolean("sounds", true);
		  volume = mySharedPreferences.getInt("volume", 100);
		  volume %= 101;
		  
		ToggleButton tb = (ToggleButton) findViewById(R.id.toggleButtonVibration);
		tb.setChecked(vibrator);
		tb = (ToggleButton) findViewById(R.id.toggleButtonSound);
		tb.setChecked(sounds);
		SeekBar sb = (SeekBar) findViewById(R.id.seekBarVolume);
		sb.setProgress(volume);
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				volume = seekBar.getProgress();
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
		});
		updateSeekBarVisibility();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	public void VibrationButton_Click(View v) {
			vibrator = ((ToggleButton) v).isChecked();
	    }
	
	public void SoundButton_Click(View v) {
			sounds = ((ToggleButton) v).isChecked();
			updateSeekBarVisibility();
	    }
	
	private void updateSeekBarVisibility() {
		findViewById(R.id.seekBarVolume).setEnabled(sounds);
	}
	
	public void QuitButton_Click(View v) {
			onBackPressed();
			finish();
	    }
	
	@Override
	public void onBackPressed() {
		   editor.putBoolean("vibrator", vibrator);
		   editor.putBoolean("sounds", sounds);
		   editor.putInt("volume", volume);
		   editor.commit();
		   
		super.onBackPressed();
	}

}
