package com.epiccrown.fragmentstest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Epiccrown on 04.03.2018.
 */

public class Firstfragment extends Fragment {

    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.clime_list_recycler,container,false);
        mRecyclerView = v.findViewById(R.id.recyclerview_crimes);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }

    public void updateUI(){
        mRecyclerView.setAdapter(new CrimeListAdapter(CrimeLab.get(getActivity()),getActivity()));
    }


}
