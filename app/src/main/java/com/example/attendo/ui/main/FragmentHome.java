package com.example.attendo.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.attendo.Fragment_Subject;
import com.example.attendo.R;
import com.example.attendo.ui.calendar.FragmentCalender;

public class FragmentHome extends Fragment {

    private FragmentCalender fragmentCalender;
    private Fragment_Subject fragment_subject;

    //@BindView(R.id.subjectcardview)
    CardView subjectCV,CalenderCv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentCalender = new FragmentCalender();
        fragment_subject = new Fragment_Subject();

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        subjectCV = view.findViewById(R.id.subjectcardview);
        CalenderCv = view.findViewById(R.id.calendercardview);

        subjectCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Bundle bundle = new Bundle();
               bundle.putString("key","");
               fragment_subject.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame,fragment_subject);
                fragmentTransaction.commit();
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