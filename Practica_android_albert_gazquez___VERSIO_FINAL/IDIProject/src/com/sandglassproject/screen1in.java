package com.sandglassproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.media.MediaPlayer;

public class screen1in extends Activity implements OnCheckedChangeListener{

	private TextView textout;
	private EditText textin;
	private RadioGroup gravitygroup;
	private RadioGroup stylegroup;
	
	//Database
	
	private Integer day, month, year, minute, hour;
	private TimePicker timep;
	private DatePicker datep;
	
	private Spinner category;

	private String message, cat;
	
	private dbfunctions dbHelper;
	
	private Long mRowId;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen1in);
		
		
		
		
		textout = (TextView) findViewById(R.id.tsavedata);
		textin = (EditText) findViewById(R.id.editText1);
		gravitygroup = (RadioGroup) findViewById(R.id.rggravity);
		stylegroup = (RadioGroup) findViewById(R.id.rgstyle);
		
		//Database
		
		dbHelper = new dbfunctions(this);
		dbHelper.open();
		
		mRowId = null;
		
		mRowId = (savedInstanceState == null) ? null : (Long) savedInstanceState
				.getSerializable(dbfunctions.KEY_ROWID);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mRowId = extras.getLong(dbfunctions.KEY_ROWID);
		}
		
		category = (Spinner) findViewById(R.id.categoryspinner);
		
		
		datep = (DatePicker) findViewById(R.id.datePicker1);
		
		timep = (TimePicker) findViewById(R.id.timePicker1);
		
		//Button sounds
		final MediaPlayer buttonClick = MediaPlayer.create(screen1in.this, R.raw.click);
				
		//Setting up button references

		Button bcheck = (Button) findViewById(R.id.bcheck);
		Button baccept = (Button) findViewById(R.id.baccept);
		
		//Setting up button listeners
		
		gravitygroup.setOnCheckedChangeListener(this);
		
		stylegroup.setOnCheckedChangeListener(this);
		
		reditdata();
		
		baccept.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
			// TODO Auto-generated method stub
				
				
				buttonClick.start();
				Toast savedchanges = Toast.makeText(screen1in.this, "Changes saved", Toast.LENGTH_SHORT);
				savedchanges.show();
				setResult(RESULT_OK);
				finish();
			}
		});

		
		
		
		
		bcheck.setOnClickListener(new View.OnClickListener() {
					
		public void onClick(View v) {
		// TODO Auto-generated method stub
			buttonClick.start();
			textout.setText(textin.getText());
		}
	});
				

}
	
	private void reditdata() {
		if (mRowId != null) {
			Cursor todo = dbHelper.fetchCalendar(mRowId);
			startManagingCursor(todo);
			
			textin.setText(todo.getString(todo.getColumnIndexOrThrow(dbfunctions.KEY_MESSAGE)));
		}
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
		outState.putSerializable(dbfunctions.KEY_ROWID, mRowId);
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveState();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		reditdata();
	}
	
	private void saveState() {
		year = datep.getYear();
		month = datep.getMonth() + 1;
		day = datep.getDayOfMonth();
		minute = timep.getCurrentMinute();
		hour = timep.getCurrentHour();
		message = textin.getText().toString();
		Long cataux = category.getSelectedItemId();
		
		cat = cataux.toString();
		
		if(cat.equals("0")) cat = "Urgent";
   	 	else cat = "Reminder";

		
		if (mRowId == null) {
			long id = dbHelper.createCalendar(day , month , year , minute , hour , message, cat);
			if (id > 0) {
				mRowId = id;
			}
		} else {
			dbHelper.updateCalendar(mRowId, day , month , year , minute , hour , message, cat);
		}
		
		
	}


public void onCheckedChanged(RadioGroup group, int checkedId) {
	// TODO Auto-generated method stub
	switch(checkedId) {
	
	case R.id.rleft:
		textout.setGravity(Gravity.LEFT);
		break;
	case R.id.rcenter:
		textout.setGravity(Gravity.CENTER);
		break;
	case R.id.rright:
		textout.setGravity(Gravity.RIGHT);
		break;
	case R.id.rnormal:
		textout.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL),Typeface.NORMAL);
		break;
	case R.id.ritalic:
		textout.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC),Typeface.ITALIC);
		break;
	case R.id.rbold:
		textout.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD),Typeface.BOLD);
		break;
}
	}
	
}
