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

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Epiccrown on 08.03.2018.
 */

public class DatePickerFragment extends DialogFragment {



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
            final Date date = (Date)getArguments().get("date");
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.date_picker_for_dialog, null);
            final DatePicker picker = v.findViewById(R.id.datePicker_detail);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            picker.updateDate(year,month,day);
            return new AlertDialog.Builder(getActivity())
                    .setTitle("Choose date")
                    .setView(v)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);

                            int hour = cal.get(Calendar.HOUR_OF_DAY);
                            int min = cal.get(Calendar.MINUTE);
                            int year = picker.getYear();
                            int month = picker.getMonth();
                            int day = picker.getDayOfMonth();

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
        }catch (Exception ex){ex.printStackTrace();}
        return null;
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
