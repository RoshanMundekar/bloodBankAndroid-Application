package com.baecon.blood_blank;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.baecon.blood_blank.ui.blooddoner.blooddoner;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class bookslot extends AppCompatActivity {
    Button  reg;

    EditText ed1,ed3 ,ed4,ed5,ed7;

    Spinner ed2,ed6;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookslot);
        getSupportActionBar().hide();
        String bloodgroup=getIntent().getStringExtra("bloodgroup");
        String emailid=getIntent().getStringExtra("emailid");

        reg = findViewById(R.id.button_register);


        ed1 = findViewById(R.id.input_fullName);
        ed4 = findViewById(R.id.input_details);
        ed5 = findViewById(R.id.input_time_confirm);
        ed7 = findViewById(R.id.mail);




        ed3 = findViewById(R.id.inputAddress);
        ed2 = findViewById(R.id.inputBloodGroup);
        ed6 = findViewById(R.id.inputDivision);

//        ed2.setTextDirection(Integer.parseInt(bloodgroup));
        ed7.setText(emailid);





        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String hospital = ed1.getText().toString();
                String bloodgroup = ed2.getSelectedItem().toString();
                String address = ed3.getText().toString();
                String location = ed6.getSelectedItem().toString();
                String input_details = ed4.getText().toString();
                String input_time_confirm = ed5.getText().toString();
                String email = ed7.getText().toString();


                if(hospital.equals("") || bloodgroup.equals("")|| address.equals("")|| input_time_confirm.equals("")|| email.equals("")|| location.equals("")){
                    Toast.makeText(bookslot.this, "Please fill details", Toast.LENGTH_SHORT).show();
                }


                else {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String url = UrlLinks.bloodnotification;

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);

                    nameValuePairs.add(new BasicNameValuePair("hospital", hospital));
                    nameValuePairs.add(new BasicNameValuePair("bloodgroup", bloodgroup));
                    nameValuePairs.add(new BasicNameValuePair("address", address));
                    nameValuePairs.add(new BasicNameValuePair("location", location));
                    nameValuePairs.add(new BasicNameValuePair("input_details", input_details));
                    nameValuePairs.add(new BasicNameValuePair("input_time_confirm", input_time_confirm));
                    nameValuePairs.add(new BasicNameValuePair("email", email));
                    nameValuePairs.add(new BasicNameValuePair("username", blooddoner.donor));

                    String result = null;
                    try {
                        result = jSOnClassforData.forCallingStringAndreturnSTring(url,nameValuePairs);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (result.equals("success")) {

                        Toast.makeText(bookslot.this, " Successfully sent donor notification ", Toast.LENGTH_SHORT).show();

                        ed1.setText("");
                        ed4.setText("");
                        ed5.setText("");
                        ed7.setText("");

                        Intent io = new Intent(bookslot.this, ngoactive.class);

                        startActivity(io);
                        finish();

                    } else {

                        Toast.makeText(bookslot.this, "error in connection", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });






    }
}