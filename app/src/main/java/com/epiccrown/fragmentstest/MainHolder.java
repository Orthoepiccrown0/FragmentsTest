package com.epiccrown.fragmentstest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainHolder extends MainActivityHolder implements Firstfragment.Callbacks, CrimeDetailsFragment.Callback{

    public static boolean isPhone = false;
    public static DetailsPager pager;
    @Override
    protected Fragment createFragment() {
        return new Firstfragment();
    }


    @Override
    public void onCrimeSelected(Crime crime) {
        if(findViewById(R.id.details_fragment_container)==null){
            isPhone = true;
            Bundle bundle = new Bundle();
            bundle.putSerializable("key", crime);
            FragmentManager fm = getSupportFragmentManager();
            DetailsPager fragment = new DetailsPager();
            pager = fragment;
            fragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.main_container,fragment)
                        .addToBackStack(null)
                        .commit();

        }else{
            isPhone = false;
            Bundle bundle = new Bundle();
            bundle.putSerializable("key", crime);
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = new DetailsPager();
            fragment.setArguments(bundle);
            fm.beginTransaction().replace(R.id.details_fragment_container,fragment)
                    .disallowAddToBackStack()
                    .commit();

        }
    }

    @Override
    public void onCrimeUpdate(Crime crime) {
        Firstfragment fragment = (Firstfragment) getSupportFragmentManager().findFragmentById(R.id.main_container);
        fragment.updateUI();
    }
}
