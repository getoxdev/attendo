package com.example.attendo.ui.main.drawers;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.attendo.R;
import com.example.attendo.model.Alarm;
import com.example.attendo.service.LoadAlarmsReceiver;
import com.example.attendo.service.LoadAlarmsService;
import com.example.attendo.ui.adapter.AlarmsAdapter;
import com.example.attendo.util.AlarmUtils;
import com.example.attendo.view.EmptyRecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.example.attendo.ui.main.drawers.AddEditAlarmActivity.ADD_ALARM;
import static com.example.attendo.ui.main.drawers.AddEditAlarmActivity.buildAddEditAlarmActivityIntent;
/*
public final class RemainderMainFragment extends Fragment
        implements LoadAlarmsReceiver.OnAlarmsLoadedListener {

        private LoadAlarmsReceiver mReceiver;
        private AlarmsAdapter mAdapter;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                mReceiver = new LoadAlarmsReceiver(this);
        }
}

@Nullable
@Override
public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

final View v = inflater.inflate(R.layout.fragment_remainder_main, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Class Reminder");

final EmptyRecyclerView rv = v.findViewById(R.id.recycler);
        mAdapter = new AlarmsAdapter();
        rv.setEmptyView(v.findViewById(R.id.empty_view));
        rv.setAdapter(mAdapter);
        rv.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());

final FloatingActionButton fab = v.findViewById(R.id.fab);
//        fab.setOnClickListener(view -> {
//        AlarmUtils.checkAlarmPermissions(getActivity());
//final Intent i = buildAddEditAlarmActivityIntent(getContext(), ADD_ALARM);
//        startActivity(i);
//        });

        fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        BottomSheetDialog addReminderBottomSheet = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);
                        View bottomsheet = LayoutInflater.from(getContext()).inflate(R.layout.time_picker_spinner_bottom_sheet,
                                (ConstraintLayout) v.findViewById(R.id.time_picker_container));
                        addReminderBottomSheet.setContentView(bottomsheet);
                        addReminderBottomSheet.show();

                }
        });

        return v;

        }

@Override
public void onStart() {
        super.onStart();
final IntentFilter filter = new IntentFilter(LoadAlarmsService.ACTION_COMPLETE);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mReceiver, filter);
        LoadAlarmsService.launchLoadAlarmsService(getContext());
        }

@Override
public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mReceiver);
        }

@Override
public void onAlarmsLoaded(ArrayList<Alarm> alarms) {
        mAdapter.setAlarms(alarms);
        }

        }

 */






