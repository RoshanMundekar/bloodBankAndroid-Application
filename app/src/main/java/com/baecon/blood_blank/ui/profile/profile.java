package com.baecon.blood_blank.ui.profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baecon.blood_blank.ExampleItem1;
import com.baecon.blood_blank.R;
import com.baecon.blood_blank.UrlLinks;
import com.baecon.blood_blank.jSOnClassforData;
import com.baecon.blood_blank.login;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class profile extends Fragment {

    TextView username,email,mobile,password,status;
    ImageView img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);


        username = root.findViewById(R.id.profileName);
        email = root.findViewById(R.id.profileEmail);
        mobile = root.findViewById(R.id.profiledepartment);
        password = root.findViewById(R.id.classname);
        status = root.findViewById(R.id.phone);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String url = UrlLinks.profile;


        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("email", login.usersession));

        String result = null;
        try {
            result = jSOnClassforData.forCallingStringAndreturnSTring(url, nameValuePairs);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                String username1 = String.valueOf(jsonArray.getJSONArray(i).getString(0));
                String email1 = String.valueOf(jsonArray.getJSONArray(i).getString(1));
                String mobile1 = String.valueOf(jsonArray.getJSONArray(i).getString(3));
                String password1 = String.valueOf(jsonArray.getJSONArray(i).getString(2));
                String status1 = String.valueOf(jsonArray.getJSONArray(i).getString(5));




                username.setText(username1+" ");
                email.setText(email1+" ");
                mobile.setText(mobile1+" ");
                password.setText(password1+" ");
                status.setText(status1+" ");




            }
        } catch (JSONException e) {
            e.printStackTrace();



        }


        return root;
    }

}