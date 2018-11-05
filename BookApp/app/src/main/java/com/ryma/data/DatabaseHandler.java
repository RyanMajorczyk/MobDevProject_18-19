package com.ryma.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "book.db";
    public static final String TABLE_NAME = "books";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY, TITLE TEXT, " +
                "ISBN TEXT, AUTEUR TEXT, DESCRIPTION TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(int id, String title, String ISBN, String auteur, String description ) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put("ID", id);
        contentValue.put("TITLE", title);
        contentValue.put("ISBN", ISBN);
        contentValue.put("AUTEUR", auteur);
        contentValue.put("DESCRIPTIOn", description);
        long result = db.insert(TABLE_NAME, null, contentValue);
        return result != -1;
    }
}
