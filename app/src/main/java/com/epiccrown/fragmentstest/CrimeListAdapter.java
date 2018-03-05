package com.epiccrown.fragmentstest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Epiccrown on 04.03.2018.
 */

public class CrimeListAdapter extends RecyclerView.Adapter<CrimeListAdapter.MyViewHolder> {

    private CrimeLab lab;
    private Crime crime_item;
    private HashMap crimes;
    private Context mContext;
    private ArrayList<Crime> crimes_array = new ArrayList<>();
    public CrimeListAdapter(CrimeLab lab, Context context){
        this.lab = lab;
        this.crimes = lab.getCrimes();
        this.crimes_array.addAll(crimes.values());
        Collections.sort(crimes_array);
        this.mContext = context;
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView description;
        TextView title;
        TextView date;
        CheckBox solved;

        public MyViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.desc_item);
            title = itemView.findViewById(R.id.title_item);
            solved = itemView.findViewById(R.id.solved_item);
            date = itemView.findViewById(R.id.date);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.crime_item_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        crime_item = crimes_array.get(position);
        holder.title.setText(crime_item.getmDescription());
        holder.description.setText(crime_item.getUuid().toString());
        holder.solved.setChecked(crime_item.ismSolved());
        holder.date.setText(crime_item.getDate().toString());
    }

    @Override
    public int getItemCount() {
        return crimes.size();
    }


}
