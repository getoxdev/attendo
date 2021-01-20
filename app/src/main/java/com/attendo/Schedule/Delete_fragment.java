package com.attendo.Schedule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.Schedule.Adapters.RoutineItemAdapterCr;
import com.attendo.Schedule.Interface.UpdateRecyclerView;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.data.model.ScheduleDelete;
import com.attendo.data.model.SubjectDetails;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;


public class Delete_fragment extends BottomSheetDialogFragment  {

    private FirebaseScheduleViewModel firebaseScheduleViewModel;
    private ScheduleViewModel scheduleViewModel;
    String scheduleId,id,day,fac,sub,time;
    Button delete_btn;
    private AppPreferences appPreferences;
    private RoutineItemAdapterCr routineItemAdapterCr;
    String ScheduleClassId;
    private CrFragment crFragment;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete_schedule, container, false);

        firebaseScheduleViewModel = new ViewModelProvider(this).get(FirebaseScheduleViewModel.class);
        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        delete_btn = view.findViewById(R.id.delete_button);


        appPreferences = new AppPreferences(getActivity());
        scheduleId = appPreferences.RetrieveClassScheduleId();

         Bundle bundle = getArguments();
         id= bundle.getString("ScheduleClassId");
         day = bundle.getString("day");




        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               delete_schedule(ScheduleClassId);

            }
        });



        return view;
    }




    public void delete_schedule(String scheduleClassId)
    {
        ScheduleDelete scheduleDelete = new ScheduleDelete(scheduleId,day,id);
        scheduleViewModel.DeleteSchedule(scheduleDelete);
        scheduleViewModel.getScheduleGetResponse().observe(getActivity(), data->
        {
            if (data == null) {
                Toast.makeText(getActivity(),"Fail to delete Schedule",Toast.LENGTH_SHORT).show();
                Log.i("ApiCall", "Failed");

            } else {

                Log.i("ApiCall", "delete successFull");}
        });

    }
}
