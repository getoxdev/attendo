package com.attendo.Schedule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.Schedule.Interface.UpdateRecyclerView;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.data.model.ScheduleEdit;
import com.attendo.data.model.SubjectDetails;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Edit_schedule_fragment extends BottomSheetDialogFragment implements UpdateRecyclerView,AdapterView.OnItemSelectedListener{

    private EditText subjectName,faculty;
    private Button update;
    private TimePicker timePicker;
    private Spinner spinner;
    private AppPreferences appPreferences;
    public String day;
    private ScheduleViewModel scheduleViewModel;

    String scheduleId,sClassID;
    String timePickerTime;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_schedule_fragment, container, false);

        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);

         subjectName = view.findViewById(R.id.edit_subject_bottom_sheet);
         update = view.findViewById(R.id.edit_subject_btn);
         faculty = view.findViewById(R.id.update_faculty);
         timePicker = view.findViewById(R.id.edit_sub_details_time_picker);
         spinner = view.findViewById(R.id.spinner_schedule);
         appPreferences = new AppPreferences(getActivity());
         scheduleId = appPreferences.RetrieveClassScheduleId();

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
                if (hourOfTheDay >= 12) {
                    Date time = calendar.getTime();
                    timePickerTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(time);
                    Log.d("TimeFormatPM", timePickerTime);
                } else {
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
                String teacher = faculty.getText().toString();
                String clock = timePickerTime;
                if (sub.length() > 0 && teacher.length() > 0 && clock.length() > 0 && day.length() > 0) {
                    edit_schedule();
                    Toast.makeText(getContext(), "Saving Subject Details", Toast.LENGTH_LONG).show();
                    }
                else {
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
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void callback(int position, List<SubjectDetails> subjectRoutines) {

    }

    @Override
    public void sendPosition(int position) {

    }

    @Override
    public void getscheduleClassId(String scheduleClassId) {
        sClassID = scheduleClassId;

    }

    public void edit_schedule()
    {
        ScheduleEdit scheduleEdit = new ScheduleEdit(appPreferences.RetrieveClassScheduleId(),sClassID,day,timePickerTime,subjectName.getText().toString(),faculty.getText().toString());
        scheduleViewModel.editScheduleResponse(scheduleEdit);
        scheduleViewModel.getScheduleResponse().observe(this, data->
        {
            if (data == null) {
                Toast.makeText(getActivity(),"Fail to edit Schedule",Toast.LENGTH_SHORT).show();
                Log.i("ApiCall", "Failed");

            } else {

                Log.i("ApiCall", "edit successFull");}
        });
    }


}