package com.attendo.Schedule;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.attendo.R;
import com.attendo.Schedule.Interface.UpdateRecyclerView;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.data.model.schedule.ScheduleEdit;
import com.attendo.databinding.FragmentEditScheduleFragmentBinding;
import com.attendo.ui.CustomLoadingDialog;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public  class Edit_schedule_fragment extends BottomSheetDialogFragment implements AdapterView.OnItemSelectedListener{

    private FragmentEditScheduleFragmentBinding binding;

    public String day;
    private ScheduleViewModel scheduleViewModel;
    private CustomLoadingDialog customLoadingDialog;
    private AppPreferences appPreferences;
    private UpdateRecyclerView updateRecyclerView;
    private String ScheduleClassId,subject_name,prof;
    private int mPositionDay;

    String timePickerTime;
    String scheduleId;

    public Edit_schedule_fragment(UpdateRecyclerView updateRecyclerView){
        this.updateRecyclerView = updateRecyclerView;
    }

    public static Edit_schedule_fragment newInstance(String subject_name, String prof,String scheduleClassId,String time, int positionDay, UpdateRecyclerView mUpdateRecyclerView) {
        Edit_schedule_fragment edit_schedule_fragment = new Edit_schedule_fragment(mUpdateRecyclerView);
        Bundle args = new Bundle();
        args.putString("SUBJECT", subject_name);
        args.putString("PROF", prof);
        args.putString("SCHEDULE_CLASS_ID",scheduleClassId);
        args.putString("TIME",time);
        args.putInt("RVPosition", positionDay);
        Log.d("Update" ,String.valueOf(positionDay)+"  : inside edit bottom sheet new instance function  ");
        edit_schedule_fragment.setArguments(args);

        return edit_schedule_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ScheduleClassId = getArguments().getString("SCHEDULE_CLASS_ID");
            subject_name = getArguments().getString("SUBJECT");
            prof = getArguments().getString("PROF");
            mPositionDay = getArguments().getInt("RVPosition");
            Log.d("Update" ,String.valueOf(mPositionDay)+"  : inside edit bottom sheet new onCreate function  ");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditScheduleFragmentBinding.inflate(inflater, container, false);

        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        appPreferences = new AppPreferences(getActivity());
        scheduleId = appPreferences.retrieveScheduleId();

        customLoadingDialog = new CustomLoadingDialog(getActivity());

        binding.editSubBottomSheet.setText(subject_name);
        binding.updateFaculty.setText(prof);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.weekday, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSchedule.setAdapter(adapter);
        binding.spinnerSchedule.setOnItemSelectedListener(this);

        binding.editSubDetailsTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfTheDay, int minute) {
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, hourOfTheDay);
                startTime.set(Calendar.MINUTE, minute);

                //instance of simple date format to get the correct time in correct format
                SimpleDateFormat sd = new SimpleDateFormat("hh:mm a", Locale.getDefault());

                timePickerTime = sd.format(startTime.getTime());
                Log.d("schedule", timePickerTime + "  : time from time picker");

            }
        });

        binding.editSubjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sub = binding.editSubBottomSheet.getText().toString();
                String teacher = binding.updateFaculty.getText().toString();
                String clock = timePickerTime;
                if (sub.length() > 0 && teacher.length() > 0 && clock.length() > 0 && day.length() > 0) {
                    customLoadingDialog.startDialog(false);
                    edit_schedule(ScheduleClassId);
                } else {
                    Toast.makeText(getActivity(), "Please fill all details", Toast.LENGTH_SHORT).show();
                }
            }

        });

        return binding.getRoot();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        day = adapterView.getItemAtPosition(i).toString();
        day = day.toLowerCase();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void edit_schedule(String scheduleClassId)
    {
        if(appPreferences.RetrieveClassId() != null){
            ScheduleEdit scheduleEdit = new ScheduleEdit(appPreferences.retrieveScheduleId(), scheduleClassId, day, timePickerTime, binding.editSubBottomSheet.getText().toString(), binding.updateFaculty.getText().toString());
            scheduleViewModel.editScheduleResponse(scheduleEdit);
            scheduleViewModel.scheduleResponseEdit().observe(getActivity(), data -> {
                if (data == null) {
                    customLoadingDialog.dismissDialog();
                    Toast.makeText(getActivity(),"Fail to edit Schedule",Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    updateRecyclerView.sendPosition(mPositionDay);
                    customLoadingDialog.dismissDialog();
                    Toast.makeText(getActivity(),"Schedule Edited Successfully",Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            });
        }else{
            Toast.makeText(getActivity(), "Please Wait!", Toast.LENGTH_SHORT).show();
        }

    }
}