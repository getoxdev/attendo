package com.example.attendo.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.attendo.ui.sub.SubActivity;
import com.example.attendo.R;
import com.example.attendo.ui.calendar.FragmentCalender;

import butterknife.BindView;

public class FragmentHome extends Fragment {

    private FragmentCalender fragmentCalender;

    @BindView(R.id.subjectcardview)
    CardView subjectCV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentCalender = new FragmentCalender();

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        subjectCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SubActivity.class);
                startActivity(intent);
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Dashboard");
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}