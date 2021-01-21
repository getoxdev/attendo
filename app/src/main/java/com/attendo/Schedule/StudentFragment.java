package com.attendo.Schedule;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.attendo.Schedule.Interface.UpdateRecyclerView;
import com.attendo.data.model.SubjectDetails;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class StudentFragment extends Fragment implements UpdateRecyclerView {


    private RecyclerView dayofWeekRecyclerView,subjectrecyclerView;
    private WeekDayAdapter weekDayAdapter;
    private List<DayOfWeek> dayList;
    private RoutineItemAdapter routineItemAdapter;
    private ArrayList<SubjectDetails> subjectRoutines  = new ArrayList();
    private FirebaseAuth mAuth;
    private String class_id;
    private ScheduleViewModel getScheduleViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Routine");

        dayofWeekRecyclerView = view.findViewById(R.id.static_weekdays_recyclerview_student);
        subjectrecyclerView = view.findViewById(R.id.subjectsRecyclerView);

        mAuth = FirebaseAuth.getInstance();

        subjectrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        routineItemAdapter = new RoutineItemAdapter(subjectRoutines);
//        subjectrecyclerView.setAdapter(routineItemAdapter);

        dayList = new ArrayList<>();

        dayList.add(new DayOfWeek("SUN"));
        dayList.add(new DayOfWeek("MON"));
        dayList.add(new DayOfWeek("TUE"));
        dayList.add(new DayOfWeek("WED"));
        dayList.add(new DayOfWeek("THU"));
        dayList.add(new DayOfWeek("FRI"));
        dayList.add(new DayOfWeek("SAT"));

        weekDayAdapter = new WeekDayAdapter(getActivity(), dayList, getActivity(), this);

        dayofWeekRecyclerView.setAdapter(weekDayAdapter);
        setAdapterAccordingToPosition("sunday");

        return view;
    }



    @Override
    public void callback(int position, List<SubjectDetails> subjectRoutines) {
//        if(subjectRoutines == null){
//            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
//        }else{
//            routineItemAdapter = new RoutineItemAdapter();
//        }
//        routineItemAdapter.notifyDataSetChanged();
//        subjectrecyclerView.setAdapter(routineItemAdapter);
    }

    @Override
    public void sendPosition(int position) {
        switch (position){
            case 0:
                setAdapterAccordingToPosition("sunday");
                break;
            case 1:
                setAdapterAccordingToPosition("monday");
                break;
            case 2:
                setAdapterAccordingToPosition("tuesday");
                break;
            case 3:
                setAdapterAccordingToPosition("wednesday");
                break;
            case 4:
                setAdapterAccordingToPosition("thursday");
                break;
            case 5:
                setAdapterAccordingToPosition("friday");
                break;
            case 6:
                setAdapterAccordingToPosition("saturday");
                break;
        }

    }

    private void setAdapterAccordingToPosition(String day){
        if(class_id == null){
            Toast.makeText(getContext(), "Please wait", Toast.LENGTH_SHORT).show();

        }else{
            getScheduleViewModel.setScheduleGetResponse(class_id, day);
            getScheduleViewModel.getScheduleGetResponse().observe(getViewLifecycleOwner(), data->{
                if(data == null){
                    Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
                }else{
                    if(data.getRequiredSchedule().size() == 0){
                        routineItemAdapter = new RoutineItemAdapter(data.getRequiredSchedule(), getContext(), this);
                        routineItemAdapter.notifyDataSetChanged();
                        subjectrecyclerView.setAdapter(routineItemAdapter);
//                        noClassRoutineLottie.setVisibility(View.VISIBLE);
//                        noClassTextView.setVisibility(View.VISIBLE);
                    }else{
//                        noClassRoutineLottie.setVisibility(View.INVISIBLE);
//                        noClassTextView.setVisibility(View.INVISIBLE);
                        routineItemAdapter = new RoutineItemAdapter(data.getRequiredSchedule(), getContext(), this);
                        routineItemAdapter.notifyDataSetChanged();
                        subjectrecyclerView.setAdapter(routineItemAdapter);
                    }

                }
            });


        }
    }




}