package com.sandglassproject;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class IDIProjectActivity extends Activity {
    /** Called when the activity is first created. */
	MediaPlayer splashmusic;
    @Override
    public void onCreate(Bundle sandglassInstance) {
        super.onCreate(sandglassInstance);
        setContentView(R.layout.splash);
        
       splashmusic = MediaPlayer.create(IDIProjectActivity.this, R.raw.logo1);
       splashmusic.start();
        Thread logoTimer = new Thread() {
        	public void run() {
        		try {
        			sleep(4000);
        			Intent menuIntent = new Intent("com.sandglassproject.MENU");
        			startActivity(menuIntent);
        		} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

        		finally {
        			finish();
        		}
        	}
        };
        logoTimer.start();
    }

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		splashmusic.release();
	}
    
}