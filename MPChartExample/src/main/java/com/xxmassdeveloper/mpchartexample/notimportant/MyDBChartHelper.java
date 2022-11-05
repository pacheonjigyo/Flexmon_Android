package com.xxmassdeveloper.mpchartexample.notimportant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public class MyDBChartHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "FLEXMON";

    public MyDBChartHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (time TEXT PRIMARY KEY, temparture REAL, electric REAL, energy REAL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(int state, String time, Double temparture, Double electric, Double energy) {
        SQLiteDatabase db = getWritableDatabase();

        try{
            ContentValues contentValues = new ContentValues();

            contentValues.put("time", time);

            if(temparture == null)
                contentValues.putNull("temparture");
            else
                contentValues.put("temparture", temparture);

            if(electric == null)
                contentValues.putNull("electric");
            else
                contentValues.put("electric", electric);

            if(energy == null)
                contentValues.putNull("energy");
            else
                contentValues.put("energy", energy);

            db.insertOrThrow(TABLE_NAME, null, contentValues);
        } catch (SQLException e) {
            Log.e("insert error occured. ", "1");

            switch(state)
            {
                case 0: db.execSQL("UPDATE " + TABLE_NAME + " SET temparture = " + temparture + " WHERE time ='" + time + "';"); break;
                case 1: db.execSQL("UPDATE " + TABLE_NAME + " SET electric = " + electric + " WHERE time ='" + time + "';"); break;
                case 2: db.execSQL("UPDATE " + TABLE_NAME + " SET energy = " + energy + " WHERE time ='" + time + "';"); break;

                default: break;
            }
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

    public String getResult() {

        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + "", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0)
                    + " - "
                    + cursor.getDouble(1)
                    + "/"
                    + cursor.getDouble(2)
                    + "/"
                    + cursor.getDouble(3)
                    + "\n";
        }

        return result;
    }

    public ArrayList<Entry> getResult(String type, int year, int month, int day) {

        SQLiteDatabase db = getReadableDatabase();

        ArrayList<Entry> values = new ArrayList<>();

        int yy, mm, dd;

        float now;
        String format;

        Cursor cursor = db.rawQuery("SELECT time," + type + " FROM " + TABLE_NAME + "", null);

        while (cursor.moveToNext()) {
            String[] nowArray = cursor.getString(0).split(" ");
            String[] dateArray = nowArray[0].split("/");
            String[] timeArray = nowArray[1].split(":");

            yy = Integer.parseInt(dateArray[0]);
            mm = Integer.parseInt(dateArray[1]);
            dd = Integer.parseInt(dateArray[2]);

            format = String.format("%.1f", Float.parseFloat(timeArray[1]) / 60);

            now = (float) (Float.parseFloat(timeArray[0]) + Float.parseFloat(format));

            if(!cursor.isNull(1) && year == yy && month == mm && day == dd)
                values.add(new Entry(now, (float) cursor.getDouble(1)));
        }

        return values;
    }

    public ArrayList<BarEntry> getResult(int state, int year, int month, int day, int hour, int minute) {

        SQLiteDatabase db = getReadableDatabase();

        ArrayList<BarEntry> values = new ArrayList<>();

        int yy, mm, dd, h, m;

        float now;
        String format;

        Cursor cursor = null;

        switch(state){
            case 0:{
                cursor = db.rawQuery("SELECT time, temparture FROM " + TABLE_NAME + "", null);

                break;
            }

            case 1: {
                cursor = db.rawQuery("SELECT time, electric FROM " + TABLE_NAME + "", null);

                break;
            }

            case 2: {
                cursor = db.rawQuery("SELECT time, energy FROM " + TABLE_NAME + "", null);

                break;
            }

            default: break;
        }

        while (cursor.moveToNext()) {
            String[] nowArray = cursor.getString(0).split(" ");
            String[] dateArray = nowArray[0].split("/");
            String[] timeArray = nowArray[1].split(":");

            yy = Integer.parseInt(dateArray[0]);
            mm = Integer.parseInt(dateArray[1]);
            dd = Integer.parseInt(dateArray[2]);

            h = Integer.parseInt(timeArray[0]);
            m = Integer.parseInt(timeArray[1]);

            format = String.format("%.1f", Float.parseFloat(timeArray[1]) / 60);

            now = (float) (Float.parseFloat(timeArray[0]) + Float.parseFloat(format));

            if(!cursor.isNull(1) && year == yy && month == mm && day == dd && hour == h && minute == m)
                values.add(new BarEntry(now, (float) cursor.getDouble(1)));
        }

        return values;
    }

    public ArrayList<BarEntry> getResult(int state, int year, int month, int day) {

        SQLiteDatabase db = getReadableDatabase();

        ArrayList<BarEntry> values = new ArrayList<>();

        int yy, mm, dd;

        float now;
        String format;

        Cursor cursor = null;

        switch(state){
            case 0:{
                cursor = db.rawQuery("SELECT time, temparture FROM " + TABLE_NAME + "", null);

                break;
            }

            case 1: {
                cursor = db.rawQuery("SELECT time, electric FROM " + TABLE_NAME + "", null);

                break;
            }

            case 2: {
                cursor = db.rawQuery("SELECT time, energy FROM " + TABLE_NAME + "", null);

                break;
            }

            default: break;
        }

        while (cursor.moveToNext()) {
            String[] nowArray = cursor.getString(0).split(" ");
            String[] dateArray = nowArray[0].split("/");
            String[] timeArray = nowArray[1].split(":");

            yy = Integer.parseInt(dateArray[0]);
            mm = Integer.parseInt(dateArray[1]);
            dd = Integer.parseInt(dateArray[2]);

            format = String.format("%.1f", Float.parseFloat(timeArray[1]) / 60);

            now = (float) (Float.parseFloat(timeArray[0]) + Float.parseFloat(format));

            if(!cursor.isNull(1) && year == yy && month == mm && day == dd)
                values.add(new BarEntry(now, (float) cursor.getDouble(1)));
        }

        return values;
    }

    public ArrayList<BarEntry> getResult(int state, int year, int month) {

        SQLiteDatabase db = getReadableDatabase();

        ArrayList<BarEntry> values = new ArrayList<>();

        int yy, mm;

        float now;
        String format;

        Cursor cursor = null;

        switch(state){
            case 0:{
                cursor = db.rawQuery("SELECT time, temparture FROM " + TABLE_NAME + "", null);

                break;
            }

            case 1: {
                cursor = db.rawQuery("SELECT time, electric FROM " + TABLE_NAME + "", null);

                break;
            }

            case 2: {
                cursor = db.rawQuery("SELECT time, energy FROM " + TABLE_NAME + "", null);

                break;
            }

            default: break;
        }

        while (cursor.moveToNext()) {
            String[] nowArray = cursor.getString(0).split(" ");
            String[] dateArray = nowArray[0].split("/");
            String[] timeArray = nowArray[1].split(":");

            yy = Integer.parseInt(dateArray[0]);
            mm = Integer.parseInt(dateArray[1]);

            format = String.format("%.1f", Float.parseFloat(timeArray[1]) / 60);

            now = (float) (Float.parseFloat(timeArray[0]) + Float.parseFloat(format));

            if(!cursor.isNull(1) && year == yy && month == mm)
                values.add(new BarEntry(now, (float) cursor.getDouble(1)));
        }

        return values;
    }
}