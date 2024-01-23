package com.baecon.blood_blank.ui.home;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.baecon.blood_blank.R;
import com.baecon.blood_blank.UrlLinks;
import com.baecon.blood_blank.databinding.FragmentHomeBinding;
import com.baecon.blood_blank.jSOnClassforData;
import com.baecon.blood_blank.login;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import kotlin.text.Regex;

public class HomeFragment extends Fragment {
    Spinner  gender,bloodgroup,Division;
    EditText input_userEmail,input_uid,contact,address,input_fullName;
    Button button_register;
    private FragmentHomeBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        input_fullName = root.findViewById(R.id.input_fullName);
        input_userEmail = root.findViewById(R.id.input_userEmail);
        input_uid =root.findViewById(R.id.input_uid);

        contact =root.findViewById(R.id.inputMobile);
        address =root.findViewById(R.id.inputAddress);


        gender =root.findViewById(R.id.gender);
        bloodgroup =root.findViewById(R.id.inputBloodGroup);
        Division =root.findViewById(R.id.inputDivision);




        button_register =root.findViewById(R.id.button_register);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String username = input_fullName.getText().toString();
                String email = input_userEmail.getText().toString();
                String UID = input_uid.getText().toString();

                String contact1 = contact.getText().toString();
                String address1 = address.getText().toString();

                String gender1 = gender.getSelectedItem().toString();
                String bloodgroup1 = bloodgroup.getSelectedItem().toString();
                String Division1 = Division.getSelectedItem().toString();



                Pattern pattern = Patterns.EMAIL_ADDRESS;

                String regex ="^[0-9]{10}$";
                Pattern pattern1 = Pattern.compile(regex);

                String regex1 ="^[0-9]{12}$";
                Pattern pattern2 = Pattern.compile(regex1);

                if(username.equals("") || email.equals("")|| UID.equals("")|| contact1.equals("")|| address1.equals("")|| bloodgroup1.equals("")){
                    Toast.makeText(getActivity(), "Please fill in details.", Toast.LENGTH_SHORT).show();
                }
                else if(!pattern.matcher(email).matches()){
                    Toast.makeText(getActivity(), "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                }
                else if(!pattern1.matcher(contact1).matches()){
                    Toast.makeText(getActivity(), "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                }
                else if(!pattern2.matcher(UID).matches()){
                    Toast.makeText(getActivity(), "Please enter a valid UID number.", Toast.LENGTH_SHORT).show();
                }
                else {

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String url = UrlLinks.adddetails;

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
                    nameValuePairs.add(new BasicNameValuePair("email1", login.usersession));
                    nameValuePairs.add(new BasicNameValuePair("username", username));
                    nameValuePairs.add(new BasicNameValuePair("email", email));
                    nameValuePairs.add(new BasicNameValuePair("UID", UID));

                    nameValuePairs.add(new BasicNameValuePair("contact1", contact1));
                    nameValuePairs.add(new BasicNameValuePair("address1", address1));
                    nameValuePairs.add(new BasicNameValuePair("gender1", gender1));
                    nameValuePairs.add(new BasicNameValuePair("bloodgroup1", bloodgroup1));
                    nameValuePairs.add(new BasicNameValuePair("Division1", Division1));


                    String result = null;
                    try {
                        result = jSOnClassforData.forCallingStringAndreturnSTring(url,nameValuePairs);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (result.equals("success")) {

                        Toast.makeText(getActivity(), "slot booking User successfully added", Toast.LENGTH_SHORT).show();
                        input_fullName.setText("");
                        input_uid.setText("");
                        input_userEmail.setText("");
                        contact.setText("");
                        address.setText("");




                    }
                }

            }

        });








        return root;
    }



}