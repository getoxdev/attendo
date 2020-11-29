package com.attendo.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.attendo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CrScheduleFragment extends Fragment {

    private TextView text;
    private FloatingActionButton fab;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CrScheduleFragment() {
        // Required empty public constructor
    }

   public static CrScheduleFragment newInstance(String param1, String param2) {
        CrScheduleFragment fragment = new CrScheduleFragment();
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
        View view =  inflater.inflate(R.layout.fragment_cr_schedule, container, false);


        text = view.findViewById(R.id.day_cr);
        String  message = getArguments().getString("message");
        text.setText(message);

        fab = view.findViewById(R.id.add_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetForSchedule bottomSheetForSchedule = new BottomSheetForSchedule();
                bottomSheetForSchedule.show(getChildFragmentManager(),"BottomSheet");
            }
        });

        return view;
    }
}