package com.attendo.Schedule;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.data.model.schedule.Notice;
import com.attendo.ui.CustomLoadingDialog;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.attendo.viewmodel.NoticeViewModel;
import com.attendo.viewmodel.ScheduleViewModel;

public class AddNoticeFragment extends Fragment {

    private EditText title,body;
    private Button btn;
    private NoticeViewModel noticeViewModel;
    private CustomLoadingDialog customLoadingDialog;
    private AppPreferences appPreferences;
    private NoticeFragment noticeFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_notice, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Notice");

        noticeFragment = new NoticeFragment();
        title = view.findViewById(R.id.Title);
        body = view.findViewById(R.id.body);
        btn = view.findViewById(R.id.btn_send);
        customLoadingDialog = new CustomLoadingDialog(getActivity());
        appPreferences = AppPreferences.getInstance(getContext());
        noticeViewModel = new ViewModelProvider(this).get(NoticeViewModel.class);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().length()==0 || body.getText().toString().length()==0){
                    Toast.makeText(getActivity(),"Please fill the details",Toast.LENGTH_SHORT).show();
                }
                else{
                    customLoadingDialog.startDialog(false);
                    sendatatoserver();
                }
            }
        });

        return  view;
    }

    private void sendatatoserver() {
        Notice notice = new Notice(title.getText().toString(),body.getText().toString());
        noticeViewModel.create_Notice(appPreferences.RetrieveClassId(),notice);
        noticeViewModel.get_Notice_Response().observe(getActivity(), data -> {
            if (data == null) {
                customLoadingDialog.dismissDialog();
                Toast.makeText(getActivity(),"Fail to Add Schedule",Toast.LENGTH_SHORT).show();
                Log.i("ApiCall", "Failed");
            } else {
                customLoadingDialog.dismissDialog();
                Toast.makeText(getActivity(),"Notice Added Successfully",Toast.LENGTH_SHORT).show();
                setFragment(new NoticeFragment());
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}