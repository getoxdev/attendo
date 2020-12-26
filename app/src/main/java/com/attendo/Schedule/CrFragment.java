package com.attendo.Schedule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.Schedule.Adapters.RoutineItemAdapter;
import com.attendo.Schedule.Adapters.RoutineItemAdapterCr;
import com.attendo.Schedule.Adapters.WeekDayAdapter;
import com.attendo.Schedule.Model.DayOfWeek;
import com.attendo.Schedule.Model.SubjectRoutine;
import com.attendo.Schedule.dynamicRvInterface.LoadMore;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CrFragment extends Fragment implements LoadMore {

    private RecyclerView dayofWeekRecyclerView,subjectrecyclerView;
    private WeekDayAdapter weekDayAdapter;
    private ArrayList<DayOfWeek> dayList;
    private RoutineItemAdapterCr routineItemAdapter;
    private ArrayList<SubjectRoutine> subjectRoutines  = new ArrayList();
    private FloatingActionButton fb;

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

        fb = view.findViewById(R.id.Schedule_add_subject);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Add Subject details",Toast.LENGTH_SHORT).show();
            }
        });

        dayofWeekRecyclerView = view.findViewById(R.id.static_weekdays_cr);
        subjectrecyclerView = view.findViewById(R.id.subjectsRecyclerViewCr);

        subjectrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        routineItemAdapter = new RoutineItemAdapterCr(subjectRoutines);
        subjectrecyclerView.setAdapter(routineItemAdapter);

        dayList = new ArrayList<>();

        dayList.add(new DayOfWeek("MON"));
        dayList.add(new DayOfWeek("MON"));
        dayList.add(new DayOfWeek("TUE"));
        dayList.add(new DayOfWeek("WED"));
        dayList.add(new DayOfWeek("THU"));
        dayList.add(new DayOfWeek("FRI"));
        dayList.add(new DayOfWeek("SAT"));

        weekDayAdapter = new WeekDayAdapter(getActivity(), dayList, getActivity(), this::callback);

        dayofWeekRecyclerView.setAdapter(weekDayAdapter);

        return view;
    }

    @Override
    public void callback(int position, ArrayList<SubjectRoutine> subjectRoutines) {
        routineItemAdapter = new RoutineItemAdapterCr(subjectRoutines);
        routineItemAdapter.notifyDataSetChanged();
        subjectrecyclerView.setAdapter(routineItemAdapter);
    }

}