package com.example.compitipercasasqldb;

import android.provider.BaseColumns;

public class ContactsHelper implements BaseColumns {
	
	public static final String TABLE_NAME = "contacts";
	public static final String NAME = "name";
	public static final String SURNAME = "surname"; 
	public static final String NUMBER = "number"; 
	
	public static final String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + "("
												+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
												+ NAME + " TEXT NOT NULL, " 
												+ SURNAME + " TEXT NOT NULL , "
												+ NUMBER + " TEXT NOT NULL )";
	
	//public static final String WHERE_QUERY = "_ID > 50";

}
