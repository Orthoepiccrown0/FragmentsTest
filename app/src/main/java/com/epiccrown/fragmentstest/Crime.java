package com.epiccrown.fragmentstest;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Epiccrown on 04.03.2018.
 */

public class Crime implements Comparable {
    UUID uuid;
    String mDescription;
    boolean mSolved;
    Date date;

    public Crime(UUID uuid, String mDescription, boolean mSolved, Date date) {
        this.uuid = uuid;
        this.mDescription = mDescription;
        this.mSolved = mSolved;
        this.date = date;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getmDescription() {
        return mDescription;
    }

    public boolean ismSolved() {
        return mSolved;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        Crime crime = (Crime) o;
        int number = Integer.parseInt(crime.getmDescription().replace("Crime#",""));
        if(number < Integer.parseInt(mDescription.replace("Crime#",""))) return  -1;
        if(number > Integer.parseInt(mDescription.replace("Crime#",""))) return  1;
        return 0;
    }
}
