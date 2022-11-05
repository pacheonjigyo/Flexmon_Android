package com.xxmassdeveloper.mpchartexample.notimportant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

public class MyDBCommunityHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "FLEXMON";

    public MyDBCommunityHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, author TEXT, time TEXT, comment BLOB, notice INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String title, String description, String author, String time, String comment, int notice) {
        SQLiteDatabase db = getWritableDatabase();

        try{
            ContentValues contentValues = new ContentValues();

            contentValues.put("title", title);
            contentValues.put("description", description);
            contentValues.put("author", author);
            contentValues.put("time", time);
            contentValues.put("comment", comment);
            contentValues.put("notice", notice);

            db.insertOrThrow(TABLE_NAME, null, contentValues);
        } catch (SQLException e) {
            //TO-DO:
        }

        db.close();
    }

    public void update(int state, int id, String title, String description, int notice) {
        SQLiteDatabase db = getWritableDatabase();

        switch(state)
        {
            case 1: db.execSQL("UPDATE " + TABLE_NAME + " SET title=" + title + " WHERE id='" + id + "';"); break;
            case 2: db.execSQL("UPDATE " + TABLE_NAME + " SET description=" + description + " WHERE id='" + id + "';"); break;
            case 3: db.execSQL("UPDATE " + TABLE_NAME + " SET notice=" + notice + " WHERE id='" + id + "';"); break;

            default: break;
        }

        db.close();
    }

    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE id='" + id + "';");
        db.close();
    }

    public Cursor getResult() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + ";", null);

        return cursor;
    }

    public Cursor getContent(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT title, description, author, time, comment, notice FROM " + TABLE_NAME + " WHERE id = " + id + ";", null);

        return cursor;
    }
}