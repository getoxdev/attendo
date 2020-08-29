package com.example.attendo.ui.main.drawers;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attendo.R;
import com.firebase.client.Firebase;


public class FragmentBug extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FragmentBug() {
        // Required empty public constructor
    }

    public static FragmentBug newInstance(String param1, String param2) {
        FragmentBug fragment = new FragmentBug();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EditText msgdata;
        Button send, details;
        Firebase firebase;
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_bug, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Report Bug");

        msgdata = view.findViewById(R.id.msgData);
        send = view.findViewById(R.id.btn_send);
        details = view.findViewById(R.id.btn_details);
        Firebase.setAndroidContext(this.getActivity());
        String UniqueID;
        UniqueID = Settings.Secure.getString(getActivity().getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        firebase=new Firebase("https://amanfirstfirebase.firebaseio.com/users"+UniqueID);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details.setEnabled(true);


                final String msg = msgdata.getText().toString().trim();
                Firebase child_msg = firebase.child("Report_Bug");

                if (msg.isEmpty()) {
                    msgdata.setError("this is a required field");
                    send.setEnabled(false);
                } else {
                    msgdata.setError(null);
                    send.setEnabled(true);
                    child_msg.setValue(msg);
                    Toast.makeText(getContext(), "Your data is sent to server", Toast.LENGTH_SHORT).show();
                }

                details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(getContext())
                                .setTitle("Sent Details :")
                                .setMessage("" + msg).show();

                    }
                });
            }
        });
        return view;
    }
}
