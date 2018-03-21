package com.epiccrown.fragmentstest;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Epiccrown on 04.03.2018.
 */

public class Crime implements Serializable{
    UUID uuid;
    String mDescription;
    String mTitle;
    String mSuspect;
    boolean mSolved;
    Date date;

    public Crime(UUID uuid, String mDescription,String mTitle , boolean mSolved, Date date) {
        this.uuid = uuid;
        this.mTitle = mTitle;
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

    public String getmTitle() { return mTitle; }

    public boolean ismSolved() {
        return mSolved;
    }

    public Date getDate() {
        return date;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getmSuspect() {
        return mSuspect;
    }

    public void setmSuspect(String mSuspect) {
        this.mSuspect = mSuspect;
    }

    public String getPhotoFileName(){return "IMG_"+uuid.toString()+".jpg";}
}
