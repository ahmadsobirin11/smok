package com.smok.ahmad.smok;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.smok.ahmad.smok.fragment.TimePickerFragment;
import com.smok.ahmad.smok.model.ModelTempat;
import com.smok.ahmad.smok.utility.OkHttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class BookingActivity extends AppCompatActivity {
Toolbar toolbar;
TextView waktubooking;
TextView waktuakhirbooking;
    Firebase firebase;
    String url = "http://smokdummy.azurewebsites.net/booking.php";
    FormBody formBody;

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
        Toast.makeText(getApplicationContext(),UpasActivity.posisi,Toast.LENGTH_LONG).show();
        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://samplesmok.firebaseio.com/");
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

        ModelTempat modelTempat = new ModelTempat(2);
        firebase.child(UpasActivity.posisi).setValue(modelTempat);
        formBody = new FormBody.Builder()
                .add("id_user","1")
                .add("id_tempat","P1")
                .add("durasi","2")
                .add("waktu_booking",waktubooking.getText().toString())
                .add("waktu_akhir",waktuakhirbooking.getText().toString())
                .build();
        final ProgressDialog alertDialog = new ProgressDialog(this);
        alertDialog.setMessage("LOADING");
        alertDialog.setCancelable(true);
        alertDialog.show();

        try {
            OkHttpRequest.postDataToServer(url,formBody).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("Error : ",e.getMessage());
                    alertDialog.dismiss();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        int kode = object.getInt("kode");
                        Log.i("request data",String.valueOf(kode));
                        if (kode == 200){
                            finish();
                            String kodeUnik = object.getString("kode_unik");
                            Intent intent = new Intent(getApplicationContext(),WaktuActivity.class);
                            startActivity(intent);
                            BookingActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialog.dismiss();
                                }
                            });

                        }else{
                           BookingActivity.this.runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   Toast.makeText(getApplicationContext(),"Login Gagal",Toast.LENGTH_LONG).show();
                               }
                           });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(getApplicationContext(),WaktuActivity.class);
        startActivity(intent);
    }


    }


}
