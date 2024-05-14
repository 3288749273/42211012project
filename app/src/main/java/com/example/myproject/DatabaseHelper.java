package com.example.myproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myproject.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "register";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_CONFIRM = "confirm";
    private static final String COLUMN_NUMBER = "number";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_ID + " TEXT PRIMARY KEY UNIQUE NOT NULL, " +
                COLUMN_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_CONFIRM + " TEXT NOT NULL, " +
                COLUMN_NUMBER + " TEXT NOT NULL)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addUser(String name, String id, String password, String confirm, String number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_ID, id);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_CONFIRM, confirm);
        contentValues.put(COLUMN_NUMBER, number);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1; // Returns true if data is inserted successfully, otherwise false
    }

    public boolean checkUser(String id, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_ID + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {id, password};
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

}
