package com.smok.ahmad.smok;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smok.ahmad.smok.utility.OkHttpRequest;
import com.smok.ahmad.smok.utility.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class SaldoActivity extends AppCompatActivity {
    private Button isisaldo;
    EditText kodeVoucher;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    FormBody formBody;
    String url = "http://smokdummy.azurewebsites.net/isisaldo.php";
    int voucher=0;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo);
        deklarasiWidget();
        settingToolbar();
        settingNavigationView();
        session = new SessionManager(SaldoActivity.this);
        session.checkLogin();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        int pulsa = Integer.parseInt(user.get(SessionManager.KEY_pulsa));
        String nama = user.get(SessionManager.KEY_NAMAUSER);
        String urlfoto = user.get(SessionManager.KEY_urlfoto);
        ImageView foto = (ImageView) findViewById(R.id.header_foto);
        TextView namauser = (TextView) findViewById(R.id.header_nama);
        TextView saldouser = (TextView) findViewById(R.id.header_saldo);
        namauser.setText(nama);
        saldouser.setText(String.valueOf(pulsa));
        Picasso.with(this).load(urlfoto).into(foto);

    }
    public void klikIsiVoucher(View v){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SaldoActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("Isi Ulang Saldo");

        // Setting Dialog Message
        alertDialog.setMessage("Apakah anda yakin kode saldo yang dimasukkan benar?");



        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                dialog.cancel();
                formBody = new FormBody.Builder()
                        .add("id_user","1")
                        .add("kode_voucher","123")
                        .build();
                final ProgressDialog alertDialog = new ProgressDialog(SaldoActivity.this);
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
                                    voucher = object.getInt("jumlah_voucher");
                                    SaldoActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            alertDialog.dismiss();
                                            Toast.makeText(getApplicationContext(),"Saldo Anda Betambah Rp."+voucher,Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }else{
                                    SaldoActivity.this.runOnUiThread(new Runnable() {
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


    private void settingNavigationView() {
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.drawer_open,R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(navitemselect);
    }

    NavigationView.OnNavigationItemSelectedListener navitemselect = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            menuItem.setCheckable(true);
            drawer.closeDrawer(GravityCompat.START);
            Intent intent;
            switch (menuItem.getItemId()){
                case R.id.id_menu_home:
                    intent = new Intent(getApplicationContext(),UpasActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.id_menu_saldo:
                    return true;
                case R.id.id_menu_logout:
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(SaldoActivity.this);

                    // Setting Dialog Title
                    alertDialog.setTitle("Keluar.");

                    // Setting Dialog Message
                    alertDialog.setMessage("Apakah anda yakin ingin keluar?");



                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            session.logoutUser();
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                            finish();
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
                    return true;
                default:
                    return true;
            }

        }
    };
    private void settingToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }

    private void deklarasiWidget() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        isisaldo = (Button) findViewById(R.id.button11);
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigation_drawer);
        kodeVoucher = (EditText) findViewById(R.id.edt_kodevoucher);
    }
}
