package com.attendo.Schedule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.data.model.schedule.Schedule;
import com.attendo.ui.main.BottomNavMainActivity;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudetntSettingsFragment extends BottomSheetDialogFragment {

    private TextView text;
    private FirebaseAuth mAuth;
    private FirebaseScheduleViewModel firebaseScheduleViewModel;
    private ScheduleViewModel scheduleViewModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_studetnt_settings, container, false);

        mAuth = FirebaseAuth.getInstance();
        firebaseScheduleViewModel = new ViewModelProvider(this).get(FirebaseScheduleViewModel.class);
        text = view.findViewById(R.id.leaveClass_student_settings);
        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check = deleteSharedpreference();
                ServerDelete();
                setsharedpreferenceNull();
                Toast.makeText(getActivity(), "You left the Schedule", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return view;
    }

    private void setsharedpreferenceNull() {
        AppPreferences appPreferences = new AppPreferences(getContext());
        appPreferences.AddClassId(null);
        appPreferences.AddJoinAs(null);
        appPreferences.AddClassScheduleId(null);
    }

    private String deleteSharedpreference() {
        return firebaseScheduleViewModel.DeleteShedule();
    }

    private void ServerDelete() {
        scheduleViewModel.Leave_Class(mAuth.getCurrentUser().getEmail());
        scheduleViewModel.leaveClassResponse().observe(getActivity(), data -> {
            if (data == null) {
                Log.i("ApiCall", "Failed");
            } else {
                Log.i("Api Call :","Success");
            }
        });
    }

}