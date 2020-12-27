package com.attendo.Schedule;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.attendo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MultipleRecyclerViewFragment extends Fragment {

    private Button cr,stu;
    private CrFragment crFragment;
    private StudentFragment studentFragment;
    private EditText code;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MultipleRecyclerViewFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static MultipleRecyclerViewFragment newInstance(String param1, String param2) {
        MultipleRecyclerViewFragment fragment = new MultipleRecyclerViewFragment();
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
        View view = inflater.inflate(R.layout.fragment_multiple_recycler_view, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Routine");

        code = view.findViewById(R.id.schedule_code);
        crFragment = new CrFragment();
        studentFragment = new StudentFragment();

        databaseReference = FirebaseDatabase.getInstance().getReference("data");
        mAuth = FirebaseAuth.getInstance();

        String userId = mAuth.getCurrentUser().getUid();


        cr = view.findViewById(R.id.CrSchedule);
        stu = view.findViewById(R.id.StudentSchedule);
        cr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CODE = code.getText().toString();
                if(CODE.isEmpty()){
                    Toast.makeText(getActivity(),"Please Enter Schedule Code",Toast.LENGTH_SHORT).show();
                }
                else {
                    CreateSchedule();
                }
            }
        });
        stu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CODE = code.getText().toString();
                if(CODE.isEmpty()){
                    Toast.makeText(getActivity(),"Please Enter Schedule Code",Toast.LENGTH_SHORT).show();
                }
                else {
                    JoinSchedule();
                }
            }
        });

        return view;
    }

    private void JoinSchedule() {

        setFragment(studentFragment);
    }

    private void CreateSchedule() {

        setFragment(crFragment);
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

}