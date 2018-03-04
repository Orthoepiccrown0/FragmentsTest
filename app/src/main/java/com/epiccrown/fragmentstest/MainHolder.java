package com.epiccrown.fragmentstest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainHolder extends MainActivityHolder {

    @Override
    protected Fragment createFragment() {
        return new Firstfragment();
    }




}
