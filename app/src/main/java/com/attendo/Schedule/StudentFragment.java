package com.attendo.Schedule;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.attendo.R;
import com.attendo.Schedule.Adapters.RoutineItemAdapter;
import com.attendo.Schedule.Adapters.RoutineItemAdapterCr;
import com.attendo.Schedule.Adapters.WeekDayAdapter;
import com.attendo.Schedule.Model.DayOfWeek;
import com.attendo.Schedule.Model.SubjectRoutine;
import com.attendo.Schedule.Interface.UpdateRecyclerView;
import com.attendo.data.model.SubjectDetails;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

    private FloatingActionButton fabOpenMenu, fabPeople, fabNotice;
    private Boolean clicked = false;
    private Animation rotateOpen, rotateClose, toBottom, fromBottom;
    private SwipeRefreshLayout refreshLayout;
    private int positionDay = 0;

    private LottieAnimationView noClassLottieAnim;
    private TextView noClassTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Routine");

        dayofWeekRecyclerView = view.findViewById(R.id.static_weekdays_recyclerview_student);
        subjectrecyclerView = view.findViewById(R.id.subjectsRecyclerView);
        noClassLottieAnim = view.findViewById(R.id.routine_lottie_student);
        noClassTv = view.findViewById(R.id.routine_txtView_student);
        fabOpenMenu = view.findViewById(R.id.fab_option_btn_stdnt);
        fabNotice = view.findViewById(R.id.fab_notice_stdnt);
        fabPeople = view.findViewById(R.id.fab_batchmates_stdnt);
        refreshLayout = view.findViewById(R.id.swipe_to_refresh_student);

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

        //loading animations for multiple FAB
        rotateOpen = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_open_anim );
        rotateClose = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_close_anim);
        toBottom = AnimationUtils.loadAnimation(getContext(), R.anim.to_bottom_anim);
        fromBottom = AnimationUtils.loadAnimation(getContext(), R.anim.from_bottom_anim);

        //swipe to refresh function
        onSwipeDownToRefresh(positionDay);

        fabOpenMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVisibility(clicked);
                setAnimation(clicked);
                setClickable(clicked);
                if(!clicked) clicked = true;
                else clicked = false;
            }
        });

        fabPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Batchmates clicked", Toast.LENGTH_SHORT).show();
            }
        });
        fabNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Notice Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void onSwipeDownToRefresh(int positionDay) {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                switch (positionDay){
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
        });
    }

    private void setVisibility(Boolean clicked){
        if(!clicked){
            fabNotice.setVisibility(View.VISIBLE);
            fabPeople.setVisibility(View.VISIBLE);
        }else{
            fabNotice.setVisibility(View.INVISIBLE);
            fabPeople.setVisibility(View.INVISIBLE);
        }
    }
    private void setAnimation(Boolean clicked){
        if(!clicked){
            fabPeople.startAnimation(fromBottom);
            fabNotice.startAnimation(fromBottom);
            fabOpenMenu.startAnimation(rotateOpen);
        }else{
            fabPeople.startAnimation(toBottom);
            fabNotice.startAnimation(toBottom);
            fabOpenMenu.startAnimation(rotateClose);
        }
    }

    private void setClickable(Boolean clicked){
        if(!clicked){
            fabNotice.setClickable(true);
            fabPeople.setClickable(true);
        }else{
            fabNotice.setClickable(false);
            fabPeople.setClickable(false);
        }
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
        positionDay = position;
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
                        noClassLottieAnim.setVisibility(View.VISIBLE);
                        noClassTv.setVisibility(View.VISIBLE);
                    }else{
                        noClassLottieAnim.setVisibility(View.INVISIBLE);
                        noClassTv.setVisibility(View.INVISIBLE);
                        routineItemAdapter = new RoutineItemAdapter(data.getRequiredSchedule(), getContext(), this);
                        routineItemAdapter.notifyDataSetChanged();
                        subjectrecyclerView.setAdapter(routineItemAdapter);
                        refreshLayout.setRefreshing(false);
                    }

                }
            });


        }
    }



}