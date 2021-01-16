package com.attendo.Schedule.CrViewPager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.Schedule.Adapters.RoutineItemAdapterCr;
import com.attendo.Schedule.AddSubjectDetailsFragment;
import com.attendo.data.model.SubjectDetails;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;

public class MondayCr extends Fragment {

    private AddSubjectDetailsFragment addSubjectDetailsFragment;
    private FloatingActionButton fb;
    private FirebaseScheduleViewModel scheduleViewModel;
    private String classId;
    private RoutineItemAdapterCr routineItemAdapterCr;
    private List<SubjectDetails> mondayList;
    private ScheduleViewModel getScheduleViewModel;

    public static MondayCr getInstance(){
        MondayCr mondayCr = new MondayCr();
        return mondayCr;
    }

    @BindView(R.id.monday_recyclerView)
    RecyclerView mondayRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_monday_cr, container, false);

        scheduleViewModel = new ViewModelProvider(this).get(FirebaseScheduleViewModel.class);
        getScheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);

        classId = scheduleViewModel.RetrieveClassCode();
        if(classId != null)
        {
            getScheduleViewModel.setScheduleGetResponse(classId, "monday");
            getScheduleViewModel.getScheduleGetResponse().observe(getViewLifecycleOwner(), data ->
            {
                if(data != null){
                    mondayList = data.getRequiredSchedule();
                    routineItemAdapterCr = new RoutineItemAdapterCr(mondayList);
                    mondayRecyclerView.setAdapter(routineItemAdapterCr);
                }
                else Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            });
        }
        else Toast.makeText(getContext(), "Please Wait", Toast.LENGTH_SHORT).show();

        addSubjectDetailsFragment = new AddSubjectDetailsFragment();

        fb = view.findViewById(R.id.Schedule_add_subject_Monday);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("day","Monday");
                addSubjectDetailsFragment.setArguments(bundle);
                addSubjectDetailsFragment.show(getParentFragmentManager(),"Subject_Details");
            }
        });

        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();

        classId = scheduleViewModel.RetrieveClassCode();
        if(classId != null)
        {
            getScheduleViewModel.setScheduleGetResponse(classId, "monday");
            getScheduleViewModel.getScheduleGetResponse().observe(getViewLifecycleOwner(), data ->
            {
                if(data != null){
                    mondayList = data.getRequiredSchedule();
                    routineItemAdapterCr = new RoutineItemAdapterCr(mondayList);
                    mondayRecyclerView.setAdapter(routineItemAdapterCr);
                }
                else Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            });
        }
        else Toast.makeText(getContext(), "Please Wait", Toast.LENGTH_SHORT).show();
    }
}