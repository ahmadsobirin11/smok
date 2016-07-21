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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.core.Context;
import com.smok.ahmad.smok.model.ModelTempat;
import com.smok.ahmad.smok.utility.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class UpasActivity extends AppCompatActivity {
    private Button isisaldo,buttonP1,buttonP2,buttonP3,buttonP4,buttonP5,buttonP6,buttonP7,buttonP8,buttonP9,buttonP10;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Firebase firebase;
    boolean kondisiP1,kondisiP2,kondisiP3,kondisiP4,kondisiP5,kondisiP6,kondisiP7,kondisiP8,kondisiP9,kondisiP10;
    public static String posisi;
    SessionManager session;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upas);
        deklarasiWidget();
        settingToolbar();
        settingNavigationView();
        deklarasiTempat();
        setupFirebase();
        setupKlikTempat();
        session = new SessionManager(getApplicationContext());
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



    private void setupKlikTempat() {
    buttonP1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (kondisiP1) {
                posisi="posisi1";
                Intent intent = new Intent(getApplicationContext(),BookingActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"Parkir Telah Di Booking",Toast.LENGTH_LONG).show();

            }
        }
    });
        buttonP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kondisiP2) {
                    posisi="posisi2";
                    Intent intent = new Intent(getApplicationContext(),BookingActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Parkir Telah Di Booking",Toast.LENGTH_LONG).show();

                }
            }
        });
        buttonP3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kondisiP3) {
                    posisi="posisi3";
                    Intent intent = new Intent(getApplicationContext(),BookingActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Parkir Telah Di Booking",Toast.LENGTH_LONG).show();

                }
            }
        });
        buttonP4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kondisiP4) {
                    posisi="posisi4";
                    Intent intent = new Intent(getApplicationContext(),BookingActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Parkir Telah Di Booking",Toast.LENGTH_LONG).show();

                }
            }
        });
        buttonP5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kondisiP5) {
                    posisi="posisi5";
                    Intent intent = new Intent(getApplicationContext(),BookingActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Parkir Telah Di Booking",Toast.LENGTH_LONG).show();

                }
            }
        });
        buttonP6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kondisiP6) {
                    posisi="posisi6";
                    Intent intent = new Intent(getApplicationContext(),BookingActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Parkir Telah Di Booking",Toast.LENGTH_LONG).show();

                }
            }
        });
        buttonP7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kondisiP7) {
                    posisi="posisi7";
                    Intent intent = new Intent(getApplicationContext(),BookingActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Parkir Telah Di Booking",Toast.LENGTH_LONG).show();

                }
            }
        });
        buttonP8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kondisiP8) {
                    posisi="posisi8";
                    Intent intent = new Intent(getApplicationContext(),BookingActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Parkir Telah Di Booking",Toast.LENGTH_LONG).show();

                }
            }
        });
        buttonP9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kondisiP9) {
                    posisi="posisi9";
                    Intent intent = new Intent(getApplicationContext(),BookingActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Parkir Telah Di Booking",Toast.LENGTH_LONG).show();

                }
            }
        });
        buttonP10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kondisiP10) {
                    posisi="posisi10";
                    Intent intent = new Intent(getApplicationContext(),BookingActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Parkir Telah Di Booking",Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private void setupFirebase() {
        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://samplesmok.firebaseio.com/");
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.print("Cek bayu : "+dataSnapshot.getValue());
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    ModelTempat modelGarasi = postSnapshot.getValue(ModelTempat.class);
                    Log.d(postSnapshot.getKey() + " Garasi : ", String.valueOf(modelGarasi.getStatus()));
                    if(postSnapshot.getKey().equals("posisi1")){
                        if(modelGarasi.getStatus()==2) {
                            kondisiP1=false;
                            buttonP1.setBackgroundColor(getResources().getColor(R.color.parkirbooking));
                        }else if(modelGarasi.getStatus()==1) {
                            kondisiP1=false;
                            buttonP1.setBackgroundColor(getResources().getColor(R.color.parkirterisi));
                        }else{
                            buttonP1.setBackgroundColor(getResources().getColor(R.color.parkirkosong));
                            kondisiP1=true;
                        }

                    }
                    if(postSnapshot.getKey().equals("posisi2")){
                        if(modelGarasi.getStatus()==2) {
                            kondisiP2=false;
                            buttonP2.setBackgroundColor(getResources().getColor(R.color.parkirbooking));
                        }else if(modelGarasi.getStatus()==1) {
                            kondisiP2=false;
                            buttonP2.setBackgroundColor(getResources().getColor(R.color.parkirterisi));
                        }else{
                            buttonP2.setBackgroundColor(getResources().getColor(R.color.parkirkosong));
                            kondisiP2=true;
                        }

                    }
                    if(postSnapshot.getKey().equals("posisi3")){
                        if(modelGarasi.getStatus()==2) {
                            kondisiP3=false;
                            buttonP3.setBackgroundColor(getResources().getColor(R.color.parkirbooking));
                        }else if(modelGarasi.getStatus()==1) {
                            kondisiP3=false;
                            buttonP3.setBackgroundColor(getResources().getColor(R.color.parkirterisi));
                        }else{
                            buttonP3.setBackgroundColor(getResources().getColor(R.color.parkirkosong));
                            kondisiP3=true;
                        }

                    }
                    if(postSnapshot.getKey().equals("posisi4")){
                        if(modelGarasi.getStatus()==2) {
                            kondisiP4=false;
                            buttonP4.setBackgroundColor(getResources().getColor(R.color.parkirbooking));
                        }else if(modelGarasi.getStatus()==1) {
                            kondisiP4=false;
                            buttonP4.setBackgroundColor(getResources().getColor(R.color.parkirterisi));
                        }else{
                            buttonP4.setBackgroundColor(getResources().getColor(R.color.parkirkosong));
                            kondisiP4=true;
                        }

                    }
                    if(postSnapshot.getKey().equals("posisi5")){
                        if(modelGarasi.getStatus()==2) {
                            kondisiP5=false;
                            buttonP5.setBackgroundColor(getResources().getColor(R.color.parkirbooking));
                        }else if(modelGarasi.getStatus()==1) {
                            kondisiP5=false;
                            buttonP5.setBackgroundColor(getResources().getColor(R.color.parkirterisi));
                        }else{
                            buttonP5.setBackgroundColor(getResources().getColor(R.color.parkirkosong));
                            kondisiP5=true;
                        }

                    }
                    if(postSnapshot.getKey().equals("posisi6")){
                        if(modelGarasi.getStatus()==2) {
                            kondisiP6=false;
                            buttonP6.setBackgroundColor(getResources().getColor(R.color.parkirbooking));
                        }else if(modelGarasi.getStatus()==1) {
                            kondisiP6=false;
                            buttonP6.setBackgroundColor(getResources().getColor(R.color.parkirterisi));
                        }else{
                            buttonP6.setBackgroundColor(getResources().getColor(R.color.parkirkosong));
                            kondisiP6=true;
                        }

                    }
                    if(postSnapshot.getKey().equals("posisi7")){
                        if(modelGarasi.getStatus()==2) {
                            kondisiP7=false;
                            buttonP7.setBackgroundColor(getResources().getColor(R.color.parkirbooking));
                        }else if(modelGarasi.getStatus()==1) {
                            kondisiP7=false;
                            buttonP7.setBackgroundColor(getResources().getColor(R.color.parkirterisi));
                        }else{
                            buttonP7.setBackgroundColor(getResources().getColor(R.color.parkirkosong));
                            kondisiP7=true;
                        }

                    }
                    if(postSnapshot.getKey().equals("posisi8")){
                        if(modelGarasi.getStatus()==2) {
                            kondisiP8=false;
                            buttonP8.setBackgroundColor(getResources().getColor(R.color.parkirbooking));
                        }else if(modelGarasi.getStatus()==1) {
                            kondisiP8=false;
                            buttonP8.setBackgroundColor(getResources().getColor(R.color.parkirterisi));
                        }else{
                            buttonP8.setBackgroundColor(getResources().getColor(R.color.parkirkosong));
                            kondisiP8=true;
                        }

                    }

                    if(postSnapshot.getKey().equals("posisi9")){
                        if(modelGarasi.getStatus()==2) {
                            kondisiP9=false;
                            buttonP9.setBackgroundColor(getResources().getColor(R.color.parkirbooking));
                        }else if(modelGarasi.getStatus()==1) {
                            kondisiP9=false;
                            buttonP9.setBackgroundColor(getResources().getColor(R.color.parkirterisi));
                        }else{
                            buttonP9.setBackgroundColor(getResources().getColor(R.color.parkirkosong));
                            kondisiP9=true;
                        }

                    }
                    if(postSnapshot.getKey().equals("posisi10")){
                        if(modelGarasi.getStatus()==2) {
                            kondisiP10=false;
                            buttonP10.setBackgroundColor(getResources().getColor(R.color.parkirbooking));
                        }else if(modelGarasi.getStatus()==1) {
                            kondisiP10=false;
                            buttonP10.setBackgroundColor(getResources().getColor(R.color.parkirterisi));
                        }else{
                            buttonP10.setBackgroundColor(getResources().getColor(R.color.parkirkosong));
                            kondisiP10=true;
                        }

                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private void deklarasiTempat() {
        buttonP1 = (Button) findViewById(R.id.buttonP1);
        buttonP2 = (Button) findViewById(R.id.buttonP2);
        buttonP3 = (Button) findViewById(R.id.buttonP3);
        buttonP4 = (Button) findViewById(R.id.buttonP4);
        buttonP5 = (Button) findViewById(R.id.buttonP5);
        buttonP6 = (Button) findViewById(R.id.buttonP6);
        buttonP7 = (Button) findViewById(R.id.buttonP7);
        buttonP8 = (Button) findViewById(R.id.buttonP8);
        buttonP9 = (Button) findViewById(R.id.buttonP9);
        buttonP10 = (Button) findViewById(R.id.buttonP10);
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
                return true;
            case R.id.id_menu_saldo:
                intent = new Intent(getApplicationContext(),SaldoActivity.class);
                startActivity(intent);
                return true;
            case R.id.id_menu_logout:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(UpasActivity.this);

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

        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigation_drawer);
    }
}
