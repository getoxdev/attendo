package com.attendo.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class CrFragmentCollectionAdapter extends FragmentStatePagerAdapter {
    public CrFragmentCollectionAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        CrScheduleFragment crScheduleFragment = new CrScheduleFragment();
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
        crScheduleFragment.setArguments(bundle);
        return crScheduleFragment;
    }

    @Override
    public int getCount() {
        return 7;
    }
}
