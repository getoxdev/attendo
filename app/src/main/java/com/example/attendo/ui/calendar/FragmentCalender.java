package com.example.attendo.ui.calendar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.attendo.R;

public class FragmentCalender extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_calender, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Calendar");
        return view;
    }
}