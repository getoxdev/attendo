package com.attendo.ui.main.drawers.reminder;

import android.app.PendingIntent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.airbnb.lottie.LottieAnimationView;
import com.attendo.R;
import com.attendo.data.api.ApiHelper;
import com.attendo.data.model.reminder.Reminder;
import com.attendo.data.rem.RemEntity;
import com.attendo.databinding.FragmentExamReminderBinding;
import com.attendo.viewmodel.ReminderViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;



public class FragmentReminder extends Fragment {

    FragmentExamReminderBinding binding;

    FloatingActionButton mFloatingActionButton;
    RecyclerView recyclerView;
    LottieAnimationView lottieAnimationView;

    TextView noReminder;

    TimePicker timePicker;
    EditText label;

    private String mylabel;
    private Bundle bundle;
    private String fcmToken;
    private ReminderViewModel viewModel;
    private ApiHelper apiHelper;
    private String retreiveFcmToken;
    private List<RemEntity> reminders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
         binding = FragmentExamReminderBinding.inflate(inflater,container,false);
          recyclerView = binding.remRecycler;
          lottieAnimationView = binding.noReminderLottie;
          noReminder = binding.noReminderTxtview;


        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Reminder");
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        //set animation for the lottie anim
        Animation fadeIN = AnimationUtils.loadAnimation(getContext(), R.anim.fade_card);

        ReminderAdapter adapter = new ReminderAdapter(getContext());
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(getActivity()).get(ReminderViewModel.class);
        apiHelper = ApiHelper.getInstance(getContext());

        viewModel.getAllReminders().observe(getActivity(), new Observer<List<RemEntity>>() {
            @Override
            public void onChanged(List<RemEntity> remEntities) {
                if(remEntities.isEmpty()){
                    //code to show no data sign
                    lottieAnimationView.setAnimation(fadeIN);
                    noReminder.setAnimation(fadeIN);
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    noReminder.setVisibility(View.VISIBLE);
                }else{
                    //code to hide no data sign
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                    noReminder.setVisibility(View.INVISIBLE);
                }
                adapter.setReminders(remEntities);
            }
        });

        //set fab icon animation
        Animation scale = AnimationUtils.loadAnimation(getContext(), R.anim.scale_fab);
        binding.addRem.setAnimation(scale);


        //deletePreviousReminders(adapter);

        SharedPreferences preferences = getContext().getSharedPreferences("MYPREF", 0);
        SharedPreferences.Editor editor = preferences.edit();

        SharedPreferences retrieve = getContext().getSharedPreferences("MYPREF", 0);


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(getActivity(), new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                fcmToken = instanceIdResult.getToken();
                editor.putString("fcmToken", fcmToken);
                editor.commit();
                retreiveFcmToken = retrieve.getString("fcmToken", "");
                Log.i("My FCM Token", retreiveFcmToken);
            }
        });

        bundle = new Bundle();

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);

        View bottomSheet = LayoutInflater.from(getContext()).inflate(R.layout.time_picker_spinner_bottom_sheet,
                (ConstraintLayout) binding.getRoot().findViewById(R.id.time_picker_container));
        bottomSheetDialog.setContentView(bottomSheet);
        bottomSheetDialog.setDismissWithAnimation(true);

        binding.addRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.show();
                timePicker = bottomSheet.findViewById(R.id.timePicker);
                label = bottomSheet.findViewById(R.id.reminder_label);
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

                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:00'Z'", Locale.getDefault());
                        SimpleDateFormat gd = new SimpleDateFormat("HHmm", Locale.getDefault());
                        String timeshow = sd.format(startTime.getTime());
                        String labelshow = label.getText().toString().trim();

                        Date date = null;
                        try {
                            date = sd.parse(timeshow);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String requestCodeString = gd.format(date);
                        Integer requestCode = Integer.valueOf(requestCodeString);

                        RemEntity rem = new RemEntity(timeshow, labelshow);
                        viewModel.insert(rem);
                        viewModel.setReminder(requestCode, timeshow, labelshow);

                        label.setText("");
                        bottomSheetDialog.dismiss();
                    }
                });
            }
        });

        return binding.getRoot();
    }
}
