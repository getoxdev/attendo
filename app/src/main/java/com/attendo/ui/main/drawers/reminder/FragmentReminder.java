package com.attendo.ui.main.drawers.reminder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.attendo.R;
import com.attendo.data.api.ApiHelper;
import com.attendo.data.rem.RemEntity;
import com.attendo.databinding.FragmentExamReminderBinding;
import com.attendo.viewmodel.ReminderViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FragmentReminder extends Fragment {

    private FragmentExamReminderBinding binding;
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
        binding = FragmentExamReminderBinding.inflate(inflater, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Reminder");
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(View.VISIBLE);

        binding.remRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.remRecycler.setHasFixedSize(true);

        //set animation for the lottie anim
        Animation fadeIN = AnimationUtils.loadAnimation(getContext(), R.anim.fade_card);

        ReminderAdapter adapter = new ReminderAdapter(getContext());
        binding.remRecycler.setAdapter(adapter);

        viewModel = ViewModelProviders.of(getActivity()).get(ReminderViewModel.class);
        apiHelper = ApiHelper.getInstance(getContext());

        viewModel.getAllReminders().observe(getActivity(), new Observer<List<RemEntity>>() {
            @Override
            public void onChanged(List<RemEntity> remEntities) {
                if (remEntities.isEmpty()) {
                    //code to show no data sign
                    binding.noReminderLottie.setAnimation(fadeIN);
                    binding.noReminderTxtview.setAnimation(fadeIN);
                    binding.noReminderLottie.setVisibility(View.VISIBLE);
                    binding.noReminderTxtview.setVisibility(View.VISIBLE);
                } else {
                    //code to hide no data sign
                    binding.noReminderLottie.setVisibility(View.INVISIBLE);
                    binding.noReminderTxtview.setVisibility(View.INVISIBLE);
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


        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                try {
                    fcmToken = task.getResult();
                    editor.putString("fcmToken", fcmToken);
                    editor.commit();
                    retreiveFcmToken = retrieve.getString("fcmToken", "");
                    Log.i("My FCM Token", retreiveFcmToken);
                } catch (Exception e) {
                    Log.i("reminder", e.getLocalizedMessage());
                }
            }
        });

        bundle = new Bundle();

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);


        View bottomSheet = LayoutInflater.from(getContext()).inflate(R.layout.time_picker_spinner_bottom_sheet, binding.getRoot().findViewById(R.id.time_picker_container));
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