package com.attendo.ui.main.drawers;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.ui.auth.AuthenticationActivity;
import com.attendo.ui.main.BottomNavMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleFragment extends Fragment {

    @BindView(R.id.AsStudent)
    Button student;
    @BindView(R.id.creator)
    Button Cr;
    @BindView(R.id.code)
    EditText Code;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private SchedulejoinFragment schedulejoinFragment;
    private ScheduleCreateFragment scheduleCreateFragment;
    int found = 0;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public ScheduleFragment() {
        // Required empty public constructor
    }
 public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
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
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        ButterKnife.bind(this,view);
        schedulejoinFragment = new SchedulejoinFragment();
        scheduleCreateFragment = new ScheduleCreateFragment();

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule_Member");
        String userId = mAuth.getCurrentUser().getUid();
        if(userId == null){
            Toast.makeText(getActivity(),"Please Login again for security reason",Toast.LENGTH_LONG).show();
            mAuth.signOut();
            Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

            student.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String code = Code.getText().toString().trim();
                    if(code.isEmpty()){
                        Toast.makeText(getActivity(),"Please enter Code",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        StudentCheck(code);
                        // databaseReference.child(userId).child("Schedule_Code").setValue(code);
                        // databaseReference.child(userId).child("Schedule_Join_As").setValue("Student");
                        // Toast.makeText(getActivity(),"You enter in a new Class Schedule",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            Cr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String code = Code.getText().toString().trim();
                    if(code.isEmpty()){
                        Toast.makeText(getActivity(),"Please enter Code",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        CrCheck(code);
                       // databaseReference.child(userId).child("Schedule_Code").setValue(code);
                       // databaseReference.child(userId).child("Schedule_Join_As").setValue("Cr");
                       // Toast.makeText(getActivity(),"You created new Class Schedule",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        return  view;
    }

    private void StudentCheck(String code) {
        String id = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Schedule");
        ref.orderByKey().equalTo(code).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && found==0) {
                    Toast.makeText(getActivity(), "You have joined Schudule with Code : " + code, Toast.LENGTH_SHORT).show();
                    databaseReference.child(id).child("Schedule_Code").setValue(code);
                    databaseReference.child(id).child("Schedule_Join_As").setValue("Student");
                    setFragment(schedulejoinFragment);
                }
                else {
                    Toast.makeText(getActivity(), "This code doesn't exit", Toast.LENGTH_SHORT).show();
                    found = 1;
                }
                found = 0;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void CrCheck(String code) {
        String id = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Schedule");
        ref.orderByKey().equalTo(code).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                } else {
                    Toast.makeText(getActivity(), "You have created Schudule with Code : " + code, Toast.LENGTH_SHORT).show();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Schedule");
                    reference.child(code).child("Cr_Name").setValue(id);
                    databaseReference.child(id).child("Schedule_Code").setValue(code);
                    databaseReference.child(id).child("Schedule_Join_As").setValue("CR");
                    found = 1;
                    setFragment(scheduleCreateFragment);
                }
                if(found == 0)
                    Toast.makeText(getActivity(),"Please enter unique code it is already registered",Toast.LENGTH_SHORT).show();
                found=0;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.commit();
    }

}