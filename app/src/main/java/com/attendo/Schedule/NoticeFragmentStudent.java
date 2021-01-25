package com.attendo.Schedule;

import android.os.Bundle;

import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.Schedule.Adapters.NoticeAdapter;
import com.attendo.Schedule.Adapters.NoticeAdapterStudent;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.viewmodel.NoticeViewModel;

public class NoticeFragmentStudent extends Fragment {

    private RecyclerView recyclerView;
    private NoticeAdapterStudent noticeAdapterStudent;
    NoticeViewModel noticeViewModel;
    AppPreferences preferences;
    private ContentLoadingProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noticeViewModel = new ViewModelProvider(this).get(NoticeViewModel.class);
        preferences = AppPreferences.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice_student, container, false);

        recyclerView = view.findViewById(R.id.notice_student_recyclerview);
        progressBar = view.findViewById(R.id.notice_progress_bar_student);

        noticeViewModel.get_All_notice(preferences.RetrieveClassId());
        noticeViewModel.get_all_noticeResponse().observe(getViewLifecycleOwner(),data->{
            if(data!=null)
            {
                progressBar.hide();
                noticeAdapterStudent = new NoticeAdapterStudent(getContext(),data.getNoticeDetailsList());
                recyclerView.setAdapter(noticeAdapterStudent);
            }
            else{
                progressBar.hide();
                Toast.makeText(getActivity(),"Something went wrong! Please try after some time",Toast.LENGTH_SHORT).show();
            }

        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}