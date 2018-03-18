package com.epiccrown.fragmentstest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Epiccrown on 14.03.2018.
 */

public class AddCrime extends Fragment {


    private static final int DATE_REQUEST = 0;
    private static final int TIME_REQUEST = 1;
    private Button datebutton;
    private Button timebutton;

    private Date date_marker;
    private String title_crime="";
    private String description_crime="";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_crime_layout,null);
        datebutton = v.findViewById(R.id.date_button_add);
        timebutton = v.findViewById(R.id.time_button_add);

        EditText title = v.findViewById(R.id.title_edit_box_add);
        final EditText description = v.findViewById(R.id.details_edit_box_add);

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                title_crime = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                description_crime = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        datebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        FragmentManager fm = getFragmentManager();
                        Bundle args = new Bundle();
                        args.putSerializable("date",new Date());
                        DatePickerFragment dialog = new DatePickerFragment();
                        dialog.setArguments(args);
                        dialog.setTargetFragment(AddCrime.this,DATE_REQUEST);
                        dialog.show(fm,"DialogDate");

            }
        });

        timebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                Bundle args = new Bundle();
                args.putSerializable("date",new Date());
                TimePickerFragment dialog = new TimePickerFragment();
                dialog.setArguments(args);
                dialog.setTargetFragment(AddCrime.this,TIME_REQUEST);
                dialog.show(fm,"DialogTime");
            }
        });

        Button cancelbutton = v.findViewById(R.id.cancel_add);
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        Button addbutton = v.findViewById(R.id.finish_add);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title_crime.trim().equals("")) {toastMe("Add title");return;}
                if(description_crime.trim().equals("")) {toastMe("Add description"); return;}
                if(date_marker==null) date_marker=new Date();
                UUID uuid = UUID.randomUUID();
                CrimeLab.get(getActivity()).addCrime(new Crime(uuid,description_crime, title_crime, false,date_marker));
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return v;
    }

    public void toastMe(String text){
        Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK && requestCode==DATE_REQUEST){
            date_marker = (Date) data.getExtras().get("date");
            SimpleDateFormat formatter = new SimpleDateFormat("EEE d MMM yyyy");

            String date_string = formatter.format(date_marker);
            datebutton.setText(date_string);
        }else if(resultCode==Activity.RESULT_OK && requestCode==TIME_REQUEST){
            date_marker = (Date) data.getExtras().get("date");
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

            String time = formatter.format(date_marker);
            timebutton.setText(time);
        }
    }



}
