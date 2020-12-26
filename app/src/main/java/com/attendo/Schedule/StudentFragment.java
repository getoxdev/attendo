package com.attendo.Schedule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class StudentFragment extends Fragment implements LoadMore {


    private RecyclerView dayofWeekRecyclerView,subjectrecyclerView;
    private WeekDayAdapter weekDayAdapter;
    private List<DayOfWeek> dayList;
    private RoutineItemAdapter routineItemAdapter;
    private ArrayList<SubjectRoutine> subjectRoutines  = new ArrayList();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public StudentFragment() {
      }
    public static StudentFragment newInstance(String param1, String param2) {
        StudentFragment fragment = new StudentFragment();
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
        View view = inflater.inflate(R.layout.fragment_student, container, false);

        dayofWeekRecyclerView = view.findViewById(R.id.static_weekdays_recyclerview_student);
        subjectrecyclerView = view.findViewById(R.id.subjectsRecyclerView);

        subjectRoutines.add(new SubjectRoutine("Mathematics","9am-10am","Pankaj"));
        subjectRoutines.add(new SubjectRoutine("Analog Electronics","10am-11am","Anupal"));
        subjectRoutines.add(new SubjectRoutine("Physics","2pm-3pm","ABC"));
        subjectRoutines.add(new SubjectRoutine("Data Structure","4pm-5pm","Dalton"));
        subjectRoutines.add(new SubjectRoutine("Analog","5pm-6pm","Anupal"));
        subjectRoutines.add(new SubjectRoutine("Algorithm","6pm-7pm","Dalton"));

        subjectrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        routineItemAdapter = new RoutineItemAdapter(subjectRoutines);
        subjectrecyclerView.setAdapter(routineItemAdapter);

        dayList = new ArrayList<>();

        dayList.add(new DayOfWeek("SUN"));
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
        routineItemAdapter = new RoutineItemAdapter(subjectRoutines);
        routineItemAdapter.notifyDataSetChanged();
        subjectrecyclerView.setAdapter(routineItemAdapter);
    }

}