package com.attendo.Schedule;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.attendo.R;
import com.attendo.Schedule.Adapters.NoticeAdapter;
import com.attendo.Schedule.Model.Notice;

import java.util.ArrayList;
import java.util.List;

public class NoticeFragment extends Fragment {

    private TextView sub,detail;
    private RecyclerView recyclerView;
    private NoticeAdapter noticeAdapter;
    private List<Notice> item;
    private CardView schedule;
    private StudentFragment studentFragment;


   private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public NoticeFragment() {
        // Required empty public constructor
    }
  public static NoticeFragment newInstance(String param1, String param2) {
        NoticeFragment fragment = new NoticeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        studentFragment = new StudentFragment();
        schedule = view.findViewById(R.id.schedule_card);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(studentFragment);
            }
        });

        sub = view.findViewById(R.id.SubjectName);
        detail = view.findViewById(R.id.detail);

        recyclerView = view.findViewById(R.id.Notice_Recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        item = new ArrayList<>();

        item.add(new Notice("Mathematics","Minor test on 15th January"));
        item.add(new Notice("Analog","Minor result on 14th January"));
        item.add(new Notice("DSC","Orientation on 3rd January at 6:00 pm"));
        item.add(new Notice("Competative Coding"," String, Searching, Shorting, Greedy, Dynamic Programming   String, Searching, Shorting, Greedy, Dynamic Programming      String, Searching, Shorting, Greedy, Dynamic Programming"));
        item.add(new Notice("Signal and System","Class Cancel on 7th January"));
        item.add(new Notice("Mechanics","Last day of assignment submission 15th January"));
        item.add(new Notice("Mechanics","Extra Class on 10th January form 11:00am"));

        noticeAdapter = new NoticeAdapter(item);
        recyclerView.setAdapter(noticeAdapter);


        return  view;
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

}