package com.example.a123.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;
    public DBHelper(Context context) {
        super(context, "database.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WEIGHT_TABLE = "CREATE TABLE weight ( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date TEXT, weight INTEGER, status TEXT, user_id TEXT)";

        db.execSQL(CREATE_WEIGHT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_WEIGHT_TABLE = "DROP TABLE IF EXISTS sleep";

        db.execSQL(DROP_WEIGHT_TABLE);

        onCreate(db);
    }

    public ArrayList<Weight> getWeightList(String user_id) {
        ArrayList<Weight> weights = new ArrayList<Weight>();

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT date, weight, status FROM weight WHERE user_id = \"" + user_id +
                "\"",null);


        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {
            Weight weight = new Weight(cursor.getString(0),cursor.getInt(1),
                    cursor.getString(2));
            weights.add(weight);

            cursor.moveToNext();
        }

        sqLiteDatabase.close();

        return weights;
    }

    public void addWeight(Weight weight,String user_id) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(Friend.Column.ID, friend.getId());
        values.put("date", weight.getDate());
        values.put("weight", weight.getWeight());
        values.put("status", weight.getStatus());
        values.put("user_id", user_id);

        sqLiteDatabase.insert("weight", null, values);

        sqLiteDatabase.close();
    }

    public void updateSleep(Weight weight,String user_id) {

        sqLiteDatabase  = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("date", weight.getDate());
        values.put("weight", weight.getWeight());
        values.put("status", weight.getStatus());
        values.put("user_id", user_id);

        sqLiteDatabase.update("weight", values, "user_id=\""+user_id+"\" AND weight=\""+weight.getWeight()+"\" AND date=\""+weight.getDate()+"\"", null);

        sqLiteDatabase.close();
    }
}
