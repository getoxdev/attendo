package com.example.attendo.ui.main.drawers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.attendo.R;
import com.example.attendo.ui.main.FragmentHome;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;


public class FragmentExamReminder extends Fragment {

  EditText title;
  Button update,Cancel;
  TimePicker time;
  private int notificationId = 1;
  private FragmentHome fragmentHome;
    TextView textView;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FragmentExamReminder() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragmentExamReminder newInstance(String param1, String param2) {
        FragmentExamReminder fragment = new FragmentExamReminder();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exam_reminder, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Class Reminder");

        fragmentHome = new FragmentHome();
        title = view.findViewById(R.id.Time_Title);
        time = view.findViewById(R.id.Time_Picker);
        update = view.findViewById(R.id.Set_Time);
        Cancel = view.findViewById(R.id.Cancel_Time);
        textView=view.findViewById(R.id.textView);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AlarmReminder.class);
                intent.putExtra("notificationId", notificationId);
                intent.putExtra("todo", title.getText().toString());


                PendingIntent alarmIntent = PendingIntent.getBroadcast(getActivity(),
                       0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager alarm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                int hour = time.getCurrentHour();
                int minute= time.getCurrentMinute();

                //Create Time
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY,hour);
                startTime.set(Calendar.MINUTE,minute);
                startTime.set(Calendar.SECOND,0);
                updateTimeText(startTime);
                long alarmStartTime = startTime.getTimeInMillis();


                /*if time is crossed
                if(startTime.before(Calendar.getInstance())){
                    startTime.add(Calendar.DATE, 1);
                }*/


                //set Alarm
                alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime,alarmIntent);

                Toast.makeText(getActivity(),"Reminder Added",Toast.LENGTH_LONG).show();
               // setFragment(fragmentHome);
            }
        });



        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Reminder Not Added",Toast.LENGTH_LONG).show();
                //setFragment(fragmentHome);
            }
        });


        return  view;
    }

   /* private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }*/
   private void updateTimeText(Calendar c) {
       String timeText = title.getText().toString()+" : Alarm set for : ";
       timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

       textView.setText(timeText);
   }

}
