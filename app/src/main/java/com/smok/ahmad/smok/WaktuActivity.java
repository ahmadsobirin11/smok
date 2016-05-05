package com.smok.ahmad.smok;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class WaktuActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView timer;
    private long startTime = 0L;
    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    int secs, mins, milliseconds, hour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waktu);
        deklarasiWidget();
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 1);
    }

    private void deklarasiWidget() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        timer = (TextView) findViewById(R.id.timer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }
    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            secs = (int) (updatedTime / 1000);
            hour = mins / 60;
            mins = secs / 60;
            secs = secs % 60;
            milliseconds = (int) (updatedTime % 100);
            timer.setText(String.format("%02d", hour) + ":" + String.format("%02d", mins) + ":"
                    + String.format("%02d", secs));
            customHandler.postDelayed(this, 1);
        }

    };
}
