package com.epiccrown.fragmentstest;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Epiccrown on 04.03.2018.
 */

public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private HashMap crimes;

    public static  CrimeLab get(Context context){
        if(sCrimeLab==null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context){
        crimes = new HashMap();
        for (int i = 0; i < 10000; i++) {
            UUID uuid = UUID.randomUUID();
            if(i%2==0) crimes.put(uuid,new Crime(uuid,"Crime#"+i, "Title example", false,new Date()));
            else crimes.put(uuid,new Crime(uuid,"Crime#"+i, "Title example", true,new Date()));
        }
    }

    public HashMap getCrimes() {
        return crimes;
    }

    public Crime getCrime(UUID uuid){
        return (Crime) crimes.get(uuid);
    }

    public Crime getCrimeStupidV(UUID uuid){
        Set set = crimes.entrySet();
        Iterator i = set.iterator();
        Crime crime = null;
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            crime = ((Crime)me.getValue());
            if(crime.getUuid().equals(uuid)) return crime;
        }
        return crime;
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
