package com.epiccrown.fragmentstest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

/**
 * Created by Epiccrown on 04.03.2018.
 */

public class CrimeListAdapter extends RecyclerView.Adapter<CrimeListAdapter.MyViewHolder> {

    private CrimeLab lab;
    private Crime crime_item;

    private Context mContext;
    private ArrayList<Crime> crimes_array = new ArrayList<>();

    public CrimeListAdapter(CrimeLab lab, Context context) {
        this.lab = lab;
        crimes_array = this.lab.getCrimes();

        Collections.reverse(crimes_array);
        this.mContext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView description;
        TextView title;
        TextView date;
        CheckBox solved;
        UUID uuid;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.desc_item);
            title = itemView.findViewById(R.id.title_item);
            solved = itemView.findViewById(R.id.solved_item);
            date = itemView.findViewById(R.id.date);
            //viewBackground = itemView.findViewById(R.id.card_background);
            //viewForeground = itemView.findViewById(R.id.card_foreground);
        }


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.crime_item_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Crime crime_item = crimes_array.get(position);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm EEE d MMM yyyy");
        String date = formatter.format(crime_item.getDate());
        holder.uuid = crime_item.getUuid();
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(holder.uuid);
            }
        });
        holder.title.setText(crime_item.getmTitle());
        holder.description.setText(crime_item.getmDescription());
        holder.solved.setChecked(crime_item.ismSolved());
        holder.solved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime_item.setmSolved(isChecked);
                CrimeLab.get(mContext).updateCrime(crime_item);
            }
        });
        holder.date.setText(date);

    }


    private void replaceFragment(UUID uuid) {
        FragmentManager fm = ((FragmentActivity) mContext).getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.main_container);
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", uuid);
        if (fragment instanceof Firstfragment) {
            fragment = new DetailsPager();
            fragment.setArguments(bundle);
            fm.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.main_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public int getItemCount() {
        return crimes_array.size();
    }

    public void setCrimes(ArrayList<Crime> crimes){
        crimes_array = crimes;
    }

    public void removeItem(int position) {
        crimes_array.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Crime item, int position) {
        crimes_array.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

}
