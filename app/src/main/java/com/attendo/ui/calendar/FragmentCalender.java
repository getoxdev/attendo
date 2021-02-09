package com.attendo.ui.calendar;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.CalendarView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.attendo.R;
import com.attendo.data.DateConverter;
import com.attendo.viewmodel.CalViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.xmlpull.v1.XmlPullParser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindString;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calender, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Calendar");
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(View.VISIBLE);
        ButterKnife.bind(this, view);

        dateConverter = new DateConverter();

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.recycler_view_layout_anim);

        subDate = formatter(dateConverter.fromTimestamp(calendar.getDate()));
        selectedDate.setText(subDate);

        //bottomnavigationbehaviour
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(calendarRecycler);
        bottomSheetBehavior.setFitToContents(false);
        bottomSheetBehavior.setHalfExpandedRatio(0.5f);

        Animation bototmSheetPeek = AnimationUtils.loadAnimation(getContext(), R.anim.calendar_bottom_sheet_peek);
        calendarRecycler.setAnimation(bototmSheetPeek);



        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                recyclerView.setLayoutAnimation(controller);

                if(month<9)
                {
                    if(dayOfMonth<=9)
                    {
                        selectedDate.setText("0"+dayOfMonth+"-"+"0"+(month+1)+"-"+year);
                        calAdapter = new CalAdapter(getActivity(),mDataList);
                        calViewModel = new ViewModelProvider(getActivity()).get(CalViewModel.class);
                        calViewModel.getSub("0"+dayOfMonth+"-"+"0"+(month+1)+"-"+year).observe(getActivity(), new Observer<List<String>>() {
                            @Override
                            public void onChanged(List<String> strings) {
                                calAdapter.setData(strings);


                            }
                        });
                        recyclerView.setAdapter(calAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


                    }
                    else {
                        selectedDate.setText(dayOfMonth + "-" + "0" + (month + 1) + "-" + year);
                        calAdapter = new CalAdapter(getActivity(), mDataList);
                        calViewModel = new ViewModelProvider(getActivity()).get(CalViewModel.class);
                        calViewModel.getSub(dayOfMonth + "-" + "0" + (month + 1) + "-" + year).observe(getActivity(), new Observer<List<String>>() {
                            @Override
                            public void onChanged(List<String> strings) {
                                calAdapter.setData(strings);

                            }
                        });
                        recyclerView.setAdapter(calAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }

                }
                else {
                    if (dayOfMonth <= 9) {
                        selectedDate.setText("0"+dayOfMonth + "-" + (month + 1) + "-" + year);

                        Log.e("date", subDate);
                        calAdapter = new CalAdapter(getActivity(), mDataList);
                        calViewModel = new ViewModelProvider(getActivity()).get(CalViewModel.class);
                        calViewModel.getSub("0"+dayOfMonth + "-" + (month + 1) + "-" + year).observe(getActivity(), new Observer<List<String>>() {
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
                        selectedDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);

                        Log.e("date", subDate);
                        calAdapter = new CalAdapter(getActivity(), mDataList);
                        calViewModel = new ViewModelProvider(getActivity()).get(CalViewModel.class);
                        calViewModel.getSub(dayOfMonth + "-" + (month + 1) + "-" + year).observe(getActivity(), new Observer<List<String>>() {
                            @Override
                            public void onChanged(List<String> strings) {
                                calAdapter.setData(strings);

                            }
                        });
                        recyclerView.setAdapter(calAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    }
                }

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