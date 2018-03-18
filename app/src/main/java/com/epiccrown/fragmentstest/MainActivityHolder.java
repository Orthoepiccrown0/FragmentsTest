package com.epiccrown.fragmentstest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Epiccrown on 04.03.2018.
 */

public abstract class MainActivityHolder extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_holder);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.main_container);
        if(fragment==null){
            fragment = createFragment();
            fm.beginTransaction().replace(R.id.main_container,fragment)
                    .disallowAddToBackStack()
                    .commit();
        }
        setTitle("Crimes list");

    }


}
