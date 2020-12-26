package com.attendo.Schedule;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.attendo.R;
import com.attendo.data.database.SubDatabase;

import java.io.File;

public class MultipleRecyclerViewFragment extends Fragment {

    private Button cr,stu;
    private CrFragment crFragment;
    private StudentFragment studentFragment;


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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_multiple_recycler_view, container, false);

        crFragment = new CrFragment();
        studentFragment = new StudentFragment();

        cr = view.findViewById(R.id.CrSchedule);
        stu = view.findViewById(R.id.StudentSchedule);
        cr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(crFragment);
            }
        });
        stu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(studentFragment);
            }
        });




        return view;
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

}