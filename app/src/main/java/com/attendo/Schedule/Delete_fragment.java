package com.attendo.Schedule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.Schedule.Adapters.RoutineItemAdapterCr;
import com.attendo.data.model.ScheduleDelete;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class Delete_fragment extends BottomSheetDialogFragment {

    private FirebaseScheduleViewModel firebaseScheduleViewModel;
    private ScheduleViewModel scheduleViewModel;
    String scheduleId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete_schedule, container, false);

        firebaseScheduleViewModel = new ViewModelProvider(this).get(FirebaseScheduleViewModel.class);
        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);

        scheduleId = firebaseScheduleViewModel.RetrieveSchdeuleId();


        return view;
    }


}
