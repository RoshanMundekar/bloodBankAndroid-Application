package com.baecon.blood_blank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ngo_login extends AppCompatActivity {
    EditText ed2,ed1;
    ImageView ngo_register ,ngo_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_login);
        getSupportActionBar().hide();
        ed1 = findViewById(R.id.editText);
        ed2 = findViewById(R.id.editText1);

//        ngo_register=findViewById(R.id.imageView4);
//        ngo_register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(ngo_login.this,ngo_register.class);
//                startActivity(i);
//
//            }
//        });

        ngo_login=findViewById(R.id.singupbtn);
        ngo_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = ed1.getText().toString();
                String password = ed2.getText().toString();

                if(username.equals("") || password.equals("")){
                    Toast.makeText(ngo_login.this, "Please fill details.", Toast.LENGTH_SHORT).show();
                }
                else {

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String url = UrlLinks.ngopylogin;

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

                    nameValuePairs.add(new BasicNameValuePair("username", username));
                    nameValuePairs.add(new BasicNameValuePair("password", password));

                    String result = null;



                    try {
                        result = jSOnClassforData.forCallingStringAndreturnSTring(url,nameValuePairs);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (result.equals("success")) {

                        Toast.makeText(ngo_login.this, "Successfully", Toast.LENGTH_SHORT).show();
                        Intent io = new Intent(ngo_login.this, ngoactive.class);

                        startActivity(io);
                        finish();

                    } else {

                        Toast.makeText(ngo_login.this, "Wrong username or password", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

    }
}