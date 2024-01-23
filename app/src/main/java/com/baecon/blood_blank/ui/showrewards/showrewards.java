package com.baecon.blood_blank.ui.showrewards;

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

import com.baecon.blood_blank.R;
import com.baecon.blood_blank.UrlLinks;
import com.baecon.blood_blank.jSOnClassforData;
import com.baecon.blood_blank.ExampleAdapter4;
import com.baecon.blood_blank.ExampleItem4;
import com.baecon.blood_blank.login;
import com.baecon.blood_blank.ui.bloodrequirement.blood_reqirements;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class showrewards extends Fragment {

    EditText Searchtext;
    private ExampleAdapter4 adapter;
    ImageButton bt_mic;
    private List<ExampleItem4> exampleList;
    private List<ExampleItem4> examples;
    public static String usersession1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_showrewards, container, false);

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

        RecyclerView recyclerView = (RecyclerView) root.findViewById( R.id.RecyclerView5);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        this.adapter = new ExampleAdapter4(exampleList,getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(this.adapter);

        return root;




    }

    private void fillExampleList() {
        exampleList = new ArrayList();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String url = UrlLinks.getrewards;


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
                String rewards = String.valueOf(jsonArray.getJSONArray(i).getString(8));
                String mail = String.valueOf(jsonArray.getJSONArray(i).getString(6));
                usersession1=hospital;
                exampleList.add(new ExampleItem4(hospital,bloodgroup,rewards,mail));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /* access modifiers changed from: private */
    public void filterQuery(String text) {
        ArrayList<ExampleItem4> filterdNames = new ArrayList<>();
        for (ExampleItem4 s : exampleList) {
            if (s.getmText1().toLowerCase().contains(text) || s.getmText2().toLowerCase().contains(text)) {
                filterdNames.add(s);
            }
        }
        this.adapter.setFilter(filterdNames);
    }
}