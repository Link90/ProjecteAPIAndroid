package com.sandglassproject;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class options extends Activity {
	
	private dbfunctions db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
	        
		final MediaPlayer buttonClick = MediaPlayer.create(options.this, R.raw.click);
		
		Button bdel = (Button) findViewById(R.id.bdeltebase);
		bdel.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
			// TODO Auto-generated method stub
				buttonClick.start();
				Log.v("caca","del1");
				Toast savedchanges = Toast.makeText(options.this, "Database reset", Toast.LENGTH_SHORT);
				savedchanges.show();
				
		
				db.delAllCalendars();
				
				
				Log.v("caca","del2");
				setResult(RESULT_OK);
				finish();
			}
		});
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
}
