package com.attendo.Schedule.CrViewPager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.savedstate.SavedStateRegistryOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.attendo.R;
import com.attendo.Schedule.AddSubjectDetailsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SaturdayCr extends Fragment {

    private FloatingActionButton fb;
    private AddSubjectDetailsFragment addSubjectDetailsFragment;

    public static SaturdayCr getInstance(){
        SaturdayCr saturdayCr = new SaturdayCr();
        return saturdayCr;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saturday_cr, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Saturday");

        addSubjectDetailsFragment = new AddSubjectDetailsFragment();
        fb = view.findViewById(R.id.Schedule_add_subject_Saturday);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("day","Saturday");
                AddSubjectDetailsFragment addSubjectDetailsFragment = new AddSubjectDetailsFragment();
                addSubjectDetailsFragment.setArguments(bundle);
                addSubjectDetailsFragment.show(getParentFragmentManager(),"Subject_Details");
            }
        });

        return  view;
    }
}