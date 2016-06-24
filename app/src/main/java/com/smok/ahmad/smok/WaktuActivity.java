package com.smok.ahmad.smok;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.smok.ahmad.smok.model.ModelTempat;
import com.smok.ahmad.smok.utility.OkHttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class WaktuActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView timer;
    Button btnSelesai;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    FormBody formBody;
    String url = "http://smokdummy.azurewebsites.net/selesai_booking.php";
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    int secs, mins, milliseconds, hour;
    Firebase firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waktu);
        deklarasiWidget();
        Toast.makeText(getApplicationContext(),UpasActivity.posisi,Toast.LENGTH_LONG).show();
        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://samplesmok.firebaseio.com/");
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 1);
    }

    public void klikSelesai(View v){
        formBody = new FormBody.Builder()
                .add("id_user","1")
                .add("kode_unik","123")
                .build();
        final ProgressDialog alertDialog = new ProgressDialog(this);
        alertDialog.setMessage("LOADING");
        alertDialog.setCancelable(true);
        alertDialog.show();
        ModelTempat modelTempat = new ModelTempat(0);
        firebase.child(UpasActivity.posisi).setValue(modelTempat);
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
                            Intent intent = new Intent(getApplicationContext(),UpasActivity.class);
                            startActivity(intent);
                            WaktuActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialog.dismiss();
                                }
                            });
                        }else{
                            WaktuActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"Login Gagal", Toast.LENGTH_LONG).show();
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
    }
    private void deklarasiWidget() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        timer = (TextView) findViewById(R.id.timer);
        btnSelesai = (Button) findViewById(R.id.btn_selesai);
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
