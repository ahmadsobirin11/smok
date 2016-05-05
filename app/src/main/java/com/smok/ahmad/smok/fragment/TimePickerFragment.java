package com.smok.ahmad.smok.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import com.smok.ahmad.smok.R;

import java.util.Calendar;

/**
 * Created by Ahmad on 05/05/2016.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
int angka;

    public TimePickerFragment(int angka) {
        this.angka = angka;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(),this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView tv = (TextView) getActivity().findViewById(R.id.mulaibooking);
        TextView tvakhir = (TextView) getActivity().findViewById(R.id.akhirbooking);
        //Display the user changed time on TextView
        if (angka==1){
            tv.setText(String.valueOf(hourOfDay)
                    + ":" + String.valueOf(minute));

        }
        else
        if (angka==2){
            tvakhir.setText(String.valueOf(hourOfDay)
                    + ":" + String.valueOf(minute));

        }

    }
}
