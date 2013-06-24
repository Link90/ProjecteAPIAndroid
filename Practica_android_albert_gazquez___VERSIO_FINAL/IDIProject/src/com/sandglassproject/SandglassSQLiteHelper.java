package com.sandglassproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class SandglassSQLiteHelper extends SQLiteOpenHelper {
	
	private static final String NAME = "appdata";
 
	private static final int VERSION = 1;
	
    //Sentencia SQL para crear la tabla
	private static final String sqlCreate = "create table Calendar (_id integer primary key autoincrement, " + "day INTEGER not null, month INTEGER not null, year INTEGER not null, minute INTEGER not null, hour INTEGER not null, message TEXT not null, cat TEXT not null)";

 
    public SandglassSQLiteHelper(Context contexto) {
        super(contexto, NAME, null, VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
    }

        
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion,
			int newVersion) {
		Log.w(SandglassSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS Calendar");
		onCreate(db);
	}

}