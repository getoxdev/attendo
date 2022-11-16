package com.attendo.Schedule;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.attendo.R;
import com.attendo.Schedule.Adapters.BatchmatesListAdapter;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.databinding.FragmentBatchmatesDetailsBinding;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.transition.MaterialSharedAxis;

import org.w3c.dom.Text;



public class BatchmatesDetailsFragment extends Fragment {
    FragmentBatchmatesDetailsBinding binding;



    private BatchmatesListAdapter adapter;
    private ScheduleViewModel scheduleViewModel;
    private AppPreferences preferences;


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

        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        preferences = AppPreferences.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentBatchmatesDetailsBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Classmates");
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(View.GONE);

        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false));

        //setting adapter to feed the data
        scheduleViewModel.GetStudentList(preferences.RetrieveClassId());
        scheduleViewModel.getStudentListResponse().observe(getViewLifecycleOwner(), data->{
            if(data != null){
                adapter = new BatchmatesListAdapter(getContext(), data.getStudents());
                binding.batchmatesListRV.setAdapter(adapter);

                //set text for total number of students enrolled
                binding.noOfStudentsInt.setText(String.valueOf(data.getStudents().size()));
            }
        });


        //on refresh listener
        binding.batchmatesSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateOnRefresh();
            }
        });



        return binding.getRoot();
    }

    private void updateOnRefresh() {
        scheduleViewModel.GetStudentList(preferences.RetrieveClassId());
        scheduleViewModel.getStudentListResponse().observe(getViewLifecycleOwner(), data->{
            if(data != null){
                binding.batchmatesSwipeRefresh.setRefreshing(false);
                adapter = new BatchmatesListAdapter(getContext(), data.getStudents());
                adapter.notifyDataSetChanged();
               binding.batchmatesListRV.setAdapter(adapter);

                // set text for total number of students enrolled
                binding.noOfStudentsInt.setText(String.valueOf(data.getStudents().size()));
            }
        });
    }


}