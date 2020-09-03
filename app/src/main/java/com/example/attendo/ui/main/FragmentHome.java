package com.example.attendo.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.attendo.ui.sub.SubjectActivity;
import com.example.attendo.R;
import com.example.attendo.ui.calendar.FragmentCalender;

import java.util.Calendar;

public class FragmentHome extends Fragment {

    private FragmentCalender fragmentCalender;

    //@BindView(R.id.subjectcardview)
    CardView subjectCV,CalenderCv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentCalender = new FragmentCalender();

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        subjectCV = view.findViewById(R.id.subjectcardview);
        CalenderCv = view.findViewById(R.id.calendercardview);

        subjectCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SubjectActivity.class);
                startActivity(intent);
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Dashboard");

        CalenderCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(fragmentCalender);
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }



}