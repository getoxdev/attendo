package com.attendo.Schedule.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    public ViewPgaeradapterCr(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Fragment selectedFragment = new Fragment();
        switch (position){
            case 0:
                selectedFragment = new SundayCr();
                break;
            case 1:
                selectedFragment = new MondayCr();
                break;
            case 2:
                selectedFragment =  new TuesdayCr();
                break;
            case 3:
                selectedFragment = new WednesdayCr();
                break;
            case 4:
                selectedFragment =  new ThursdayCr();
                break;
            case 5:
                selectedFragment =  new FridayCr();
                break;
            case 6:
                selectedFragment =  new SaturdayCr();
                break;
        }

        return selectedFragment;
    }

    @Override
    public int getItemCount() {
        return 7;
    }


}