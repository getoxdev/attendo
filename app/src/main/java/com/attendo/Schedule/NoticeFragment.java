package com.attendo.Schedule;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.Schedule.Adapters.NoticeAdapter;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.data.model.schedule.NoticeDetails;
import com.attendo.viewmodel.NoticeViewModel;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeFragment extends Fragment {


    private NoticeAdapter noticeAdapter;
    private FloatingActionButton fb;
    NoticeViewModel noticeViewModel;
    private AddNoticeFragment addNoticeFragment;
    AppPreferences preferences;

    @BindView(R.id.notice_recyclerview)
    RecyclerView notice_recyclerview;

    @BindView(R.id.notice_progress_bar_cr)
    ContentLoadingProgressBar ProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noticeViewModel = new ViewModelProvider(this).get(NoticeViewModel.class);
        preferences = AppPreferences.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Notice");


        ButterKnife.bind(this,view);



        addNoticeFragment = new AddNoticeFragment();

        fb = view.findViewById(R.id.Add_Notice);

        noticeViewModel.get_All_notice(preferences.RetrieveClassId());
        noticeViewModel.get_all_noticeResponse().observe(getViewLifecycleOwner(),data->{
            if(data!=null)
            {
                ProgressBar.hide();
                noticeAdapter = new NoticeAdapter(getContext(),data.getNoticeDetailsList());
                notice_recyclerview.setAdapter(noticeAdapter);
            }
            else{
                ProgressBar.hide();
                Toast.makeText(getActivity(),"Something went wrong! Please try after some time",Toast.LENGTH_SHORT).show();
            }
        });
        notice_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));




        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(addNoticeFragment);

            }
        });



    return view;
    }



    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

}