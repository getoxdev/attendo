package com.example.attendo.ui.calendar;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendo.R;
import com.example.attendo.data.CalendarEntity;
import com.example.attendo.data.DateConverter;
import com.example.attendo.data.SubEntity;
import com.example.attendo.ui.main.BottomNavMainActivity;
import com.example.attendo.ui.main.MainActivity;
import com.example.attendo.ui.sub.SubListAdapter;
import com.example.attendo.viewmodel.CalViewModel;
import com.example.attendo.viewmodel.SubjectViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentCalender extends Fragment {


    @BindView(R.id.CalRecyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.selected_date)
    TextView selectedDate;

    @BindView(R.id.calendar_fragment)
    CalendarView calendar;

    @BindView(R.id.my_bottom_sheet_calendar)
    RelativeLayout calendarRecycler;

    private CalAdapter calAdapter;
    private CalViewModel calViewModel;
    private DateConverter dateConverter;
    private String subDate;
    private List<String> mDataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calender, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Calendar");
        ButterKnife.bind(this, view);

        dateConverter = new DateConverter();

        subDate = formatter(dateConverter.fromTimestamp(calendar.getDate()));
        selectedDate.setText(subDate);

        //bottomnavigationbehaviour
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(calendarRecycler);
        bottomSheetBehavior.setFitToContents(false);
        bottomSheetBehavior.setHalfExpandedRatio(0.5f);


        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                if(month<9)
                {
                    selectedDate.setText(dayOfMonth+"-"+"0"+(month+1)+"-"+year);
                    calAdapter = new CalAdapter(getActivity(),mDataList);
                    calViewModel = new ViewModelProvider(getActivity()).get(CalViewModel.class);
                    calViewModel.getSub(dayOfMonth+"-"+"0"+(month+1)+"-"+year).observe(getActivity(), new Observer<List<String>>() {
                        @Override
                        public void onChanged(List<String> strings) {
                            calAdapter.setData(strings);

                        }
                    });
                    recyclerView.setAdapter(calAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                }
                else
                {
                    selectedDate.setText(dayOfMonth+"-"+(month+1)+"-"+year);

                    Log.e("date",subDate);
                    calAdapter = new CalAdapter(getActivity(),mDataList);
                    calViewModel = new ViewModelProvider(getActivity()).get(CalViewModel.class);
                    calViewModel.getSub(dayOfMonth+"-"+(month+1)+"-"+year).observe(getActivity(), new Observer<List<String>>() {
                        @Override
                        public void onChanged(List<String> strings) {
                            calAdapter.setData(strings);

                        }
                    });
                    recyclerView.setAdapter(calAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));}

            }
        });

        calAdapter = new CalAdapter(getActivity(), mDataList);
        calViewModel = new ViewModelProvider(getActivity()).get(CalViewModel.class);
        calViewModel.getSub(subDate).observe(getActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> data) {
                calAdapter.setData(data);
            }
        });

        recyclerView.setAdapter(calAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    public static String formatter(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String subDate = dateFormat.format(date);

        return  subDate;
    }
}