package com.smok.ahmad.smok;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smok.ahmad.smok.utility.OkHttpRequest;
import com.smok.ahmad.smok.utility.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {
    Button btnLogin;
    FormBody formBody;
    EditText email,password;
    boolean hasil ;
    String url = "http://smokdummy.azurewebsites.net/login.php";
    SessionManager session;
    // UI references.
    String idUser,nama,urlfoto;
    int pulsa;

    /*private  TextView blmlogin;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
    deklarasiWidget();

    }

    private void deklarasiWidget() {

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.email_sign_in_button );
    }
    public void klikLogin(View v){
        formBody = new FormBody.Builder()
                .add("email",email.getText().toString())
                .add("password",password.getText().toString())
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
                        if(kode == 200){
                            idUser = object.getString("id_user");
                            nama = object.getString("nama");
                            urlfoto = object.getString("url_foto");
                            pulsa = object.getInt("saldo");

                            LoginActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialog.dismiss();
                                    session = new SessionManager(getApplicationContext());
                                    session.createLoginSession(idUser,nama,urlfoto,pulsa);

                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("SmartMobileParkingPref",MODE_PRIVATE);
                                    Log.i("hasil contain",String.valueOf(pref.contains(SessionManager.KEY_IDUSER)));

                                    Intent intent = new Intent(getApplicationContext(),UpasActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                    // Add new Flag to start new Activity
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }else{
                            hasil = false;
                            LoginActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialog.dismiss();
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



 }
    public void klikregister(View v){
        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
        startActivity(intent);
    }
}

