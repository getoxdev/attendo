package com.attendo.Schedule.Adapters;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.attendo.Schedule.CrViewPager.FridayCr;
import com.attendo.Schedule.CrViewPager.MondayCr;
import com.attendo.Schedule.CrViewPager.SaturdayCr;
import com.attendo.Schedule.CrViewPager.SundayCr;
import com.attendo.Schedule.CrViewPager.ThursdayCr;
import com.attendo.Schedule.CrViewPager.TuesdayCr;
import com.attendo.Schedule.CrViewPager.WednesdayCr;

import java.util.ArrayList;
import java.util.List;

public class ViewPgaeradapterCr extends FragmentStateAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    Activity activity;

    public ViewPgaeradapterCr(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        activity = fragmentActivity;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 1:
                ((AppCompatActivity) activity).getSupportActionBar().setTitle("Monday");
                return new MondayCr();
            case 2:
                ((AppCompatActivity) activity).getSupportActionBar().setTitle("Tuesday");
                return new TuesdayCr();
            case 3:
                ((AppCompatActivity) activity).getSupportActionBar().setTitle("Wednesday");
                return new WednesdayCr();
            case 4:
                ((AppCompatActivity) activity).getSupportActionBar().setTitle("Thursday");
                return new ThursdayCr();
            case 5:
                ((AppCompatActivity) activity).getSupportActionBar().setTitle("Friday");
                return new FridayCr();
            case 6:
                ((AppCompatActivity) activity).getSupportActionBar().setTitle("Saturday");
                return new SaturdayCr();
            default:
                ((AppCompatActivity) activity).getSupportActionBar().setTitle("Sunday");
                return new SundayCr();
        }


    }

    @Override
    public int getItemCount() {
        return 7;
    }


}