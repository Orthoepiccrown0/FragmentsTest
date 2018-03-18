package com.epiccrown.fragmentstest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Epiccrown on 09.03.2018.
 */

public class TimePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.time_picker_for_dialog,null);
        final TimePicker picker = v.findViewById(R.id.timePicker_detail);
        final Date date = (Date)getArguments().get("date");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        picker.setIs24HourView(true);
        picker.setHour(hour);
        picker.setMinute(min);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Crime time")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hour = picker.getHour();
                        int min = picker.getMinute();

                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        cal.set(Calendar.HOUR_OF_DAY,hour);
                        cal.set(Calendar.MINUTE,min);
                        cal.set(Calendar.SECOND,0);
                        cal.set(Calendar.DAY_OF_MONTH,day);
                        cal.set(Calendar.MONTH,month);
                        cal.set(Calendar.YEAR,year);

                        Date date = cal.getTime();

                        sendResult(Activity.RESULT_OK,date);
                    }
                })
                .create();
    }

    private void sendResult(int resultcode, Date date){
        if(getTargetFragment()==null){
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("date",date);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultcode,intent);
    }
}
