package com.baecon.blood_blank;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baecon.blood_blank.ui.blooddoner.blooddoner;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class feedbackform extends AppCompatActivity {
    EditText ed1,ed2,ed3,ed4;
    Button feedback;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbackform);

        String username=getIntent().getStringExtra("username");
        String bloodgroup=getIntent().getStringExtra("bloodgroup");
        String emailid=getIntent().getStringExtra("emailid");

        ed1 = findViewById(R.id.input_fullName);
        ed2 = findViewById(R.id.bloodgroup);
        ed3 = findViewById(R.id.mail);
        ed4 = findViewById(R.id.input_details);

        ed1.setText(username);
        ed2.setText(bloodgroup);
        ed3.setText(emailid);

        feedback = findViewById(R.id.feedback);

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = ed1.getText().toString();
                String bloodgroup = ed2.getText().toString();
                String emailid = ed3.getText().toString();
                String feedback = ed4.getText().toString();


                if(username.equals("") || bloodgroup.equals("")|| emailid.equals("")|| feedback.equals("")){
                    Toast.makeText(feedbackform.this, "Please fill details", Toast.LENGTH_SHORT).show();
                }


                else {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String url = UrlLinks.feedback;

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);

                    nameValuePairs.add(new BasicNameValuePair("username", username));
                    nameValuePairs.add(new BasicNameValuePair("bloodgroup", bloodgroup));
                    nameValuePairs.add(new BasicNameValuePair("emailid", emailid));
                    nameValuePairs.add(new BasicNameValuePair("feedback", feedback));


                    String result = null;
                    try {
                        result = jSOnClassforData.forCallingStringAndreturnSTring(url,nameValuePairs);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (result.equals("success")) {

                        Toast.makeText(feedbackform.this, " Successfully sent donor notification ", Toast.LENGTH_SHORT).show();


                        Intent io = new Intent(feedbackform.this, ngoactive.class);

                        startActivity(io);
                        finish();

                    } else {

                        Toast.makeText(feedbackform.this, "error in connection", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });


    }
}