package com.baecon.blood_blank.ui.logout;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baecon.blood_blank.R;
import com.baecon.blood_blank.login;


public class logout extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_logout, container, false);

        Intent loginActivity = new Intent(getContext(), login.class);
        startActivity(loginActivity);

        return root;
    }
}