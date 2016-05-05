package com.smok.ahmad.smok;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.smok.ahmad.smok.model.JenisMobil;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private Button daftar,upasbtn;
    private EditText namaprofile,noktp,noplat;
    private TextView backbtnregister;
    List <String> merk = new ArrayList<>();
    List <JenisMobil> jenis = new ArrayList<>();
    private Spinner spinMerk,spinJenis;

       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        namaprofile = (EditText) findViewById(R.id.namaLengkap);
        noktp = (EditText) findViewById(R.id.noKtp);
        noplat = (EditText) findViewById(R.id.noPlat);
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

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strnama = namaprofile.getText().toString();
                String strktp = noktp.getText().toString();
                String strplat = noplat.getText().toString();
                Toast.makeText(getApplicationContext(),strnama+"\n"+strktp+"\n"+strplat, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(ProfileActivity.this, UpasActivity.class);
                startActivity(i); // menghubungkan activity splashscren ke main activity dengan intent
            }
        });
    }

    private void inisialisasiJenisMobil() {
    jenis.add(new JenisMobil("Honda","Jazz"));
        jenis.add(new JenisMobil("Honda","Brio"));
        jenis.add(new JenisMobil("Nissan","apa aja"));
    }

    private void inisialisasiMerkMobil() {
    merk.add("Honda");
        merk.add("Suzuki");
        merk.add("Chevrolete");
        merk.add("Kia");
        merk.add("Nissan");
    }
}