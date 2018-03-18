package com.epiccrown.fragmentstest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CrimeDetailsFragment extends Fragment {

    protected String titleText;
    protected String descText;
    protected Context mContext;
    public Crime crime;
    public Button date_button;
    public Button time_button;
    private static final int DATE_REQUEST = 0;
    private static final int TIME_REQUEST = 1;


    public static CrimeDetailsFragment newInstance(UUID uuid) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", uuid);

        CrimeDetailsFragment fragment = new CrimeDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_crime_details, container, false);
        getActivity().setTitle("Crime");
        if (getArguments() != null) {
            UUID uuid = (UUID) getArguments().get("key");
            crime = CrimeLab.get(getActivity()).getCrime(uuid);

            EditText title = v.findViewById(R.id.title_edit_box);
            EditText description = v.findViewById(R.id.details_edit_box);
            date_button = v.findViewById(R.id.date_button);
            time_button = v.findViewById(R.id.time_button);
            CheckBox solved = v.findViewById(R.id.solved_details);

            titleText = crime.getmTitle();
            descText = crime.getmDescription();

            title.setText(crime.getmTitle());
            title.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    crime.setmTitle(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            description.setText(crime.getmDescription());
            description.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    crime.setmDescription(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            SimpleDateFormat formatter = new SimpleDateFormat("EEE d MMM yyyy");
            String date = formatter.format(crime.getDate());

            date_button.setText(date);
            date_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getFragmentManager();
                    Bundle args = new Bundle();
                    args.putSerializable("date", crime.getDate());
                    DatePickerFragment dialog = new DatePickerFragment();
                    dialog.setArguments(args);
                    dialog.setTargetFragment(CrimeDetailsFragment.this, DATE_REQUEST);
                    dialog.show(fm, "DialogDate");
                }
            });
            formatter = new SimpleDateFormat("HH:mm");
            String time = formatter.format(crime.getDate());
            time_button.setText(time);
            time_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getFragmentManager();
                    Bundle args = new Bundle();
                    args.putSerializable("date", crime.getDate());
                    TimePickerFragment dialog = new TimePickerFragment();
                    dialog.setArguments(args);
                    dialog.setTargetFragment(CrimeDetailsFragment.this, TIME_REQUEST);
                    dialog.show(fm, "DialogTime");
                }
            });
            solved.setChecked(crime.ismSolved());
        }
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == DATE_REQUEST) {
            Date date = (Date) data.getExtras().get("date");
            SimpleDateFormat formatter = new SimpleDateFormat("EEE d MMM yyyy");

            crime.setDate(date);
            String date_string = formatter.format(crime.getDate());
            date_button.setText(date_string);
        } else if (resultCode == Activity.RESULT_OK && requestCode == TIME_REQUEST) {
            Date date = (Date) data.getExtras().get("date");
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

            crime.setDate(date);
            String time = formatter.format(crime.getDate());
            time_button.setText(time);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity()).updateCrime(crime);
    }


}
