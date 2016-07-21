package com.smok.ahmad.smok;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.smok.ahmad.smok.utility.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class WaktuActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView timer,kodeUnik;
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
        kodeUnik.setText(BookingActivity.kodeUnik);
    }

    public void klikSelesai(View v){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(WaktuActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("Keluar");

        // Setting Dialog Message
        alertDialog.setMessage("Apakah anda yakin ingin keluar?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                dialog.cancel();
                final SessionManager sessionManager = new SessionManager(getApplicationContext());
                HashMap<String,String> mapUser= sessionManager.getUserDetails();

                DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                Log.i("tanggal sekarang",String.valueOf(formatDate.format(cal.getTime())));

                long waktusekarang = cal.getTimeInMillis();
                long durasi = (waktusekarang-BookingActivity.waktu)/1000/60/60;
                int biaya = (int) ((durasi *3000)+3000);
                final int sisapulsa = Integer.valueOf(mapUser.get(SessionManager.KEY_pulsa))-biaya;

                formBody = new FormBody.Builder()
                        .add("id_user",mapUser.get(SessionManager.KEY_IDUSER))
                        .add("kode_unik",BookingActivity.kodeUnik)
                        .add("waktu_keluar",String.valueOf(formatDate.format(cal.getTime())))
                        .add("durasi",String.valueOf(durasi))
                        .build();
                final ProgressDialog alertDialog = new ProgressDialog(WaktuActivity.this);
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
                                    sessionManager.updatePulsa(sisapulsa);
                                    Intent intent = new Intent(getApplicationContext(),UpasActivity.class);
                                    startActivity(intent);
                                    finish();
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
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();

                    }


    private void deklarasiWidget() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        timer = (TextView) findViewById(R.id.timer);
        btnSelesai = (Button) findViewById(R.id.btn_selesai);
        kodeUnik = (TextView) findViewById(R.id.txtKodeUnik);

        setSupportActionBar(toolbar);
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
