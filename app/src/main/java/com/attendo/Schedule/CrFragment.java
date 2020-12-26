package com.attendo.Schedule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.attendo.R;
import com.attendo.Schedule.Adapters.RoutineItemAdapter;
import com.attendo.Schedule.Adapters.WeekDayAdapter;
import com.attendo.Schedule.Model.DayOfWeek;
import com.attendo.Schedule.Model.SubjectRoutine;
import com.attendo.Schedule.dynamicRvInterface.LoadMore;

import java.util.ArrayList;
import java.util.List;

public class CrFragment extends Fragment {

    private RecyclerView dayofWeekRecyclerView;
    private WeekDayAdapter weekDayAdapter;
    private ArrayList<DayOfWeek> dayList;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

   private String mParam1;
    private String mParam2;

    public CrFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CrFragment newInstance(String param1, String param2) {
        CrFragment fragment = new CrFragment();
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
        View view  =inflater.inflate(R.layout.fragment_cr, container, false);


    return view;
    }


}