package com.example.attendo.ui.calendar;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendo.R;
import com.example.attendo.data.CalendarEntity;
import com.example.attendo.data.SubEntity;
import com.example.attendo.ui.main.BottomNavMainActivity;
import com.example.attendo.ui.main.MainActivity;
import com.example.attendo.ui.sub.SubListAdapter;
import com.example.attendo.viewmodel.CalViewModel;
import com.example.attendo.viewmodel.SubjectViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentCalender extends Fragment {



    @BindView(R.id.CalRecyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.selected_date)
    TextView selected_date;

    @BindView(R.id.calendar_fragment)
    CalendarView calendar;


    private CalAdapter calAdapter;
    private CalViewModel calViewModel;
    List<CalendarEntity> mDataList;
    private Context mcontext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_calender, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Calendar");
        ButterKnife.bind(this,view);
        //Date date = Calendar.getInstance().getTime();
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat(" HH:mm:ss dd MMMM yyyy");
        String currentdate=sdf.format(calendar.getTime());
        selected_date.setText(currentdate);

        calAdapter = new CalAdapter(getContext(),mDataList);
        calViewModel = new ViewModelProvider((BottomNavMainActivity)getContext()).get(CalViewModel.class);

        calViewModel.getitem().observe(getActivity(), new Observer<List<CalendarEntity>>() {
            @Override
            public void onChanged(List<CalendarEntity> calendarEntities) {
                calAdapter.set(calendarEntities);

            }
        });
        recyclerView.setAdapter(calAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;

    }
}