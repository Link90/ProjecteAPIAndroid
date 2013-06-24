package com.sandglassproject;

import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class dbfunctions {

	//Campos de la BD
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_DAY = "day";
	public static final String KEY_MONTH = "month";
	public static final String KEY_YEAR = "year";
	public static final String KEY_MINUTE = "minute";
	public static final String KEY_HOUR = "hour";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_CAT = "cat";
	private static final String DATABASE_TABLE = "Calendar";
	private Context context;
	private SQLiteDatabase database;
	private SandglassSQLiteHelper dbHelper;
	private Calendar caux;

	public dbfunctions(Context context) {
		this.context = context;
	}

	public dbfunctions open() throws SQLException {
		Log.v("caca","entra");
		dbHelper = new SandglassSQLiteHelper(context);
		Log.v("caca","meitat");
		database = dbHelper.getWritableDatabase();
		Log.v("caca","retorna");
		return this;
	}

	public void close() {
		dbHelper.close();
	}
	
	/**
	 * Crea una nueva tarea, si esto va bien retorna la
	 * rowId de la tarea, de lo contrario retorna -1
	 */
	public long createCalendar(Integer day, Integer month, Integer year,  Integer minute, Integer hour, String message, String cat) {
		ContentValues initialValues = createContentValues(day, month, year, minute, hour, message, cat);

		return database.insert(DATABASE_TABLE, null, initialValues);
	}
	
	//Actualiza la tarea
	
	

	public boolean updateCalendar(long rowId, Integer day, Integer month, Integer year,  Integer minute, Integer hour, String message, String cat) {
		ContentValues updateValues = createContentValues(day, month, year, minute, hour, message, cat);

		return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "="
				+ rowId, null) > 0;
	}
	
	//Borra la tarea
	
	public boolean deleteCalendar(long rowId) {
		return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	//Returna un Cursor que contiene todos los items
	public Cursor fetchAllCalendars() {
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_DAY, KEY_MONTH, KEY_YEAR, KEY_MINUTE, KEY_HOUR, KEY_MESSAGE, KEY_CAT }, null, null, null,
				null, null);
	}
	
	//Returna un Cursor que contiene la info del item
	public Cursor fetchCalendar(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_DAY, KEY_MONTH, KEY_YEAR, KEY_MINUTE, KEY_HOUR, KEY_MESSAGE, KEY_CAT },
				KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	public void delAllCalendars() {
		database.delete(DATABASE_TABLE, null, null);
	}
	
	public Cursor orderurgent() {
		String[] args = new String[] {"Urgent"};
		Cursor c = database.rawQuery(" SELECT * FROM Calendar WHERE cat=? ", args);
		return c;
	}
	
	public Cursor orderreminder() {
		String[] args = new String[] {"Reminder"};
		Cursor c = database.rawQuery(" SELECT * FROM Calendar WHERE cat=? ", args);
		return c;
	}
	
	public Cursor selecttoday() {
		caux = Calendar.getInstance();
		
		//Select day events
		
				
				int calday, calyear, calmonth;
				String scalday, scalyear, scalmonth;
				
				calday = caux.get(Calendar.DATE);
				
				calmonth = caux.get(Calendar.MONTH) + 1;
				
				calyear = caux.get(Calendar.YEAR);
				
				scalday = String.valueOf(calday);
				scalmonth = String.valueOf(calmonth);
				scalyear = String.valueOf(calyear);
				
			//	System.out.println("------------------" + scalday + " " + scalmonth + " " + scalyear);
		
		String[] args = new String[] {scalday,scalmonth,scalyear};
		Cursor c = database.rawQuery(" SELECT * FROM Calendar WHERE day=? and month=? and year=? ", args);
		return c;
	}

	
	
	private ContentValues createContentValues(Integer day, Integer month, Integer year,  Integer minute, Integer hour, String message, String cat) {
		ContentValues values = new ContentValues();
		values.put(KEY_DAY, day);
		values.put(KEY_MONTH, month);
		values.put(KEY_YEAR, year);
		values.put(KEY_MINUTE, minute);
		values.put(KEY_HOUR, hour);
		values.put(KEY_MESSAGE, message);
		values.put(KEY_CAT, cat);
		return values;
	}
}