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
import com.attendo.Schedule.Adapters.RoutineItemAdapterCr;
import com.attendo.Schedule.Adapters.WeekDayAdapter;
import com.attendo.data.model.schedule.DayOfWeek;
import com.attendo.Schedule.Interface.UpdateRecyclerView;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.data.model.schedule.FcmToken;
import com.attendo.data.model.schedule.SubjectDetails;
import com.attendo.ui.main.BottomNavMainActivity;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.attendo.viewmodel.ScheduleViewModel;
//import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CrFragment extends Fragment implements UpdateRecyclerView,RoutineItemAdapterCr.OnCardClick{

    private RecyclerView dayofWeekRecyclerView,subjectrecyclerView;
    private WeekDayAdapter weekDayAdapter;
    private ArrayList<DayOfWeek> dayList;
    private RoutineItemAdapterCr routineItemAdapter;
    private FloatingActionButton fabOpenMenu, fabAddSubject, fabNotice, fabBatchmates;
    private ScheduleViewModel getScheduleViewModel;
    private FirebaseScheduleViewModel firebaseScheduleViewModel;
    private NoticeFragment noticeFragment;

    //firebase references
    private DatabaseReference mReference;
    private FirebaseAuth mAuth;
    private String class_id;
    private AppPreferences appPreferences;
    private Boolean clicked = false;
    private Animation rotateOpen, rotateClose, toBottom, fromBottom;
    private SwipeRefreshLayout refreshLayout;
    private ContentLoadingProgressBar progressBar;
    private LottieAnimationView searchingLottie;

    //position
    private int positionDay = 0;

    LottieAnimationView noClassRoutineLottie;
    TextView noClassTextView;

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
        View view  =inflater.inflate(R.layout.fragment_cr, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Routine");
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(View.VISIBLE);


        noticeFragment = new NoticeFragment();
        fabOpenMenu = view.findViewById(R.id.fab_open_menu_cr);
        fabAddSubject = view.findViewById(R.id.fab_add_cr);
        fabBatchmates = view.findViewById(R.id.fab_batchmates_cr);
        fabNotice = view.findViewById(R.id.fab_notice_cr);
        noClassRoutineLottie = view.findViewById(R.id.routine_lottie);
        noClassTextView = view.findViewById(R.id.routine_txtView);
        refreshLayout = view.findViewById(R.id.swipe_to_refresh_cr);
        progressBar = view.findViewById(R.id.subject_progress_bar_cr);
        searchingLottie = view.findViewById(R.id.searching_routine_cr);


        appPreferences = new AppPreferences(getActivity());
        //loading animations for multiple FAB
        rotateOpen = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_open_anim );
        rotateClose = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_close_anim);
        toBottom = AnimationUtils.loadAnimation(getContext(), R.anim.to_bottom_anim);
        fromBottom = AnimationUtils.loadAnimation(getContext(), R.anim.from_bottom_anim);

        //set on refresh listener
        Log.d("schedule", appPreferences.RetrieveClassId() + "   : Class Id");


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onSwipeDownToRefresh(positionDay);
            }
        });


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


        fabAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSubjectDetailsFragment addSubjectDetailsFragment = AddSubjectDetailsFragment.newInstance(CrFragment.this, positionDay);
                addSubjectDetailsFragment.show(getParentFragmentManager(),"Subject_Details");
            }
        });

        fabBatchmates.setOnClickListener(new View.OnClickListener() {
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
                Fragment noticeFragment = new NoticeFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.container_frame, noticeFragment, "notice");
                transaction.addToBackStack(null).commit();
            }
        });

        dayofWeekRecyclerView = view.findViewById(R.id.static_weekdays_cr);
        subjectrecyclerView = view.findViewById(R.id.subjectsRecyclerViewCr);

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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkFCM();
    }

    private void checkFCM()
    {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>()
        {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    return;
                }

                String crFCM = task.getResult().getToken();
                Log.e("New FCM",crFCM);

                if(crFCM!=null)
                {
                    if(appPreferences.RetrieveFcm()!=null)
                    {
                        if(appPreferences.RetrieveFcm().equals(crFCM))
                            Log.e("checkFCM: ", "Old");
                        else
                        {
                            Log.e("checkFCM: ", "New");
                            UpdateApiFcmCr(crFCM);
                        }
                    }
                    else
                    {
                        Log.e("checkFCM: ", "New");
                        UpdateApiFcmCr(crFCM);
                    }
                }
                else
                {
                    Log.e("FCM is","null");
                }
            }
        });
    }

    private void UpdateApiFcmCr(String FCM)
    {
        String email = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
        FcmToken fcmToken = new FcmToken(email,FCM);
        getScheduleViewModel.updateFcm(fcmToken);
        getScheduleViewModel.updateFcmResponse().observe(getActivity(), data -> {
            if (data == null) {
                Toast.makeText(getActivity(),"Something went wrong please try again later",Toast.LENGTH_SHORT).show();
                Log.e("FCMApiCall", "Failed");
            } else {
                Log.e("FCMApiCall", "successFull");
                firebaseScheduleViewModel.AddFcmCode(FCM);
                appPreferences.AddFcm(FCM);
            }
        });
    }

    private void onSwipeDownToRefresh(int positionDay)
    {
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


    //fab button configuration
    private void setVisibility(Boolean clicked){
        if(!clicked){
            fabNotice.setVisibility(View.VISIBLE);
            fabBatchmates.setVisibility(View.VISIBLE);
            fabAddSubject.setVisibility(View.VISIBLE);
        }else{
            fabNotice.setVisibility(View.INVISIBLE);
            fabBatchmates.setVisibility(View.INVISIBLE);
            fabAddSubject.setVisibility(View.INVISIBLE);
        }
    }

    private void setAnimation(Boolean clicked){
        if(!clicked){
            fabBatchmates.startAnimation(fromBottom);
            fabNotice.startAnimation(fromBottom);
            fabAddSubject.startAnimation(fromBottom);
            fabOpenMenu.startAnimation(rotateOpen);
        }else{
            fabBatchmates.startAnimation(toBottom);
            fabNotice.startAnimation(toBottom);
            fabAddSubject.startAnimation(toBottom);
            fabOpenMenu.startAnimation(rotateClose);
        }
    }

    private void setClickable(Boolean clicked){
        if(!clicked){
            fabNotice.setClickable(true);
            fabBatchmates.setClickable(true);
            fabAddSubject.setClickable(true);
        }else{
            fabNotice.setClickable(false);
            fabBatchmates.setClickable(false);
            fabAddSubject.setClickable(false);
        }
    }


    @Override
    public void callback(int position, List<SubjectDetails> subjectRoutines) {
        if(subjectRoutines == null){
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
            noClassRoutineLottie.setVisibility(View.VISIBLE);
            noClassTextView.setVisibility(View.VISIBLE);
            progressBar.animate();
            progressBar.hide();
            searchingLottie.setVisibility(View.INVISIBLE);

        }else{
            searchingLottie.setVisibility(View.INVISIBLE);
            progressBar.animate();
            progressBar.hide();
            noClassRoutineLottie.setVisibility(View.INVISIBLE);
            noClassTextView.setVisibility(View.INVISIBLE);
            routineItemAdapter = new RoutineItemAdapterCr(getActivity(),subjectRoutines,getActivity(),this,this);
        }
        routineItemAdapter.notifyDataSetChanged();
        subjectrecyclerView.setAdapter(routineItemAdapter);
    }

    @Override
    public void sendPosition(int position) {
        /**here problem is that position is null untill interface is called so we have use
        an if and else statement */
        positionDay = position;
        Log.d("Update", String.valueOf(positionDay));
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
                //Toast.makeText(getActivity(), "Database Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapterAccordingToPosition(String day){
        searchingLottie.setVisibility(View.VISIBLE);
        progressBar.show();
        progressBar.animate();
        if(appPreferences.RetrieveClassId() == null){
            Toast.makeText(getContext(), "Please wait", Toast.LENGTH_SHORT).show();

        }else{
            getScheduleViewModel.setScheduleGetResponse(appPreferences.RetrieveClassId(), day);
            Log.e("schedule99",appPreferences.RetrieveClassScheduleId());
            getScheduleViewModel.getScheduleGetResponse().observe(this, data->{
                if(data == null){
                    searchingLottie.setVisibility(View.INVISIBLE);
                    progressBar.hide();
                    progressBar.animate();
                    noClassRoutineLottie.setVisibility(View.VISIBLE);
                    noClassTextView.setVisibility(View.VISIBLE);
                }else{
                    if(data.getRequiredSchedule().size() == 0){
                        routineItemAdapter = new RoutineItemAdapterCr(getActivity(),data.getRequiredSchedule(),getActivity(),this,this);
                        routineItemAdapter.notifyDataSetChanged();
                        searchingLottie.setVisibility(View.INVISIBLE);
                        progressBar.hide();
                        progressBar.animate();
                        subjectrecyclerView.setAdapter(routineItemAdapter);
                        noClassRoutineLottie.setVisibility(View.VISIBLE);
                        noClassTextView.setVisibility(View.VISIBLE);
                    }else{
                        noClassRoutineLottie.setVisibility(View.INVISIBLE);
                        noClassTextView.setVisibility(View.INVISIBLE);
                        routineItemAdapter = new RoutineItemAdapterCr(getActivity(),data.getRequiredSchedule(),getActivity(),this,this);
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

    @Override
    public void onDeleteClick(int position, SubjectDetails subjectDetails) {
        DeleteFragment delete_fragment = DeleteFragment.newInstance(subjectDetails.get_id(), positionDay, this);
        delete_fragment.show(getParentFragmentManager(), "Delete");
    }

    @Override
    public void onEditClick(int position, SubjectDetails subjectDetails) {
        Edit_schedule_fragment edit_schedule_fragment = Edit_schedule_fragment.newInstance(subjectDetails.getSubject(),
                subjectDetails.getFaculty(),
                subjectDetails.get_id(),
                subjectDetails.getTime(),
                positionDay,
                this);
        edit_schedule_fragment.show(getParentFragmentManager(), "Edit");
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }



}