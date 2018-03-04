package com.epiccrown.fragmentstest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Epiccrown on 04.03.2018.
 */

public class Firstfragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_layout,container,false);

        /*Button button = v.findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                Fragment_zwai fg= new Fragment_zwai();

                fm.beginTransaction().replace(R.id.main_container,fg).commit();
            }
        });
        */
        return v;
    }


}
