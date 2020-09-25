package com.example.attendo.ui.main.drawers;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
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

import com.airbnb.lottie.LottieAnimationView;
import com.example.attendo.R;
import com.example.attendo.ui.main.FragmentHome;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


public class FragmentExamReminder extends Fragment {

    EditText label;
    FloatingActionButton mFloatingActionButton;
    TimePicker timePicker;
    TextView timeShow, labelShow;
    CardView alarmCard;
    Button cancelAlarm;
    private boolean flag;
    private String mylabel;
    private PendingIntent alarmdone;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_exam_reminder, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Class Reminder");
        mFloatingActionButton=view.findViewById(R.id.add_rem);
        timeShow = view.findViewById(R.id.time_show);
        labelShow = view.findViewById(R.id.label_show);
        alarmCard = view.findViewById(R.id.alarm_card_view);
        cancelAlarm = view.findViewById(R.id.cancel_alarm);


        bundle = new Bundle();

        SharedPreferences preferences = getContext().getSharedPreferences("MYPREF", 0);
        SharedPreferences.Editor  editor = preferences.edit();

        SharedPreferences retrieve = getContext().getSharedPreferences("MYPREF" ,0);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);

        View bottomSheet = LayoutInflater.from(getContext()).inflate(R.layout.time_picker_spinner_bottom_sheet,
                (ConstraintLayout) view.findViewById(R.id.time_picker_container));
        bottomSheetDialog.setContentView(bottomSheet);
        bottomSheetDialog.setDismissWithAnimation(true);

        Intent intent = new Intent(getActivity(), AlarmReminder.class);

        AlarmManager alarm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        cancelAlarm.setEnabled(false);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.show();

                timePicker = bottomSheet.findViewById(R.id.timePicker);
                label = bottomSheet.findViewById(R.id.reminder_label);
                Button add = bottomSheet.findViewById(R.id.add_reminder);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mylabel = label.getText().toString().trim();

                        bundle.putString("Label",mylabel);
                        intent.putExtras(bundle);

                        alarmdone = PendingIntent.getBroadcast(getActivity(), 101 ,intent,PendingIntent.FLAG_CANCEL_CURRENT);

                        int hour = timePicker.getCurrentHour();
                        int minute= timePicker.getCurrentMinute();

                        AlarmReminder reminder = new AlarmReminder();

                        //Create Time
                        Calendar startTime = Calendar.getInstance();
                        startTime.set(Calendar.HOUR_OF_DAY,hour);
                        startTime.set(Calendar.MINUTE,minute);
                        startTime.set(Calendar.SECOND,0);

                        //my code to show time and label in cardview
                        String timeshow = DateFormat.getTimeInstance(DateFormat.SHORT).format(startTime.getTime());
                        String labelshow = label.getText().toString().trim();

                        editor.putString("time" ,timeshow);
                        editor.putString("label" ,labelshow);
                        editor.commit();

                        String retirveTime = retrieve.getString("time" , "Reminder Time");
                        String retriveLabel = retrieve.getString("label" ,"Reminder Label");

                        timeShow.setText(retirveTime);
                        labelShow.setText(retriveLabel);

                         //updateTimeText(startTime);

                        long alarmStartTime = startTime.getTimeInMillis();

                        //set Alarm
                        alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime,alarmdone);

                        Toast.makeText(getActivity(),"Reminder Added",Toast.LENGTH_LONG).show();

                        bottomSheetDialog.dismiss();
                        label.setText("");
                        cancelAlarm.setText("Cancel Reminder");
                        cancelAlarm.setEnabled(true);
                    }
                });

            }
        });


      //cancel alarm
        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm.setEnabled(false);
               cancelAlarm.setText("Set Alarm");
                flag = false;

                alarm.cancel(alarmdone);
                Toast.makeText(getContext(), "Reminder Cancelled", Toast.LENGTH_SHORT).show();

                timeShow.setText("Set a Reminder");
                labelShow.setText("Reminder Label");
            }
        });

        String retirveTime = retrieve.getString("time" , "Set a Reminder");
        String retriveLabel = retrieve.getString("label" ,"Reminder Label");

        timeShow.setText(retirveTime);
        labelShow.setText(retriveLabel);

        return  view;
    }



    }

