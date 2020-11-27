package com.attendo.ui.main.drawers;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.ui.main.BottomNavMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SchedulejoinFragment extends Fragment {

    @BindView(R.id.Code)
    TextView code;
    @BindView(R.id.leave)
    Button LEAVE;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SchedulejoinFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SchedulejoinFragment newInstance(String param1, String param2) {
        SchedulejoinFragment fragment = new SchedulejoinFragment();
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
        View view =  inflater.inflate(R.layout.fragment_schedulejoin, container, false);

        ButterKnife.bind(this,view);

        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule_Member");

        LEAVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(id).child("Schedule_Code").setValue("");
                databaseReference.child(id).child("Schedule_Join_As").setValue("");
                Toast.makeText(getActivity(),"You leave the Schedule",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), BottomNavMainActivity.class);
                startActivity(intent);
            }
        });

        return  view;
    }
}