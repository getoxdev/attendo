package com.attendo.Schedule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
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
import com.attendo.data.model.Schedule;
import com.attendo.data.model.ScheduleEdit;
import com.attendo.data.model.SubjectDetails;
import com.attendo.ui.CustomLoadingDialog;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public  class Edit_schedule_fragment extends BottomSheetDialogFragment implements UpdateRecyclerView,AdapterView.OnItemSelectedListener{


    private LottieAnimationView celebration;

    public String day;
    private ScheduleViewModel scheduleViewModel;
    private FirebaseScheduleViewModel firebaseScheduleViewModel;
    private CustomLoadingDialog customLoadingDialog;
    private AppPreferences appPreferences;

    private ProgressBar PB;

    //schdule class Id
    private String ScheduleClassId;

    private String class_id;

    //check if data is sent to server
    private boolean check = false;


    String timePickerTime;
    String scheduleId;

    @BindView(R.id.edit_sub_BottomSheet)
    EditText subjectName;

    @BindView(R.id.update_faculty)
    EditText Faculty;

    @BindView(R.id.spinner_schedule)
    Spinner spinner;

    @BindView(R.id.edit_sub_details_time_picker)
    TimePicker timePicker;

    @BindView(R.id.edit_subject_btn)
    Button update;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_schedule_fragment, container, false);
        ButterKnife.bind(this,view);

        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        firebaseScheduleViewModel = new ViewModelProvider(this).get(FirebaseScheduleViewModel.class);
        appPreferences = new AppPreferences(getActivity());
        scheduleId = appPreferences.RetrieveClassScheduleId();
        class_id = appPreferences.RetrieveClassId();

        customLoadingDialog = new CustomLoadingDialog(getActivity());




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.weekday, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);




        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfTheDay, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR, hourOfTheDay);
                calendar.set(Calendar.MINUTE, minute);
                if(hourOfTheDay >= 12){
                    Date time = calendar.getTime();
                    timePickerTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(time);
                    Log.d("TimeFormatPM", timePickerTime);
                }else{
                    Date time = calendar.getTime();
                    timePickerTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(time);
                    Log.d("TimeFormatAM", timePickerTime);
                }

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sub = subjectName.getText().toString();
                String teacher = Faculty.getText().toString();
                String clock = timePickerTime;
                if (sub.length() > 0 && teacher.length() > 0 && clock.length() > 0 && day.length() > 0) {
                    edit_schedule(ScheduleClassId);
                    if (check) {
                        dismiss();
                        check = false;
                    } else {
                        Toast.makeText(getContext(), "Saving Subject Details", Toast.LENGTH_LONG).show();
                    }


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

    @Override
    public void callback(int position, List<SubjectDetails> subjectRoutines) {

    }

    @Override
    public void sendPosition(int position) {

    }

    @Override
    public void getscheduleClassId(String subject, String scheduleclassid, String faculty) {

    }


    public void edit_schedule(String scheduleClassId)
    {
        if(class_id!=null){
            ScheduleEdit scheduleEdit = new ScheduleEdit(scheduleId,scheduleClassId,day,timePickerTime,subjectName.getText().toString(),Faculty.getText().toString());
            Toast.makeText(getActivity(),""+scheduleId+"+60059c3d63e1170017e7efab + "+day+" "+timePickerTime+" "+subjectName.getText().toString()+" "+Faculty.getText().toString(),Toast.LENGTH_LONG).show();
            scheduleViewModel.editScheduleResponse(scheduleEdit);
            scheduleViewModel.getScheduleResponse().observe(getActivity(), data -> {
                if (data == null) {
                    customLoadingDialog.dismissDialog();
                    Toast.makeText(getActivity(),"Fail to edit Schedule",Toast.LENGTH_SHORT).show();
                    Log.i("ApiCall", "Failed to edit");
                    check = true;
                } else {
                    customLoadingDialog.dismissDialog();
                    Log.i("ApiCall", "successFull");
                    Toast.makeText(getActivity(),"Schedule Edited Successfully",Toast.LENGTH_SHORT).show();
                    check = true;
                    Handler mhandler = new Handler();
                    mhandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();

                        }
                    },600);
                }
            });
        }else{
            Toast.makeText(getContext(), "Please Wait!", Toast.LENGTH_SHORT).show();
        }

    }
}