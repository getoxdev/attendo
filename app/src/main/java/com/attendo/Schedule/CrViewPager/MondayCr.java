package com.attendo.Schedule.CrViewPager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.attendo.R;
import com.attendo.Schedule.AddSubjectDetailsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MondayCr extends Fragment {

    private AddSubjectDetailsFragment addSubjectDetailsFragment;
    private FloatingActionButton fb;

    public static MondayCr getInstance(){
        MondayCr mondayCr = new MondayCr();
        return mondayCr;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_monday_cr, container, false);

        addSubjectDetailsFragment = new AddSubjectDetailsFragment();

        fb = view.findViewById(R.id.Schedule_add_subject_Monday);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("day","Monday");
                AddSubjectDetailsFragment addSubjectDetailsFragment = new AddSubjectDetailsFragment();
                addSubjectDetailsFragment.setArguments(bundle);
                addSubjectDetailsFragment.show(getParentFragmentManager(),"Subject_Details");
            }
        });

        return  view;
    }
}