package com.xxmassdeveloper.mpchartexample.notimportant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBLoginHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "FLEXMON";

    public MyDBLoginHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (id TEXT PRIMARY KEY, password TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String id, String password) {
        SQLiteDatabase db = getWritableDatabase();

        try{
            ContentValues contentValues = new ContentValues();

            contentValues.put("id", id);
            contentValues.put("password", password);

            db.insertOrThrow(TABLE_NAME, null, contentValues);
        } catch (SQLException e) {
            //TO-DO:
        }

        db.close();
    }

    public void update(int state, String time, double value) {
        SQLiteDatabase db = getWritableDatabase();

        switch(state)
        {
            case 1: db.execSQL("UPDATE " + TABLE_NAME + " SET temparture=" + value + " WHERE time='" + time + "';"); break;
            case 2: db.execSQL("UPDATE " + TABLE_NAME + " SET electric=" + value + " WHERE time='" + time + "';"); break;
            case 3: db.execSQL("UPDATE " + TABLE_NAME + " SET energy=" + value + " WHERE time='" + time + "';"); break;

            default: break;
        }

        db.close();
    }

    public void delete(String time) {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE time='" + time + "';");
        db.close();
    }

    public int loginProcess(String id, String password) {

        SQLiteDatabase db = getReadableDatabase();

        String login = null;

        Cursor cursor = db.rawQuery("SELECT password FROM " + TABLE_NAME + " WHERE id = '" + id + "';", null);

        while (cursor.moveToNext()) {
            login = cursor.getString(0);
        }

        if(login == null) {
            return -1;
        }
        else {
            if (login.equals(password))
                return 1;
            else
                return 0;
        }
    }
}