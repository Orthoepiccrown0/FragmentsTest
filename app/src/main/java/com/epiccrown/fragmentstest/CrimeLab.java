package com.epiccrown.fragmentstest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import CrimeCursorWrapper.CrimeCursosWrapper;

/**
 * Created by Epiccrown on 04.03.2018.
 */

public class CrimeLab {

    public static Crime currentCrime;

    private static CrimeLab sCrimeLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public static  CrimeLab get(Context context){
        if(sCrimeLab==null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context){
        //crimes = new ArrayList<>();
        mContext = context;
        mDatabase = new CrimeDbHelper(mContext).getWritableDatabase();
    }

    public ArrayList<Crime> getCrimes() {
        ArrayList<Crime> crimes = new ArrayList<>();
        CrimeCursosWrapper cursor = queryCrimes(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID uuid){
        CrimeCursosWrapper cursor = queryCrimes(
                CrimeDbSchema.CrimeTable.Cols.UUID+" = ?",
                new String[] {uuid.toString()}
        );

        try{
            if(cursor.getCount()==0)return null;

            cursor.moveToFirst();
            return  cursor.getCrime();
        }catch (Exception ex){ex.printStackTrace();}
        return null;
    }

    private ContentValues getContentValues(Crime crime){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CrimeDbSchema.CrimeTable.Cols.UUID,crime.getUuid().toString());
        contentValues.put(CrimeDbSchema.CrimeTable.Cols.Title,crime.getmTitle());
        contentValues.put(CrimeDbSchema.CrimeTable.Cols.Description,crime.getmDescription());
        contentValues.put(CrimeDbSchema.CrimeTable.Cols.Date,crime.getDate().getTime());
        contentValues.put(CrimeDbSchema.CrimeTable.Cols.Solved,crime.ismSolved() ? 1 : 0);

        return contentValues;
    }

    private CrimeCursosWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeDbSchema.CrimeTable.NAME,
                null, // Columns - null выбирает все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );

        return new CrimeCursosWrapper(cursor);
    }


    public void addCrime(Crime c){
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeDbSchema.CrimeTable.NAME,null, values);
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getUuid().toString();
        ContentValues values = getContentValues(crime);
        mDatabase.update(CrimeDbSchema.CrimeTable.NAME, values,
                CrimeDbSchema.CrimeTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }





//    HashMap hm = new HashMap();
//    // Get a set of the entries
//    Set set = hm.entrySet();
//
//    // Get an iterator
//    Iterator i = set.iterator();
//
//    // Display elements
//      while(i.hasNext()) {
//        Map.Entry me = (Map.Entry)i.next();
//        System.out.print(me.getKey() + ": ");
//        System.out.println(me.getValue());
//    }
//      System.out.println();
//
//    // Deposit 1000 into Zara's account
//    double balance = ((Double)hm.get("Zara")).doubleValue();
//      hm.put("Zara", new Double(balance + 1000));
//      System.out.println("Zara's new balance: " + hm.get("Zara"));
}
