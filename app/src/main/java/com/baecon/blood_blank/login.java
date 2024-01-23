package com.baecon.blood_blank;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class login extends AppCompatActivity {
    TextView reg ,login,ngologin ;
    EditText ed2,ed1;
    public static String usersession;
    private boolean READ_PHONE_STATE_granted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        int PERMISSION_ALL = 1;
        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE

            };

            if (!hasPermissions(login.this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(login.this, PERMISSIONS, PERMISSION_ALL);
                READ_PHONE_STATE_granted = true;
            }

        }
        ed1 = findViewById(R.id.input_username);
        ed2 = findViewById(R.id.input_password);
        reg=findViewById(R.id.button_register);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(login.this,register.class);
                startActivity(i);

            }
        });


        ngologin=findViewById(R.id.ngo);
        ngologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(login.this,ngo_login.class);
                startActivity(i);

            }
        });

        login=findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = ed1.getText().toString();
                String password = ed2.getText().toString();
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                if(username.equals("") || password.equals("")){
                    Toast.makeText(login.this, "Please fill details.", Toast.LENGTH_SHORT).show();
                }
                else if(!pattern.matcher(username).matches()){
                    Toast.makeText(login.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                }
                else {

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String url = UrlLinks.pylogin;

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

                    nameValuePairs.add(new BasicNameValuePair("email", username));
                    nameValuePairs.add(new BasicNameValuePair("password", password));

                    String result = null;



                    try {
                        result = jSOnClassforData.forCallingStringAndreturnSTring(url,nameValuePairs);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (result.equals("success")) {
                        usersession=username;
                        Toast.makeText(login.this, "The user has successfully logged in.", Toast.LENGTH_SHORT).show();
                        Intent io = new Intent(login.this, MainActivity.class);

                        startActivity(io);
                        finish();

                    } else {

                        Toast.makeText(login.this, "The NGO has not verified the user.", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });





    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}