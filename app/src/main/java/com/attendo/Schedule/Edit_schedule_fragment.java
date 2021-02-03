package com.attendo.Schedule;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.attendo.R;
import com.attendo.Schedule.Interface.UpdateRecyclerView;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.data.model.schedule.ScheduleEdit;
import com.attendo.ui.CustomLoadingDialog;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public  class Edit_schedule_fragment extends BottomSheetDialogFragment implements AdapterView.OnItemSelectedListener{


    private LottieAnimationView celebration;

    public String day;
    private ScheduleViewModel scheduleViewModel;
    private FirebaseScheduleViewModel firebaseScheduleViewModel;
    private CustomLoadingDialog customLoadingDialog;
    private AppPreferences appPreferences;
    private UpdateRecyclerView updateRecyclerView;

    private ProgressBar PB;

    //schdule class Id
    private String ScheduleClassId,subject_name,time,prof;
    private int mPositionDay;

    private String class_id;

    //check if data is sent to server
    private boolean check = false;


    String timePickerTime;
    String scheduleId;

    @BindView(R.id.edit_sub_BottomSheet)
    EditText subjectName;

    @BindView(R.id.update_faculty)
    EditText faculty;

    @BindView(R.id.spinner_schedule)
    Spinner spinner;

    @BindView(R.id.edit_sub_details_time_picker)
    TimePicker timePicker;

    @BindView(R.id.edit_subject_btn)
    Button update;

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
            time = getArguments().getString("TIME");
            mPositionDay = getArguments().getInt("RVPosition");
            Log.d("Update" ,String.valueOf(mPositionDay)+"  : inside edit bottom sheet new onCreate function  ");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_schedule_fragment, container, false);
        ButterKnife.bind(this,view);

        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        firebaseScheduleViewModel = new ViewModelProvider(this).get(FirebaseScheduleViewModel.class);
        appPreferences = new AppPreferences(getActivity());
        scheduleId = appPreferences.retrieveScheduleId();
        class_id = appPreferences.RetrieveClassId();

        customLoadingDialog = new CustomLoadingDialog(getActivity());

        subjectName.setText(subject_name);
        faculty.setText(prof);




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.weekday, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);




        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
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

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sub = subjectName.getText().toString();
                String teacher = faculty.getText().toString();
                String clock = timePickerTime;
                if (sub.length() > 0 && teacher.length() > 0 && clock.length() > 0 && day.length() > 0) {
                    customLoadingDialog.startDialog(false);
                    edit_schedule(ScheduleClassId);
                } else {
                    Toast.makeText(getActivity(), "Please fill all details", Toast.LENGTH_SHORT).show();
                }
            }

        });







        return view;
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
            ScheduleEdit scheduleEdit = new ScheduleEdit(appPreferences.retrieveScheduleId(),scheduleClassId,day,timePickerTime,subjectName.getText().toString(),faculty.getText().toString());
            scheduleViewModel.editScheduleResponse(scheduleEdit);
            scheduleViewModel.scheduleResponseEdit().observe(getActivity(), data -> {
                if (data == null) {
                    customLoadingDialog.dismissDialog();
                    Toast.makeText(getActivity(),"Fail to edit Schedule",Toast.LENGTH_SHORT).show();
                    Log.i("ApiCall", "Failed to edit");
                    check = true;
                    dismiss();
                } else {
                    updateRecyclerView.sendPosition(mPositionDay);
                    customLoadingDialog.dismissDialog();
                    Log.i("ApiCall", "successFull");
                    Toast.makeText(getActivity(),"Schedule Edited Successfully",Toast.LENGTH_SHORT).show();
                    dismiss();

                    check = true;
                }
            });
        }else{
            Toast.makeText(getContext(), "Please Wait!", Toast.LENGTH_SHORT).show();
        }

    }
}