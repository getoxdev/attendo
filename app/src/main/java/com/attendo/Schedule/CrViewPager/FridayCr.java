package com.attendo.Schedule.CrViewPager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.attendo.R;
import com.attendo.Schedule.AddSubjectDetailsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FridayCr extends Fragment {

    private FloatingActionButton fb;
    private AddSubjectDetailsFragment addSubjectDetailsFragment;

    public static FridayCr getInstance(){
        FridayCr fridayCr = new FridayCr();
        return fridayCr;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friday_cr, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("friday");

        addSubjectDetailsFragment = new AddSubjectDetailsFragment();
        fb = view.findViewById(R.id.Schedule_add_subject_Friday);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("day","Friday");
                AddSubjectDetailsFragment addSubjectDetailsFragment = new AddSubjectDetailsFragment();
                addSubjectDetailsFragment.setArguments(bundle);
                addSubjectDetailsFragment.show(getParentFragmentManager(),"Subject_Details");
            }
        });

        return  view;
    }
}