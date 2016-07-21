package com.smok.ahmad.smok;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.smok.ahmad.smok.model.JenisMobil;
import com.smok.ahmad.smok.utility.OkHttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity {
    private Button daftar,upasbtn;
    private EditText namaprofile,noktp,noplat, editEmail, editPassword;
    private TextView backbtnregister;
    String strMerk,strJenis;

    List <String> merk = new ArrayList<>();
    List <JenisMobil> jenis = new ArrayList<>();
    private Spinner spinMerk,spinJenis;
    FormBody formBody;
    String url = "http://smokdummy.azurewebsites.net/register.php";

       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        namaprofile = (EditText) findViewById(R.id.namaLengkap);
        noktp = (EditText) findViewById(R.id.noKtp);
        noplat = (EditText) findViewById(R.id.noPlat);
           editEmail = (EditText) findViewById(R.id.register_email);
           editPassword = (EditText) findViewById(R.id.register_password);
           daftar = (Button) findViewById(R.id.daftarbaru);
        upasbtn = (Button) findViewById(R.id.daftarbaru);
        spinMerk = (Spinner) findViewById(R.id.merk);
           spinJenis = (Spinner) findViewById(R.id.jenis);
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

    }
public void klikDaftar(View v){
    String email = editEmail.getText().toString();
    String password = editPassword.getText().toString();
    String strnama = namaprofile.getText().toString();
    String strktp = noktp.getText().toString();
    String strplat = noplat.getText().toString();
    formBody = new FormBody.Builder()
            .add("nama",strnama)
            .add("no_ktp",strktp)
            .add("no_plat",strplat)
            .add("email",email)
            .add("password",password)
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
                        Intent intent = new Intent(getApplicationContext(),UpasActivity.class);
                        startActivity(intent);
                        ProfileActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alertDialog.dismiss();
                            }
                        });
                    }else{
                        ProfileActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"Register gagal",Toast.LENGTH_LONG).show();
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