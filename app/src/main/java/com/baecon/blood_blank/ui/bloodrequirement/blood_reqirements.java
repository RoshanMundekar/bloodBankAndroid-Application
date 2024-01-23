package com.baecon.blood_blank.ui.bloodrequirement;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baecon.blood_blank.ExampleAdapter2;
import com.baecon.blood_blank.ExampleItem2;
import com.baecon.blood_blank.R;
import com.baecon.blood_blank.UrlLinks;
import com.baecon.blood_blank.jSOnClassforData;
import com.baecon.blood_blank.login;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class blood_reqirements extends Fragment {

    EditText Searchtext;
    private ExampleAdapter2 adapter;
    ImageButton bt_mic;
    private List<ExampleItem2> exampleList;
    private List<ExampleItem2> examples;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_blood_reqirements, container, false);

        fillExampleList();
        this.Searchtext = (EditText) root.findViewById(R.id.search_input);
        this.Searchtext.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                filterQuery(editable.toString());
            }
        });

        RecyclerView recyclerView = (RecyclerView) root.findViewById( R.id.RecyclerView3);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        this.adapter = new ExampleAdapter2(exampleList,getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(this.adapter);


        return root;
    }

    private void fillExampleList() {
        exampleList = new ArrayList();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String url = UrlLinks.getbloodnotification;


        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("donor", login.usersession));
        String result = null;
        try {
            result = jSOnClassforData.forCallingStringAndreturnSTring(url, nameValuePairs);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                String hospital = String.valueOf(jsonArray.getJSONArray(i).getString(0));
                String bloodgroup = String.valueOf(jsonArray.getJSONArray(i).getString(1));
                String input_details = String.valueOf(jsonArray.getJSONArray(i).getString(4));
                String location = String.valueOf(jsonArray.getJSONArray(i).getString(2));
                String input_time_confirm = String.valueOf(jsonArray.getJSONArray(i).getString(5));

                exampleList.add(new ExampleItem2(hospital,bloodgroup,input_details,location,input_time_confirm));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /* access modifiers changed from: private */
    public void filterQuery(String text) {
        ArrayList<ExampleItem2> filterdNames = new ArrayList<>();
        for (ExampleItem2 s : exampleList) {
            if (s.getmText1().toLowerCase().contains(text) || s.getmText2().toLowerCase().contains(text)) {
                filterdNames.add(s);
            }
        }
        this.adapter.setFilter(filterdNames);
    }

}