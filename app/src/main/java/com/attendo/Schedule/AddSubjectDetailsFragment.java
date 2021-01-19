package com.attendo.Schedule;

import android.content.SharedPreferences;
import android.os.Bundle;

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
import com.attendo.data.model.Schedule;
import com.attendo.ui.CustomLoadingDialog;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddSubjectDetailsFragment extends BottomSheetDialogFragment implements AdapterView.OnItemSelectedListener {


    private EditText subject, faculty, time;
    private Button submit;
    private LottieAnimationView celebration;
    private Spinner spi;
    public String day;
    private ScheduleViewModel scheduleViewModel;
    private FirebaseScheduleViewModel firebaseScheduleViewModel;
    private CustomLoadingDialog customLoadingDialog;

    private ProgressBar PB;

    //class Id
    private String class_Id;

    //check if data is sent to server
    private boolean check = false;

    TimePicker timePicker;
    String timePickerTime;


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


//                if(hourOfTheDay > 12){
//                    int hour = hourOfTheDay;
//                    timePickerTime = hour - 12 + ":" + minute + " " + "pm";
//                }else{
//                    timePickerTime = hourOfTheDay + ":" + minute + " " + "am";
//                }
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        day = parent.getItemAtPosition(position).toString();
        day = day.toLowerCase();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    private void sendDataToServer(){
        if(class_Id != null){
            Schedule schedule = new Schedule(firebaseScheduleViewModel.RetrieveClassId(), day, timePickerTime, subject.getText().toString(), faculty.getText().toString());
            scheduleViewModel.setScheduleResponse(schedule);
            //Toast.makeText(getActivity(),""+firebaseScheduleViewModel.RetrieveClassId()+" "+day+" "+time.getText().toString()+" "+subject.getText().toString()+" "+faculty.getText().toString(),Toast.LENGTH_LONG).show();
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
                    Log.i("schedule id ",scheduleId);
                    firebaseScheduleViewModel.AddClassScheduleId(scheduleId);
                    SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("Schedule_Id",scheduleId);
                    editor.commit();
                    Toast.makeText(getActivity(),"Schedule Added Successfully",Toast.LENGTH_SHORT).show();
                    celebration.setVisibility(View.VISIBLE);
                    celebration.playAnimation();
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