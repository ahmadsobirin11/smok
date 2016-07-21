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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.smok.ahmad.smok.fragment.TimePickerFragment;
import com.smok.ahmad.smok.model.JenisMobil;
import com.smok.ahmad.smok.model.ModelTempat;
import com.smok.ahmad.smok.utility.OkHttpRequest;
import com.smok.ahmad.smok.utility.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class BookingActivity extends AppCompatActivity {
Toolbar toolbar;
TextView waktubooking;
TextView waktuakhirbooking,posisi;
    Firebase firebase;
    String url = "http://smokdummy.azurewebsites.net/booking.php";
    FormBody formBody;

    List <String> merk = new ArrayList<>();
    List <JenisMobil> jenis = new ArrayList<>();
    private Spinner spinMerk,spinJenis;
    String strMerk,strJenis;
    public static String kodeUnik;
    public static long waktu;

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

        /*setup spinner */
        spinMerk = (Spinner) findViewById(R.id.merk);
        spinJenis = (Spinner) findViewById(R.id.jenis);
        posisi = (TextView) findViewById(R.id.txtViewPosisi);
        posisi.setText("Booking "+UpasActivity.posisi);
        inisialisasiMerkMobil();
        inisialisasiJenisMobil();
        ArrayAdapter<String> dataAdapterMerk = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,merk);
        spinMerk.setAdapter(dataAdapterMerk);
        spinMerk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> listTampilJenis = new ArrayList<String>();
                Toast.makeText(getApplicationContext(),merk.get(position),Toast.LENGTH_LONG).show();
                strMerk = merk.get(position);
                for(int i = 0; i < jenis.size();i++){
                    if (jenis.get(i).getMerkMobil().equalsIgnoreCase(merk.get(position))){
                        listTampilJenis.add(jenis.get(i).getJenisMobil());
                    }
                }
                ArrayAdapter<String> dataAdapterJenis = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,listTampilJenis);
                spinJenis.setAdapter(dataAdapterJenis);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
        /*
        try {
            Date one = dateFormat.parse(waktubooking.getText().toString());
            Date two = dateFormat.parse(waktuakhirbooking.getText().toString());
            hasil = one.compareTo(two);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

    if ( hasil == 1){
        Toast.makeText(getApplicationContext(),"Pemilihan Waktu Salah",Toast.LENGTH_LONG).show();

    }else{

        ModelTempat modelTempat = new ModelTempat(2);
        firebase.child(UpasActivity.posisi).setValue(modelTempat);
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        waktu = cal.getTimeInMillis();
        Log.i("tanggal sekarang",String.valueOf(formatDate.format(cal.getTime())));


        HashMap<String,String> user = sessionManager.getUserDetails();
        Toast.makeText(getApplicationContext(),user.get(SessionManager.KEY_IDUSER),Toast.LENGTH_LONG).show();
        Log.i("id user",user.get(SessionManager.KEY_IDUSER));
        formBody = new FormBody.Builder()
                .add("id_user",user.get(SessionManager.KEY_IDUSER))
                .add("id_tempat",UpasActivity.posisi)
                .add("waktu_booking",String.valueOf(formatDate.format(cal.getTime())))
                .add("merk",spinMerk.getSelectedItem().toString())
                .add("tipe",spinJenis.getSelectedItem().toString())
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
                            kodeUnik = object.getString("kode_unik");
                            BookingActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialog.dismiss();

                                    Intent intent = new Intent(getApplicationContext(),WaktuActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });

                        }else{
                            Log.i("pesan request data",object.getString("message"));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.daftarmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /*data spinner merk dan tipe */
    private void inisialisasiJenisMobil() {
        jenis.add(new JenisMobil("Honda","Jazz"));
        jenis.add(new JenisMobil("Honda","Brio"));
        jenis.add(new JenisMobil("Honda","CR-V"));
        jenis.add(new JenisMobil("Honda","BR-V"));
        jenis.add(new JenisMobil("Honda","H-RV"));
        jenis.add(new JenisMobil("Honda","Civic"));
        jenis.add(new JenisMobil("Honda","Oddysey"));
        jenis.add(new JenisMobil("Honda","Mobilio"));
        jenis.add(new JenisMobil("Honda","City"));
        jenis.add(new JenisMobil("Honda","Freed"));
        jenis.add(new JenisMobil("Toyota","Fortuner"));
        jenis.add(new JenisMobil("Toyota","Yaris"));
        jenis.add(new JenisMobil("Toyota","Agya"));
        jenis.add(new JenisMobil("Toyota","Kijang Innova"));
        jenis.add(new JenisMobil("Toyota","Avanza"));
        jenis.add(new JenisMobil("Toyota","Sienta"));
        jenis.add(new JenisMobil("Toyota","Camry"));
        jenis.add(new JenisMobil("Toyota","Corrola Altis"));
        jenis.add(new JenisMobil("Toyota","Alphard"));
        jenis.add(new JenisMobil("Toyota","Etios Valco"));
        jenis.add(new JenisMobil("Datsun","Go"));
        jenis.add(new JenisMobil("Datsun","Go+"));
        jenis.add(new JenisMobil("Datsun","Go Panca"));
        jenis.add(new JenisMobil("Daihatsu","Xenia"));
        jenis.add(new JenisMobil("Daihatsu","Ayla"));
        jenis.add(new JenisMobil("Daihatsu","Sirion"));
        jenis.add(new JenisMobil("Daihatsu","Luxio"));
        jenis.add(new JenisMobil("Daihatsu","Luxio"));
        jenis.add(new JenisMobil("Nissan","Juke"));
        jenis.add(new JenisMobil("Nissan","Grand Livina"));
        jenis.add(new JenisMobil("Nissan","March"));
        jenis.add(new JenisMobil("Nissan","Serena"));
        jenis.add(new JenisMobil("Kia","Rio"));
        jenis.add(new JenisMobil("Kia","Picanto"));
        jenis.add(new JenisMobil("Kia","Sorento"));
        jenis.add(new JenisMobil("Ford","Fiesta"));
        jenis.add(new JenisMobil("Ford","Focus"));


    }

    private void inisialisasiMerkMobil() {
        merk.add("Honda");
        merk.add("Suzuki");
        merk.add("Toyota");
        merk.add("Kia");
        merk.add("Nissan");
        merk.add("Daihatsu");
        merk.add("Datsun");
        merk.add("Ford");
        merk.add("Mitsubisi");
        merk.add("Mazda");
        merk.add("Chevrolet");
    }
}
