package com.smok.ahmad.smok;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.smok.ahmad.smok.fragment.TimePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookingActivity extends AppCompatActivity {
Toolbar toolbar;
TextView waktubooking;
TextView waktuakhirbooking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
    deklarasiWidget();
        waktubooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment(1);
                newFragment.show(getFragmentManager(),"TimePicker");
            }
        });
        waktuakhirbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment(2);
                newFragment.show(getFragmentManager(),"TimePicker");
            }
        });

    }

    private void deklarasiWidget() {
    toolbar = (Toolbar) findViewById(R.id.toolbar);
        waktubooking = (TextView) findViewById(R.id.mulaibooking);
        waktuakhirbooking = (TextView) findViewById(R.id.akhirbooking);
    setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }
    public void submitbooking(View v){
        String pattern = "HH:mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        int hasil = 0;
        try {
            Date one = dateFormat.parse(waktubooking.getText().toString());
            Date two = dateFormat.parse(waktuakhirbooking.getText().toString());
            hasil = one.compareTo(two);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    if ( hasil == 1){
        Toast.makeText(getApplicationContext(),"Pemilihan Waktu Salah",Toast.LENGTH_LONG).show();

    }
       else {
        Intent intent = new Intent(getApplicationContext(),WaktuActivity.class);
        startActivity(intent);
    }


    }


}
