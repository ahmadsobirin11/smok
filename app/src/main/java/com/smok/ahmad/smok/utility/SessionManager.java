package com.smok.ahmad.smok.utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.smok.ahmad.smok.LoginActivity;

import java.util.HashMap;

/**
 * Created by Ahmad on 29/05/2016.
 */
public class SessionManager {
    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private Context mContext;

    private static final String PREF_NAME = "SmartMobileParkingPref";

    private static final String IS_LOGIN = "IsLoggedInSMOK";

    public static final String KEY_IDUSER = "iduser";

    public static final String KEY_NAMAUSER = "namauser";

    public static final String KEY_urlfoto = "fotouser";

    public static final String KEY_pulsa = "pulsauser";

    public SessionManager(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String idUser, String nama, String urlfoto, int pulsa ) {

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_IDUSER, idUser);
        editor.putString(KEY_NAMAUSER, nama);
        editor.putString(KEY_urlfoto,urlfoto);
        editor.putInt(KEY_pulsa,pulsa);
        editor.commit();
    }

    public void updatePulsa(int pulsa) {
        editor.putInt(KEY_pulsa,pulsa);
        editor.commit();
    }

    public void logoutUser() {

        editor.clear();
        editor.commit();

        Intent i = new Intent(mContext, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(i);
    }

    public HashMap<String, String> getUserDetails() {

        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_IDUSER, pref.getString(KEY_IDUSER, null));
        user.put(KEY_NAMAUSER, pref.getString(KEY_NAMAUSER, null));
        user.put(KEY_urlfoto,pref.getString(KEY_urlfoto,null));
        user.put(KEY_pulsa,String.valueOf(pref.getInt(KEY_pulsa,0)));

        return user;
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN,false);
    }
    public void checkLogin(){

        if(!this.isLoggedIn()){

            Intent i = new Intent(mContext, LoginActivity.class);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            mContext.startActivity(i);
        }

    }
}
