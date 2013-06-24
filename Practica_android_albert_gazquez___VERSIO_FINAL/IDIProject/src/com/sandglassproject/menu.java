package com.sandglassproject;

import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.media.MediaPlayer;
import android.widget.TextView;

public class menu extends Activity {
	
	
	TextView viewtoday;
	
	//Calendar maincal;
	
	private dbfunctions dbHelper;
	
	private SQLiteDatabase mydatabase;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//Log.v("caca","vava111111");

		dbHelper = new dbfunctions(this);
		//Log.v("caca","vava1");
		dbHelper.open();
		//Log.v("caca","vava1");
		
		
		viewtoday = (TextView) findViewById(R.id.mtoday);  
		//Button sounds
//		final MediaPlayer buttonClick = MediaPlayer.create(menu.this, R.raw.click);
		
		//Setting up button references
		
		/*maincal = Calendar.getInstance();
		
		//Select day events
		
		//String[] fields = new String[] {"code", "day" , "month" , "year" , "minute" , "hour" , "message"};
		
		int calday, calyear, calmonth;
		
		calday = maincal.get(Calendar.DATE);
		
		calmonth = maincal.get(Calendar.MONTH) + 1;
		
		calyear = maincal.get(Calendar.MONTH) + 1;
		System.out.println("------------------" + calday + " " + calmonth + " " + calyear);
		
		

		

		//viewtoday.setText(String.valueOf(calyear));
		Log.v("caca","vava12");
		Cursor c = dbHelper.fetchAllCalendars();
		Log.v("caca","vava2");

		int i = 0;
		if (c.moveToFirst()) {
		     //Recorremos el cursor hasta que no haya más registros
			Log.v("caca","vava");
		     do {

		    	 String auxminute = String.valueOf(c.getInt(4));
		    	 String auxhour = String.valueOf(c.getInt(5));
		    	 String auxcategory;
		    	 if(c.getString(7).equals("0")) auxcategory = "Urgent";
		    	 else auxcategory = "Reminder";
		    	 viewtoday.setText("Hour: " + auxhour + ":" + auxminute + "\n" + "Message: " + c.getString(6) + "\n" + "Priority: " + auxcategory + "\n");
		     } while(c.moveToNext());

		}*/
		//Log.v("caca","vava3");
	}
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater menuinf = getMenuInflater();
		menuinf.inflate(R.menu.main_menu, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()) {
			case R.id.opscreen1in:
				startActivity(new Intent("com.sandglassproject.SCREEN1IN"));
				return true;
			case R.id.displaycalendar:
				startActivity(new Intent("com.sandglassproject.DISPLAYSCROLLDATA"));
				return true;
			case R.id.mop:
				startActivity(new Intent("com.sandglassproject.OPTIONS"));
				return true;
		}
		return true;
	}
	

}
