package com.example.attendo.ui.main.drawers;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.attendo.R;


public class FragmentBug extends Fragment {


   private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

   private String mParam1;
    private String mParam2;

    public FragmentBug() {
        // Required empty public constructor
    }

  public static FragmentBug newInstance(String param1, String param2) {
        FragmentBug fragment = new FragmentBug();
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
        View view =  inflater.inflate(R.layout.fragment_bug, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Report Bug");





        return view;
    }
}
