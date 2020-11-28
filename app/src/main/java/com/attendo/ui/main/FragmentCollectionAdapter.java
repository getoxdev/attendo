package com.attendo.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class FragmentCollectionAdapter extends FragmentStatePagerAdapter {
    public FragmentCollectionAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        ScheduleFragment scheduleFragment = new ScheduleFragment();
        Bundle bundle = new Bundle();
        position = position+1;
        String day = "Monday";
        if(position == 2)
            day = "Tuesday";
        if(position == 3)
            day = "Wednesday";
        if(position == 4)
            day = "Thusday";
        if(position == 5)
            day = "Friday";
        if(position == 6)
            day = "Saturday";
        if(position == 7)
            day = "Sunday";
        bundle.putString("message",day+" : "+position);
        scheduleFragment.setArguments(bundle);
        return scheduleFragment;
    }

    @Override
    public int getCount() {
        return 7;
    }
}
