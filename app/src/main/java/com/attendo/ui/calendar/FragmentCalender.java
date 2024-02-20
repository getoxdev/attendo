package com.attendo.ui.calendar;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.CalendarView;
import com.attendo.R;
import com.attendo.data.DateConverter;
import com.attendo.databinding.FragmentCalenderBinding;
import com.attendo.viewmodel.CalViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FragmentCalender extends Fragment {
    private CalAdapter calAdapter;
    private CalViewModel calViewModel;
    private DateConverter dateConverter;
    private String subDate;
    private FragmentCalenderBinding binding;
    private List<String> mDataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentCalenderBinding.inflate(inflater, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Calendar");
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(View.VISIBLE);


        dateConverter = new DateConverter();

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.recycler_view_layout_anim);

        subDate = formatter(dateConverter.fromTimestamp(binding.calendarFragment.getDate()));
        binding.selectedDate.setText(subDate);

        //bottomnavigationbehaviour
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(binding.myBottomSheetCalendar);
        bottomSheetBehavior.setFitToContents(false);
        bottomSheetBehavior.setHalfExpandedRatio(0.5f);

        Animation bototmSheetPeek = AnimationUtils.loadAnimation(getContext(), R.anim.calendar_bottom_sheet_peek);
        binding.myBottomSheetCalendar.setAnimation(bototmSheetPeek);



        binding.calendarFragment.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                binding.CalRecyclerview.setLayoutAnimation(controller);

                if(month<9)
                {
                    if(dayOfMonth<=9)
                    {
                        binding.selectedDate.setText("0"+dayOfMonth+"-"+"0"+(month+1)+"-"+year);
                        calAdapter = new CalAdapter(getActivity(),mDataList);
                        calViewModel = new ViewModelProvider(getActivity()).get(CalViewModel.class);
                        calViewModel.getSub("0"+dayOfMonth+"-"+"0"+(month+1)+"-"+year).observe(getActivity(), new Observer<List<String>>() {
                            @Override
                            public void onChanged(List<String> strings) {
                                calAdapter.setData(strings);


                            }
                        });
                        binding.CalRecyclerview.setAdapter(calAdapter);
                        binding.CalRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));


                    }
                    else {
                        binding.selectedDate.setText(dayOfMonth + "-" + "0" + (month + 1) + "-" + year);
                        calAdapter = new CalAdapter(getActivity(), mDataList);
                        calViewModel = new ViewModelProvider(getActivity()).get(CalViewModel.class);
                        calViewModel.getSub(dayOfMonth + "-" + "0" + (month + 1) + "-" + year).observe(getActivity(), new Observer<List<String>>() {
                            @Override
                            public void onChanged(List<String> strings) {
                                calAdapter.setData(strings);

                            }
                        });
                        binding.CalRecyclerview.setAdapter(calAdapter);
                        binding.CalRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }

                }
                else {
                    if (dayOfMonth <= 9) {
                        binding.selectedDate.setText("0"+dayOfMonth + "-" + (month + 1) + "-" + year);

                        Log.e("date", subDate);
                        calAdapter = new CalAdapter(getActivity(), mDataList);
                        calViewModel = new ViewModelProvider(getActivity()).get(CalViewModel.class);
                        calViewModel.getSub("0"+dayOfMonth + "-" + (month + 1) + "-" + year).observe(getActivity(), new Observer<List<String>>() {
                            @Override
                            public void onChanged(List<String> strings) {
                                calAdapter.setData(strings);

                            }
                        });
                        binding.CalRecyclerview.setAdapter(calAdapter);
                        binding.CalRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }
                    else
                    {
                        binding.selectedDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);

                        Log.e("date", subDate);
                        calAdapter = new CalAdapter(getActivity(), mDataList);
                        calViewModel = new ViewModelProvider(getActivity()).get(CalViewModel.class);
                        calViewModel.getSub(dayOfMonth + "-" + (month + 1) + "-" + year).observe(getActivity(), new Observer<List<String>>() {
                            @Override
                            public void onChanged(List<String> strings) {
                                calAdapter.setData(strings);

                            }
                        });
                        binding.CalRecyclerview.setAdapter(calAdapter);
                        binding.CalRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

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

        binding.CalRecyclerview.setAdapter(calAdapter);
        binding.CalRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        return binding.getRoot();
    }

    public static String formatter(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String subDate = dateFormat.format(date);

        return  subDate;
    }
}