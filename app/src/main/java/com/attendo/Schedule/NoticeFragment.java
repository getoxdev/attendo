package com.attendo.Schedule;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.attendo.R;
import com.attendo.Schedule.Adapters.NoticeAdapter;
import com.attendo.Schedule.Model.Notice;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class NoticeFragment extends Fragment {

    private RecyclerView recyclerView;
    private NoticeAdapter noticeAdapter;
    private ArrayList<Notice> notices;
    private FloatingActionButton fb;
    private AddNoticeFragment addNoticeFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Notice");

        addNoticeFragment = new AddNoticeFragment();
        recyclerView = view.findViewById(R.id.notice_recyclerview);
        fb = view.findViewById(R.id.Add_Notice);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        notices = new ArrayList<>();

        notices.add(new Notice("Android class"));
        notices.add(new Notice("Maths surprise test!"));
        notices.add(new Notice("Analog class cancel!"));
        notices.add(new Notice("Google Meet link"));
        notices.add(new Notice("Project submission date"));

        noticeAdapter = new NoticeAdapter(notices);
        recyclerView.setAdapter(noticeAdapter);

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