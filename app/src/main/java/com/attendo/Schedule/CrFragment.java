package com.attendo.Schedule;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.Schedule.Adapters.RoutineItemAdapterCr;
import com.attendo.Schedule.Adapters.WeekDayAdapter;
import com.attendo.Schedule.Model.DayOfWeek;
import com.attendo.Schedule.Interface.UpdateRecyclerView;
import com.attendo.data.model.GetSchedule;
import com.attendo.data.model.SubjectDetails;
import com.attendo.viewmodel.GetScheduleViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CrFragment extends Fragment implements UpdateRecyclerView {

    private RecyclerView dayofWeekRecyclerView,subjectrecyclerView;
    private WeekDayAdapter weekDayAdapter;
    private ArrayList<DayOfWeek> dayList;
    private RoutineItemAdapterCr routineItemAdapter;
    private List<SubjectDetails> subjectRoutines;
    private FloatingActionButton fb;
    private AddSubjectDetailsFragment addSubjectDetailsFragment;
    private GetScheduleViewModel getScheduleViewModel;

    //firebase references
    private DatabaseReference mReference;
    private FirebaseAuth mAuth;
    private String class_id;

    //List for subjects
    List<SubjectDetails> monday;
    List<SubjectDetails> tuesday;
    List<SubjectDetails> thursday;
    List<SubjectDetails> wednesday;
    List<SubjectDetails> friday;
    List<SubjectDetails> saturday;
    List<SubjectDetails> sunday;


    @Override
    public void onStart() {
        super.onStart();
        getClassId();
//        getScheduleViewModel = new ViewModelProvider(this).get(GetScheduleViewModel.class);
//        if(class_id == null){
//            Toast.makeText(getContext(), "Please wait", Toast.LENGTH_SHORT).show();
//        }else getAllData();
    }

    @Override
    public void onResume() {
        super.onResume();
//        if(class_id == null){
//            Toast.makeText(getContext(), "Please wait", Toast.LENGTH_SHORT).show();
//        }else getAllData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  =inflater.inflate(R.layout.fragment_cr, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Routine");

        addSubjectDetailsFragment = new AddSubjectDetailsFragment();


        fb = view.findViewById(R.id.Schedule_add_subject);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSubjectDetailsFragment addSubjectDetailsFragment = new AddSubjectDetailsFragment();
                addSubjectDetailsFragment.show(getParentFragmentManager(),"Subject_Details");
            }
        });

        dayofWeekRecyclerView = view.findViewById(R.id.static_weekdays_cr);
        subjectrecyclerView = view.findViewById(R.id.subjectsRecyclerViewCr);

        subjectrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        routineItemAdapter = new RoutineItemAdapterCr(subjectRoutines);
        subjectrecyclerView.setAdapter(routineItemAdapter);

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
    public void callback(int position, List<SubjectDetails> subjectRoutines) {
        if(subjectRoutines == null){
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        }else{
            routineItemAdapter = new RoutineItemAdapterCr(subjectRoutines);
        }
        routineItemAdapter.notifyDataSetChanged();
        subjectrecyclerView.setAdapter(routineItemAdapter);
    }

    @Override
    public void sendPosition(int position) {
        switch (position){
            case 0:
                setAdapterAccordingToPosition(sunday);
                break;
            case 1:
                setAdapterAccordingToPosition(monday);
                break;
            case 2:
                setAdapterAccordingToPosition(tuesday);
                break;
            case 3:
                setAdapterAccordingToPosition(wednesday);
                break;
            case 4:
                setAdapterAccordingToPosition(thursday);
                break;
            case 5:
                setAdapterAccordingToPosition(friday);
                break;
            case 6:
                setAdapterAccordingToPosition(saturday);
                break;
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

//    private void getAllData(){
//        //getting for monday
//        getScheduleViewModel.setScheduleGetResponse(new GetSchedule(class_id, "monday"));
//        getScheduleViewModel.getScheduleGetResponse().observe((LifecycleOwner) getActivity(), data ->{
//            if(data != null){
//                monday = data.getRequiredSchedule();
//            }else monday = null;
//        });
//
//        //getting for tuesday
//        getScheduleViewModel.setScheduleGetResponse(new GetSchedule(class_id, "tuesday"));
//        getScheduleViewModel.getScheduleGetResponse().observe((LifecycleOwner) getActivity(), data ->{
//            if(data != null){
//                tuesday = data.getRequiredSchedule();
//            }else tuesday = null;
//        });
//
//        //getting for wednesday
//        getScheduleViewModel.setScheduleGetResponse(new GetSchedule(class_id, "wednesday"));
//        getScheduleViewModel.getScheduleGetResponse().observe((LifecycleOwner) getActivity(), data ->{
//            if(data != null){
//                wednesday = data.getRequiredSchedule();
//            }else wednesday = null;
//        });
//
//        //getting for thursday
//        getScheduleViewModel.setScheduleGetResponse(new GetSchedule(class_id, "thursday"));
//        getScheduleViewModel.getScheduleGetResponse().observe((LifecycleOwner) getActivity(), data ->{
//            if(data != null){
//                thursday = data.getRequiredSchedule();
//            }else thursday = null;
//        });
//
//        //getting for friday
//        getScheduleViewModel.setScheduleGetResponse(new GetSchedule(class_id, "friday"));
//        getScheduleViewModel.getScheduleGetResponse().observe((LifecycleOwner) getActivity(), data ->{
//            if(data != null){
//                friday = data.getRequiredSchedule();
//            }else friday = null;
//        });
//
//        //getting for saturday
//        getScheduleViewModel.setScheduleGetResponse(new GetSchedule(class_id, "saturday"));
//        getScheduleViewModel.getScheduleGetResponse().observe((LifecycleOwner) getActivity(), data ->{
//            if(data != null){
//                saturday = data.getRequiredSchedule();
//            }else saturday = null;
//        });
//
//        //getting for sunday
//        getScheduleViewModel.setScheduleGetResponse(new GetSchedule(class_id, "sunday"));
//        getScheduleViewModel.getScheduleGetResponse().observe((LifecycleOwner) getActivity(), data ->{
//            if(data != null){
//                sunday = data.getRequiredSchedule();
//            }else sunday = null;
//        });
//    }


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

    private void setAdapterAccordingToPosition(List<SubjectDetails> dataList){
        if(dataList == null){
            Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
        }else{
            routineItemAdapter = new RoutineItemAdapterCr(dataList);
        }
        routineItemAdapter.notifyDataSetChanged();
        subjectrecyclerView.setAdapter(routineItemAdapter);

    }

}