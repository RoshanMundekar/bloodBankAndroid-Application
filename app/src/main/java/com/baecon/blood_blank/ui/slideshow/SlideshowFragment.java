package com.baecon.blood_blank.ui.slideshow;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baecon.blood_blank.ExampleAdapter1;
import com.baecon.blood_blank.ExampleItem1;
import com.baecon.blood_blank.R;
import com.baecon.blood_blank.UrlLinks;
import com.baecon.blood_blank.databinding.FragmentSlideshowBinding;
import com.baecon.blood_blank.jSOnClassforData;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    EditText Searchtext;
    private ExampleAdapter1 adapter;
    ImageButton bt_mic;
    private List<ExampleItem1> exampleList;
    private List<ExampleItem1> examples;

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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

        RecyclerView recyclerView = (RecyclerView) root.findViewById( R.id.RecyclerView2);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        this.adapter = new ExampleAdapter1(exampleList,getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(this.adapter);


        return root;
    }

    private void fillExampleList() {
        exampleList = new ArrayList();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String url = UrlLinks.getupload_image;


        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

        String result = null;
        try {
            result = jSOnClassforData.forCallingStringAndreturnSTring(url, nameValuePairs);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {

                String image = String.valueOf(jsonArray.getJSONArray(i).getString(4));
                String donorName = String.valueOf(jsonArray.getJSONArray(i).getString(0));
                String donorContact = String.valueOf(jsonArray.getJSONArray(i).getString(2));
                String email = String.valueOf(jsonArray.getJSONArray(i).getString(1));

                String stUrl =UrlLinks.urlserverpython+"/static/"+image;

                Bitmap bitmap = null;
                try {
                    InputStream inputStream = new java.net.URL(stUrl).openStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                exampleList.add(new ExampleItem1(bitmap,donorName,donorContact,email));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /* access modifiers changed from: private */
    public void filterQuery(String text) {
        ArrayList<ExampleItem1> filterdNames = new ArrayList<>();
        for (ExampleItem1 s : exampleList) {
            if (s.getmText2().toLowerCase().contains(text) || s.getmText4().toLowerCase().contains(text)) {
                filterdNames.add(s);
            }
        }
        this.adapter.setFilter(filterdNames);
    }
}