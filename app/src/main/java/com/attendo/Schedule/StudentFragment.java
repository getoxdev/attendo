package com.attendo.Schedule;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.attendo.data.model.schedule.FcmToken;
import com.attendo.data.model.schedule.SubjectDetails;
import com.attendo.ui.main.BottomNavMainActivity;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentFragment extends Fragment implements UpdateRecyclerView {


    private RecyclerView dayofWeekRecyclerView,subjectrecyclerView;
    private WeekDayAdapter weekDayAdapter;
    private List<DayOfWeek> dayList;
    private RoutineItemAdapter routineItemAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mReference;
    private ScheduleViewModel getScheduleViewModel;
    private FirebaseScheduleViewModel firebaseScheduleViewModel;
    private AppPreferences preferences;
    private LottieAnimationView searchingLottie;

    private FloatingActionButton fabOpenMenu, fabPeople, fabNotice;
    private Boolean clicked = false;
    private Animation rotateOpen, rotateClose, toBottom, fromBottom;
    private SwipeRefreshLayout refreshLayout;
    private ContentLoadingProgressBar progressBar;
    private int positionDay = 0;

    private LottieAnimationView noClassLottieAnim;
    private TextView noClassTv;
    private  String class_id;

    @Override
    public void onStart() {
        super.onStart();
        getClassId();
        getScheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        firebaseScheduleViewModel = new ViewModelProvider(this).get(FirebaseScheduleViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Routine");
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(View.VISIBLE);

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
        searchingLottie = view.findViewById(R.id.searching_routine_student);

        preferences = AppPreferences.getInstance(getContext());
        //swipe to refresh function
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onSwipeDownToRefresh(positionDay);
            }
        });

        getClassId();
        getScheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        firebaseScheduleViewModel = new ViewModelProvider(this).get(FirebaseScheduleViewModel.class);
        mAuth = FirebaseAuth.getInstance();
        subjectrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
                transaction.replace(R.id.container_frame, batchmatesFragment, "batch");
                transaction.addToBackStack(null).commit();
            }
        });
        fabNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment noticeFragment = new NoticeFragmentStudent();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.container_frame, noticeFragment, "notice");
                transaction.addToBackStack(null).commit();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkFCM();
    }

//    private void checkFCM()
//    {
//        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>()
//        {
//            @Override
//            public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                if (!task.isSuccessful()) {
//                    return;
//                }
//
//                String studentFCM = task.getResult().getToken();
//                Log.e("New FCM",studentFCM);
//
//                if(studentFCM!=null)
//                {
//                    if(preferences.RetrieveFcm()!=null)
//                    {
//                        if(preferences.RetrieveFcm().equals(studentFCM))
//                            Log.e("checkFCM: ", "Old");
//                        else
//                        {
//                            Log.e("checkFCM: ", "New");
//                            UpdateApiFcmStudent(studentFCM);
//                        }
//                    }
//                    else
//                    {
//                        Log.e("checkFCM: ", "New");
//                        UpdateApiFcmStudent(studentFCM);
//                    }
//                }
//                else
//                {
//                    Log.e("FCM is","null");
//                }
//            }
//        });
//    }

    private void checkFCM() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(getActivity(), new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    return;
                }

                String studentFCM = task.getResult();
                Log.e("New FCM",studentFCM);

                if(studentFCM!=null)
                {
                    if(preferences.RetrieveFcm()!=null)
                    {
                        if(preferences.RetrieveFcm().equals(studentFCM))
                            Log.e("checkFCM: ", "Old");
                        else
                        {
                            Log.e("checkFCM: ", "New");
                            UpdateApiFcmStudent(studentFCM);
                        }
                    }
                    else
                    {
                        Log.e("checkFCM: ", "New");
                        UpdateApiFcmStudent(studentFCM);
                    }
                }
                else
                {
                    Log.e("FCM is","null");
                }
            }
        });
    }

    private void UpdateApiFcmStudent(String FCM)
    {
        String email = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
        FcmToken fcmToken = new FcmToken(email,FCM);
        getScheduleViewModel.updateFcm(fcmToken);
        getScheduleViewModel.updateFcmResponse().observe(getActivity(), data -> {
            if (data == null) {
                Log.e("FCMApiCall", "Failed");
            } else {
                Log.e("FCMApiCall", "successFull");
                firebaseScheduleViewModel.AddFcmCode(FCM);
                preferences.AddFcm(FCM);
            }
        });
    }

    private void onSwipeDownToRefresh(int positionDay)
    {
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
        searchingLottie.setVisibility(View.INVISIBLE);
        progressBar.hide();
        progressBar.animate();
        if(subjectRoutines == null){
            Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
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
        searchingLottie.setVisibility(View.VISIBLE);
        progressBar.show();
        progressBar.animate();
        if(preferences.RetrieveClassId() == null){
            Toast.makeText(getActivity(), "Please wait", Toast.LENGTH_SHORT).show();

        }else{
            Log.d("Student", day);
            getScheduleViewModel.setScheduleGetResponse(preferences.RetrieveClassId(), day);
            getScheduleViewModel.getScheduleGetResponse().observe(getViewLifecycleOwner(), data->{
                if(data == null){
                    searchingLottie.setVisibility(View.INVISIBLE);
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
                        searchingLottie.setVisibility(View.INVISIBLE);
                    }else{
                        noClassLottieAnim.setVisibility(View.INVISIBLE);
                        noClassTv.setVisibility(View.INVISIBLE);
                        routineItemAdapter = new RoutineItemAdapter(data.getRequiredSchedule(), getContext(), this);
                        routineItemAdapter.notifyDataSetChanged();
                        subjectrecyclerView.setAdapter(routineItemAdapter);
                        refreshLayout.setRefreshing(false);
                        progressBar.hide();
                        progressBar.animate();
                        searchingLottie.setVisibility(View.INVISIBLE);
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
            }
        });
    }

}