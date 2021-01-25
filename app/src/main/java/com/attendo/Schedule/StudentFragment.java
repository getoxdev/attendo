package com.attendo.Schedule;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
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
import com.attendo.Schedule.Adapters.WeekDayAdapter;
import com.attendo.data.model.schedule.DayOfWeek;
import com.attendo.Schedule.Interface.UpdateRecyclerView;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.data.model.schedule.SubjectDetails;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentFragment extends Fragment implements UpdateRecyclerView {


    private RecyclerView dayofWeekRecyclerView,subjectrecyclerView;
    private WeekDayAdapter weekDayAdapter;
    private List<DayOfWeek> dayList;
    private RoutineItemAdapter routineItemAdapter;
    private ArrayList<SubjectDetails> subjectRoutines  = new ArrayList();
    private FirebaseAuth mAuth;
    private DatabaseReference mReference;
    private String class_id;
    private ScheduleViewModel getScheduleViewModel;
    private AppPreferences preferences;

    private FloatingActionButton fabOpenMenu, fabPeople, fabNotice;
    private Boolean clicked = false;
    private Animation rotateOpen, rotateClose, toBottom, fromBottom;
    private SwipeRefreshLayout refreshLayout;
    private ContentLoadingProgressBar progressBar;
    private int positionDay = 0;

    private LottieAnimationView noClassLottieAnim;
    private TextView noClassTv;

    @Override
    public void onStart() {
        super.onStart();
        getClassId();
        getScheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Routine");

        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, true));
        setReenterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false));

        dayofWeekRecyclerView = view.findViewById(R.id.static_weekdays_recyclerview_student);
        subjectrecyclerView = view.findViewById(R.id.subjectsRecyclerView);
        noClassLottieAnim = view.findViewById(R.id.routine_lottie_student);
        noClassTv = view.findViewById(R.id.routine_txtView_student);
        fabOpenMenu = view.findViewById(R.id.fab_option_btn_stdnt);
        fabNotice = view.findViewById(R.id.fab_notice_stdnt);
        fabPeople = view.findViewById(R.id.fab_batchmates_stdnt);
        refreshLayout = view.findViewById(R.id.swipe_to_refresh_student);
        progressBar = view.findViewById(R.id.subject_progress_bar_student);

        preferences = AppPreferences.getInstance(getContext());
        //swipe to refresh function
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onSwipeDownToRefresh(positionDay);
            }
        });


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

        //loading animations for multiple FAB
        rotateOpen = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_open_anim );
        rotateClose = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_close_anim);
        toBottom = AnimationUtils.loadAnimation(getContext(), R.anim.to_bottom_anim);
        fromBottom = AnimationUtils.loadAnimation(getContext(), R.anim.from_bottom_anim);


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
                Fragment batchmatesFragment = new BatchmatesDetailsFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.container_frame, batchmatesFragment, "batch")
                        .commit();
            }
        });
        fabNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment noticeFragment = new NoticeFragmentStudent();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.container_frame, noticeFragment, "notice")
                        .commit();
            }
        });

        return view;
    }

    private void onSwipeDownToRefresh(int positionDay) {

                Log.d("Student", String.valueOf(positionDay) + "  : onSwipe");
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
        progressBar.hide();
        progressBar.animate();
        if(subjectRoutines == null){
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
            noClassLottieAnim.setVisibility(View.VISIBLE);
            noClassTv.setVisibility(View.VISIBLE);
        }else{
            routineItemAdapter = new RoutineItemAdapter(subjectRoutines, getContext(), this);
            noClassLottieAnim.setVisibility(View.INVISIBLE);
            noClassTv.setVisibility(View.INVISIBLE);
        }
        routineItemAdapter.notifyDataSetChanged();
        subjectrecyclerView.setAdapter(routineItemAdapter);
    }

    @Override
    public void sendPosition(int position) {
        positionDay = position;
        Log.d("Student", String.valueOf(positionDay) + "  : onSendPosition");
        Log.d("Student", String.valueOf(position) + "  : this is actual position");
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
            Log.d("Student", class_id);
            Log.d("Student", day);
            getScheduleViewModel.setScheduleGetResponse(class_id, day);
            getScheduleViewModel.getScheduleGetResponse().observe(getViewLifecycleOwner(), data->{
                if(data == null){
                    progressBar.hide();
                    progressBar.animate();
                    noClassLottieAnim.setVisibility(View.VISIBLE);
                    noClassTv.setVisibility(View.VISIBLE);
                }else{
                    if(data.getRequiredSchedule().size() == 0){
                        routineItemAdapter = new RoutineItemAdapter(data.getRequiredSchedule(), getContext(), this);
                        routineItemAdapter.notifyDataSetChanged();
                        subjectrecyclerView.setAdapter(routineItemAdapter);
                        noClassLottieAnim.setVisibility(View.VISIBLE);
                        noClassTv.setVisibility(View.VISIBLE);
                        progressBar.hide();
                        progressBar.animate();
                    }else{
                        noClassLottieAnim.setVisibility(View.INVISIBLE);
                        noClassTv.setVisibility(View.INVISIBLE);
                        routineItemAdapter = new RoutineItemAdapter(data.getRequiredSchedule(), getContext(), this);
                        routineItemAdapter.notifyDataSetChanged();
                        subjectrecyclerView.setAdapter(routineItemAdapter);
                        refreshLayout.setRefreshing(false);
                        progressBar.hide();
                        progressBar.animate();
                    }

                }
            });


        }
    }

    private void getClassId(){
        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference("Schedule");
        mReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    class_id = snapshot.child("Class_Id").getValue(String.class);
                    //Log.d("ClassIDMINE" , class_id);
                }else {
                    class_id = null;
                    // Log.d("ClassIDMINE" , class_id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Database Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

}