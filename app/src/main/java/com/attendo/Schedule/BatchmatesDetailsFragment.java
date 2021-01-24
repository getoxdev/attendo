package com.attendo.Schedule;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.attendo.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BatchmatesDetailsFragment extends Fragment {

    @BindView(R.id.batchmates_list_RV)
    RecyclerView batchmatesRV;

    @BindView(R.id.no_of_students_int)
    TextView noOfStudents;

    @BindView(R.id.batchmates_swipe_refresh)
    SwipeRefreshLayout refreshLayout;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public BatchmatesDetailsFragment() {
        // Required empty public constructor
    }

    public static BatchmatesDetailsFragment newInstance(String param1, String param2) {
        BatchmatesDetailsFragment fragment = new BatchmatesDetailsFragment();
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
        View view =  inflater.inflate(R.layout.fragment_batchmates_details, container, false);
        ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Classmates");

        return view;
    }
}