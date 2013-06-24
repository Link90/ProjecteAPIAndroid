package com.sandglassproject;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.sandglassproject.SandglassSQLiteHelper;
import android.widget.ListAdapter;


	

	
	
	public class displayscrolldata extends ListActivity implements OnCheckedChangeListener {
		
		private dbfunctions dbHelper;
		private static final int ACTIVITY_CREATE = 0;
		private static final int ACTIVITY_EDIT = 1;
		private static final int DELETE_ID = Menu.FIRST + 1;
		private Cursor cursor;
		private RadioGroup ordergroup;
		private RadioGroup todayg;
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.displayscrolldata);
	        //Log.v("caca","display111");
	        ordergroup = (RadioGroup) findViewById(R.id.rgorderby);
	        ordergroup.setOnCheckedChangeListener(this);
	        todayg = (RadioGroup) findViewById(R.id.rgorderby2);
	        todayg.setOnCheckedChangeListener(this);
	        this.getListView().setDividerHeight(2);
			dbHelper = new dbfunctions(this);
			dbHelper.open();
			recdatabase();
			registerForContextMenu(getListView());
	    }
	    
	 // Creamos el menu desde el archivo XML
	 	@Override
	 	public boolean onCreateOptionsMenu(Menu menu) {
	 		super.onCreateOptionsMenu(menu);
	 		MenuInflater inflater = getMenuInflater();
	 		inflater.inflate(R.menu.listmenu, menu);
	 		return true;
	 	}

	 	// Codificamos la accion al seleccionar el item del menu
	 	@Override
	 	public boolean onMenuItemSelected(int featureId, MenuItem item) {
	 		switch (item.getItemId()) {
	 		case R.id.insert:
	 			createnewdata();
	 			return true;
	 		}
	 		return super.onMenuItemSelected(featureId, item);
	 	}

	 	@Override
	 	public boolean onOptionsItemSelected(MenuItem item) {
	 		switch (item.getItemId()) {
	 		case R.id.insert:
	 			createnewdata();
	 			return true;
	 		case R.id.it:
	 			Toast savedchanges = Toast.makeText(displayscrolldata.this, "Database reset", Toast.LENGTH_SHORT);
				savedchanges.show();
	 			dbHelper.delAllCalendars();
	 			recdatabase();
	 			return true;
	 		}
	 		return super.onOptionsItemSelected(item);
	 	}

	 	@Override
	 	public boolean onContextItemSelected(MenuItem item) {
	 		switch (item.getItemId()) {
	 		case DELETE_ID:
	 			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
	 					.getMenuInfo();
	 			dbHelper.deleteCalendar(info.id);
	 			recdatabase();
	 			return true;
	 		}
	 		return super.onContextItemSelected(item);
	 	}

	 	private void createnewdata() {
	 		Intent i = new Intent("com.sandglassproject.SCREEN1IN");
	 		startActivityForResult(i, ACTIVITY_CREATE);
	 	}

	 	//ListView y la accion al seleccionar un item
	 	@Override
	 	protected void onListItemClick(ListView l, View v, int position, long id) {
	 		super.onListItemClick(l, v, position, id);
	 		Intent i = new Intent(this, screen1in.class);
	 		i.putExtra(dbfunctions.KEY_ROWID, id);
	 		//La actividad retorna un resultado cuando se llama 
	 		//startActivityForResult
	 		startActivityForResult(i, ACTIVITY_EDIT);
	 	}

	 	//El siguiente metodo se llama con el resultado de otra actividad
	 	// requestCode es el codigo original que se manda a la actividad
	 	// resultCode es el codigo de retorno, 0 significa que todo salió bien
	 	// intent es usado para obtener alguna información del caller
	 	@Override
	 	protected void onActivityResult(int requestCode, int resultCode,
	 			Intent intent) {
	 		super.onActivityResult(requestCode, resultCode, intent);
	 		recdatabase();

	 	}

	 	private void recdatabase() {
	 		cursor = dbHelper.fetchAllCalendars();
	 		startManagingCursor(cursor);

	 		String[] from = new String[] { dbfunctions.KEY_DAY, dbfunctions.KEY_MONTH, dbfunctions.KEY_YEAR, dbfunctions.KEY_MINUTE, dbfunctions.KEY_HOUR, dbfunctions.KEY_MESSAGE, dbfunctions.KEY_CAT };
	 		int[] to = new int[] { R.id.lab1, R.id.lab3, R.id.lab5, R.id.lab8, R.id.lab6, R.id.lab10, R.id.lab52 };
	 		//Log.v("caca","display2222");
	 		//Creamos un array adapter para desplegar cada una de las filas
	 		SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
	 				R.layout.displayrows, cursor, from, to);
	 		//Log.v("caca","display3333");
	 		setListAdapter(notes);
	 	}

	 	@Override
	 	public void onCreateContextMenu(ContextMenu menu, View v,
	 			ContextMenuInfo menuInfo) {
	 		super.onCreateContextMenu(menu, v, menuInfo);
	 		menu.add(0, DELETE_ID, 0, "Delete");
	 	}
	 	
	 	@Override
	 	protected void onDestroy() {
	 		super.onDestroy();
	 		if (dbHelper != null) {
	 			dbHelper.close();
	 		}
	 	}
	 	
	 	private void recdatabaseid() {
	 		cursor = dbHelper.orderurgent();
	 		startManagingCursor(cursor);

	 		String[] from = new String[] { dbfunctions.KEY_DAY, dbfunctions.KEY_MONTH, dbfunctions.KEY_YEAR, dbfunctions.KEY_MINUTE, dbfunctions.KEY_HOUR, dbfunctions.KEY_MESSAGE, dbfunctions.KEY_CAT };
	 		int[] to = new int[] { R.id.lab1, R.id.lab3, R.id.lab5, R.id.lab8, R.id.lab6, R.id.lab10, R.id.lab52 };

	 		//Creamos un array adapter para desplegar cada una de las filas
	 		SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
	 				R.layout.displayrows, cursor, from, to);

	 		setListAdapter(notes);
	 	}
	 	
	 	private void recdatabasecat() {
	 		cursor = dbHelper.orderreminder();
	 		startManagingCursor(cursor);

	 		String[] from = new String[] { dbfunctions.KEY_DAY, dbfunctions.KEY_MONTH, dbfunctions.KEY_YEAR, dbfunctions.KEY_MINUTE, dbfunctions.KEY_HOUR, dbfunctions.KEY_MESSAGE, dbfunctions.KEY_CAT };
	 		int[] to = new int[] { R.id.lab1, R.id.lab3, R.id.lab5, R.id.lab8, R.id.lab6, R.id.lab10, R.id.lab52 };

	 		//Creamos un array adapter para desplegar cada una de las filas
	 		SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
	 				R.layout.displayrows, cursor, from, to);

	 		setListAdapter(notes);
	 	}

	 	private void recdatabasetoday() {
	 		cursor = dbHelper.selecttoday();
	 		startManagingCursor(cursor);

	 		String[] from = new String[] { dbfunctions.KEY_DAY, dbfunctions.KEY_MONTH, dbfunctions.KEY_YEAR, dbfunctions.KEY_MINUTE, dbfunctions.KEY_HOUR, dbfunctions.KEY_MESSAGE, dbfunctions.KEY_CAT };
	 		int[] to = new int[] { R.id.lab1, R.id.lab3, R.id.lab5, R.id.lab8, R.id.lab6, R.id.lab10, R.id.lab52 };

	 		//Creamos un array adapter para desplegar cada una de las filas
	 		SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
	 				R.layout.displayrows, cursor, from, to);

	 		setListAdapter(notes);
	 	}
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			Log.v("caca","---");
			// TODO Auto-generated method stub
			switch(checkedId) {
			
			case R.id.rorderbyall:
				recdatabase();
				break;
			case R.id.rorderbyid:
				recdatabaseid();
				break;
			case R.id.rorderbycat:
				recdatabasecat();
				break;
			case R.id.rbytoday:
				recdatabasetoday();
				break;
			case R.id.rbytodayall:
				recdatabase();
				break;
			
		}
		}
	}


