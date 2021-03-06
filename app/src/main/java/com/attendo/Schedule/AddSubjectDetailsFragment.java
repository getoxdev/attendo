package com.attendo.Schedule;

import android.os.Bundle;

import androidx.annotation.Nullable;
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
import com.attendo.data.model.schedule.Schedule;
import com.attendo.ui.CustomLoadingDialog;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddSubjectDetailsFragment extends BottomSheetDialogFragment implements AdapterView.OnItemSelectedListener {


    private EditText subject, faculty, time;
    private Button submit;
    private LottieAnimationView celebration;
    private Spinner spi;
    public String day;
    private ScheduleViewModel scheduleViewModel;
    private FirebaseScheduleViewModel firebaseScheduleViewModel;
    private CustomLoadingDialog customLoadingDialog;
    private AppPreferences appPreferences;
    private UpdateRecyclerView updateRecyclerView;
    private int mPositionDay;

    private ProgressBar PB;

    //class Id
    private String class_Id;

    //check if data is sent to server
    private boolean check = false;

    TimePicker timePicker;
    String timePickerTime;

    public AddSubjectDetailsFragment(UpdateRecyclerView updateRecyclerView){
        this.updateRecyclerView = updateRecyclerView;
    }

    public static AddSubjectDetailsFragment newInstance(UpdateRecyclerView mUpdateRecyclerView, int positionDay) {

        Bundle args = new Bundle();

        AddSubjectDetailsFragment fragment = new AddSubjectDetailsFragment(mUpdateRecyclerView);
        args.putInt("RVPosition", positionDay);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mPositionDay = getArguments().getInt("RVPosition");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //initial setting of data
        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        class_Id = firebaseScheduleViewModel.RetrieveClassId();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_subject_details, container, false);



        firebaseScheduleViewModel = new ViewModelProvider(this).get(FirebaseScheduleViewModel.class);
        customLoadingDialog = new CustomLoadingDialog(getActivity());
        appPreferences = AppPreferences.getInstance(getContext());

        PB = view.findViewById(R.id.progress_bar_add_subject_details);
        PB.setVisibility(View.INVISIBLE);


        spi = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.weekday, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spi.setAdapter(adapter);
        spi.setOnItemSelectedListener(this);


        celebration = view.findViewById(R.id.lottie_animation_add_subject_details);
        celebration.setVisibility(View.INVISIBLE);

        subject = view.findViewById(R.id.add_subject_bottomsheet);
        faculty = view.findViewById(R.id.add_faculty);
        time = view.findViewById(R.id.add_Time);
        submit = view.findViewById(R.id.add_subject_btn);
        timePicker = view.findViewById(R.id.add_sub_details_time_picker);

        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        timePickerTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(time);


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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sub = subject.getText().toString();
                String teacher = faculty.getText().toString();
                String clock = timePickerTime;
                if (sub.length() > 0 && teacher.length() > 0 && clock.length() > 0 && day.length() > 0) {
                    customLoadingDialog.startDialog(false);
                    sendDataToServer();
                    if(check){
                        celebration.setVisibility(View.VISIBLE);
                        celebration.playAnimation();
                        dismiss();
                        check = false;
                    }else{
                        //Toast.makeText(getContext(), "Saving Subject Details", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(getActivity(), "Please fill all details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        day = parent.getItemAtPosition(position).toString();
        day = day.toLowerCase();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    private void sendDataToServer(){
        if(appPreferences.RetrieveClassId() != null){
            Schedule schedule = new Schedule(appPreferences.RetrieveClassId(), day, timePickerTime, subject.getText().toString(), faculty.getText().toString());
            scheduleViewModel.setScheduleResponse(schedule);
            //Toast.makeText(getActivity(),""+appPreferences.RetrieveClassId()+" "+day+" "+time.getText().toString()+" "+subject.getText().toString()+" "+faculty.getText().toString(),Toast.LENGTH_LONG).show();
            scheduleViewModel.getScheduleResponse().observe(getActivity(), data -> {
                if (data == null) {
                    customLoadingDialog.dismissDialog();
                    Toast.makeText(getActivity(),"Fail to Add Schedule",Toast.LENGTH_SHORT).show();
                    Log.i("ApiCall", "Failed");
                    check = true;
                } else {
                    customLoadingDialog.dismissDialog();
                    Log.i("ApiCall", "successFull");
                    String scheduleId = data.getSchedule().get_id();
                    Log.e("schedule id ",scheduleId);
                    firebaseScheduleViewModel.AddClassScheduleId(scheduleId);
                    appPreferences.AddScheduleId(scheduleId);
                    updateRecyclerView.sendPosition(mPositionDay);
                    Toast.makeText(getActivity(),"Schedule Added Successfully",Toast.LENGTH_SHORT).show();
                    celebration.setVisibility(View.VISIBLE);
                    celebration.playAnimation();
                    check = true;
                    Handler mhandler = new Handler();
                    mhandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismissAllowingStateLoss();
                        }
                    },600);
                }
            });
        }else{
            Toast.makeText(getActivity(), "Please Wait!", Toast.LENGTH_SHORT).show();
        }

    }

}