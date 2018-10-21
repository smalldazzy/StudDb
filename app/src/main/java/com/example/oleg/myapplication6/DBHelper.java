package com.example.oleg.myapplication6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBHelper extends SQLiteOpenHelper {
   public static final int DATABASE_VERSION=1;
   public static final String DATABASE_NAME="studentDB";
   public static final String TABLE_STUD="students";
   public static final String KEY_ID="_id";
   public static final String KEY_SURNAME="surname";
   public static final String KEY_GROUP="groupp";
   public static final String KEY_FACULTY="faculty";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_STUD + "(" + KEY_ID
                + " integer primary key," + KEY_SURNAME + " text," + KEY_GROUP + " integer,"+ KEY_FACULTY + " text " + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + TABLE_STUD);
            onCreate(db);
    }
}
