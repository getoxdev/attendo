package com.attendo.ui.main.drawers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.attendo.R;

import butterknife.BindView;

public class SchedulejoinFragment extends Fragment {

    @BindView(R.id.Code)
    TextView code;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SchedulejoinFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SchedulejoinFragment newInstance(String param1, String param2) {
        SchedulejoinFragment fragment = new SchedulejoinFragment();
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
        View view =  inflater.inflate(R.layout.fragment_schedulejoin, container, false);


        return  view;
    }
}