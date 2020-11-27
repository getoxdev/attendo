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

public class ScheduleCreateFragment extends Fragment {

    @BindView(R.id.Code)
    TextView CoDe;
    @BindView(R.id.delete)
    Button DELETE;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScheduleCreateFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ScheduleCreateFragment newInstance(String param1, String param2) {
        ScheduleCreateFragment fragment = new ScheduleCreateFragment();
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_schedule_create, container, false);

        ButterKnife.bind(this,view);
        Bundle bundle = this.getArguments();
        String code = bundle.getString("Code");
        CoDe.setText(code);

        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule_Member");

        DELETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(id).child("Schedule_Code").setValue("");
                databaseReference.child(id).child("Schedule_Join_As").setValue("");
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Schedule").child(code);
                ref.removeValue();
                Toast.makeText(getActivity(),"You Have Deleted the Schedule ",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), BottomNavMainActivity.class);
                startActivity(intent);
            }
        });

        return  view;
    }
}