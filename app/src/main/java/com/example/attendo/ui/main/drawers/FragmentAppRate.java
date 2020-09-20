package com.example.attendo.ui.main.drawers;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attendo.R;
import com.firebase.client.Firebase;

public class FragmentAppRate extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EditText feedback;
        Button btnFeedback;
        Firebase firebase;
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_app_rate, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Rate our App");
         feedback=view.findViewById(R.id.feedback);
        btnFeedback=view.findViewById(R.id.btnFeedback);
        Firebase.setAndroidContext(this.getActivity());
        String UniqueID_feed;
        UniqueID_feed = Settings.Secure.getString(getActivity().getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        firebase=new Firebase("https://amanfirstfirebase.firebaseio.com/users"+UniqueID_feed);
        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFeedback.setEnabled(true);
                final String msg = feedback.getText().toString().trim();
                Firebase child_msg = firebase.child("FeedBack");

                if (msg.isEmpty()) {
                    feedback.setError("this is a required field");
                    //btnFeedback.setEnabled(false);
                } else {
                    feedback.setError(null);
                    btnFeedback.setEnabled(true);
                    child_msg.setValue(msg);
                    Toast.makeText(getContext(), "Thank You", Toast.LENGTH_SHORT).show();
                }



            }
        });


        return  view;
    }













}
