package com.thelearnedfriends.thirdparty;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventsData extends SQLiteOpenHelper 
{

	private static final String DATABASE_NAME = "easymobileAPP.db";
	private static final int DATABASE_VERSION = 2;
	
	public EventsData(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String sql = "CREATE TABLE IF NOT EXISTS PDDB (ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT, PASSWORD TEXT, AUTH TEXT, REGISTERED TEXT, SOCIALINFO TEXT, DEVICEINFO TEXT)";
		db.execSQL(sql);
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{	
		String sql = "DROP TABLE IF EXISTS PDDB";
		db.execSQL(sql);
		onCreate(db);
	}

}
