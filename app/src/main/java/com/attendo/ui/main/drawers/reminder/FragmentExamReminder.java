package com.attendo.ui.main.drawers.reminder;

import android.app.PendingIntent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.data.api.ApiHelper;
import com.attendo.data.model.Reminder;
import com.attendo.viewmodel.ReminderViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentExamReminder extends Fragment {

    @BindView(R.id.add_rem)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.time_show)
    TextView timeShow;
    @BindView(R.id.label_show)
    TextView labelShow;
    @BindView(R.id.alarm_card_view)
    CardView alarmCard;
    @BindView(R.id.cancel_alarm)
    Button cancelAlarm;

    TimePicker timePicker;
    EditText label;


    private String mylabel;
    private PendingIntent alarmdone;
    private Bundle bundle;
    private String fcmToken;
    private ReminderViewModel viewModel;
    private ApiHelper apiHelper;
    private String retreiveFcmToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_exam_reminder, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Class Reminder");

        ButterKnife.bind(this,view);

        viewModel = ViewModelProviders.of(getActivity()).get(ReminderViewModel.class);
        apiHelper = ApiHelper.getInstance(getContext());

        SharedPreferences preferences = getContext().getSharedPreferences("MYPREF", 0);
        SharedPreferences.Editor editor = preferences.edit();

        SharedPreferences retrieve = getContext().getSharedPreferences("MYPREF", 0);


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(getActivity(), new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                fcmToken = instanceIdResult.getToken();
                editor.putString("fcmToken",fcmToken);
                editor.commit();
                retreiveFcmToken=retrieve.getString("fcmToken","");
                Log.i("My FCM Token", retreiveFcmToken);
            }
        });

        bundle = new Bundle();



        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);

        View bottomSheet = LayoutInflater.from(getContext()).inflate(R.layout.time_picker_spinner_bottom_sheet,
                (ConstraintLayout) view.findViewById(R.id.time_picker_container));
        bottomSheetDialog.setContentView(bottomSheet);
        bottomSheetDialog.setDismissWithAnimation(true);

        cancelAlarm.setEnabled(false);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.show();
                timePicker=bottomSheet.findViewById(R.id.timePicker);
                label=bottomSheet.findViewById(R.id.reminder_label);
                Button add = bottomSheet.findViewById(R.id.add_reminder);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mylabel = label.getText().toString().trim();

                        bundle.putString("Label", mylabel);

                        int hour = timePicker.getCurrentHour();
                        int minute = timePicker.getCurrentMinute();

                        //Create Time
                        Calendar startTime = Calendar.getInstance();
                        startTime.set(Calendar.HOUR_OF_DAY, hour);
                        startTime.set(Calendar.MINUTE, minute);
                        startTime.set(Calendar.SECOND, 0);

                        //my code to show time and label in cardview
                        String timeshow1 = DateFormat.getTimeInstance(DateFormat.SHORT).format(startTime.getTime());

                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        String timeshow = sd.format(startTime.getTime());
                        String labelshow = label.getText().toString().trim();

                        editor.putString("time", timeshow1);
                        editor.putString("label", labelshow);
                        editor.commit();

                        String retirveTime = retrieve.getString("time", "Reminder Time");
                        String retriveLabel = retrieve.getString("label", "Reminder Label");

                        timeShow.setText(retirveTime);
                        labelShow.setText(retriveLabel);


                        if (labelshow.isEmpty()) {
                            label.setError("enter the subject");
                        } else {
                            Reminder reminder = new Reminder(retreiveFcmToken, timeshow, labelshow, true);
                            viewModel.setReminder(reminder);
                            viewModel.getReminderResponse().observe(getActivity(), data -> {
                                if (data == null) {
                                    Log.i("ApiCall", "Failed");
                                } else {
                                   editor.putString("ID", data.getReminder().get_id());
                                   editor.commit();
                                    Log.i("ApiCall", "successFull");
                                }
                            });
                        }
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
                String idOfResponse=retrieve.getString("ID","");
                viewModel.setcancelReminder(idOfResponse);
                viewModel.getIdresponse().observe(getActivity(), data -> {
                    if (data == null) {
                        Log.i("ApiCallCancel", "Failed");
                    } else {

                        Log.i("ApiCallCancel", "successFull");
                    }
                });

                Toast.makeText(getContext(), "Reminder Cancelled", Toast.LENGTH_SHORT).show();

                timeShow.setText("Set a Reminder");
                labelShow.setText("Reminder Label");
            }
        });

        String retirveTime = retrieve.getString("time", "Set a Reminder");
        String retriveLabel = retrieve.getString("label", "Reminder Label");

        timeShow.setText(retirveTime);
        labelShow.setText(retriveLabel);

        Animation scale_fab = AnimationUtils.loadAnimation(getContext(), R.anim.scale_fab);

        mFloatingActionButton.setAnimation(scale_fab);

        return view;
    }
}
