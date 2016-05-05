package com.smok.ahmad.smok;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SaldoActivity extends AppCompatActivity {
    private Button isisaldo;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo);
        deklarasiWidget();
        settingToolbar();
        settingNavigationView();

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
                    intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
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
    }
}
