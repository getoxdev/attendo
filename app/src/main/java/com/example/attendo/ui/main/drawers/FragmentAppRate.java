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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAppRate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAppRate extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentAppRate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAppRate.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAppRate newInstance(String param1, String param2) {
        FragmentAppRate fragment = new FragmentAppRate();
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
