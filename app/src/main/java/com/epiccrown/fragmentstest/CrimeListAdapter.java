package com.epiccrown.fragmentstest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Epiccrown on 04.03.2018.
 */

public class CrimeListAdapter extends RecyclerView.Adapter<CrimeListAdapter.MyViewHolder> {

    public CrimeListAdapter(){

    }

    public class  MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
