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




  EditText title;
   EditText label;
  FloatingActionButton mFloatingActionButton;
  Button update,Cancel;
  TimePicker time,timePicker;
  TextView timeShow, labelShow;
  CardView alarmCard;
  LottieAnimationView cancelAlarm;
  private int notificationId = 5;
   String mylabel;
  private FragmentHome fragmentHome;
    TextView subject;
    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";


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
       /* NotificationManager manager = (NotificationManager)getActivity(). getSystemService(NOTIFICATION_SERVICE);
        assert manager != null;
        manager.cancel(getActivity().getIntent().getIntExtra(NOTIFICATION_ID, -1));
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exam_reminder, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Class Reminder");
        mFloatingActionButton=view.findViewById(R.id.add_rem);
        timeShow = view.findViewById(R.id.time_show);
        labelShow = view.findViewById(R.id.label_show);
        alarmCard = view.findViewById(R.id.alarm_card_view);
        cancelAlarm = view.findViewById(R.id.cancel_alarm);


        SharedPreferences preferences = getContext().getSharedPreferences("MYPREF", 0);
        SharedPreferences.Editor  editor = preferences.edit();

        SharedPreferences retrieve = getContext().getSharedPreferences("MYPREF" ,0);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);

        View bottomSheet = LayoutInflater.from(getContext()).inflate(R.layout.time_picker_spinner_bottom_sheet,
                (ConstraintLayout) view.findViewById(R.id.time_picker_container));
        bottomSheetDialog.setContentView(bottomSheet);
        bottomSheetDialog.setDismissWithAnimation(true);

        Intent intent = new Intent(getActivity(), AlarmReminder.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getActivity(),
                0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);













        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bottomSheetDialog.show();


                 timePicker = bottomSheet.findViewById(R.id.timePicker);
                label = bottomSheet.findViewById(R.id.reminder_label);
                Button add = bottomSheet.findViewById(R.id.add_reminder);


                mylabel = label.getText().toString().trim();

                intent.putExtra("notificationId", notificationId);






                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent.putExtra("todo", label.getText().toString());



                       //Intent frag = new Intent(getActivity(),AlarmReminder.class);
                        //frag.putExtra("Label", label.getText().toString());



                        int hour = timePicker.getCurrentHour();
                        int minute= timePicker.getCurrentMinute();

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

                        String retirveTime = retrieve.getString("time" , "Set An Alarm");
                        String retriveLabel = retrieve.getString("label" ,"Your Label");

                        timeShow.setText(retirveTime);
                        labelShow.setText(retriveLabel);

                         //updateTimeText(startTime);

                        long alarmStartTime = startTime.getTimeInMillis();

                        //set Alarm
                        alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime,alarmIntent);


                        Toast.makeText(getActivity(),"Reminder Added",Toast.LENGTH_LONG).show();

                        bottomSheetDialog.dismiss();
                        label.setText("");


                    }
                });



            }
        });



      //cancel alarm
        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm.setSpeed(2f);
                cancelAlarm.playAnimation();

                alarm.cancel(alarmIntent);
                Toast.makeText(getContext(), "Alarm cancelled", Toast.LENGTH_SHORT).show();




            }
        });

        String retirveTime = retrieve.getString("time" , "Set An Alarm");
        String retriveLabel = retrieve.getString("label" ,"Your Label");
        timeShow.setText(retirveTime);
        labelShow.setText(retriveLabel);












        return  view;
    }

   /* private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }*/


   /*private void updateTimeText(Calendar c) {
       String timeText = label.getText().toString()+" : Alarm set for : ";
       timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
      // title.setText("");

       subject.setText(timeText);
   }

    */


  /*  public static PendingIntent getDismissIntent(int notificationId, Context context) {
        Intent intent = new Intent(context, AlarmReminder.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(NOTIFICATION_ID, notificationId);
        PendingIntent dismissIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return dismissIntent;
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();


    }
}
