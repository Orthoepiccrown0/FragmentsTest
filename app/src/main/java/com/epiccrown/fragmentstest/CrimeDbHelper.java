package com.epiccrown.fragmentstest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Epiccrown on 17.03.2018.
 */

public class CrimeDbHelper extends SQLiteOpenHelper {

    private final static int DB_VERSION = 1;
    private final static String DB_NAME = "CrimesDataBase.db";


    public CrimeDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ CrimeDbSchema.CrimeTable.NAME+"("+
                    "_id integer primary key autoincrement, "+
                    CrimeDbSchema.CrimeTable.Cols.UUID+","+
                    CrimeDbSchema.CrimeTable.Cols.Title+","+
                    CrimeDbSchema.CrimeTable.Cols.Description+","+
                    CrimeDbSchema.CrimeTable.Cols.Date+","+
                    CrimeDbSchema.CrimeTable.Cols.Solved+")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
