package com.attendo.Schedule.CrViewPager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.attendo.R;
import com.attendo.Schedule.Adapters.ViewPgaeradapterCr;
import com.attendo.Schedule.CrViewPager.MondayCr;
import com.attendo.Schedule.CrViewPager.SundayCr;
import com.attendo.Schedule.CrViewPager.TuesdayCr;

import java.util.ArrayList;
import java.util.List;

public class CrFragmentViewPager extends Fragment {


    ViewPager2 viewPager2;
    ViewPgaeradapterCr viewPgaeradapterCr;
    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cr_view_pager, container, false);

        viewPager2 = view.findViewById(R.id.view_pager_2_cr);
        viewPgaeradapterCr = new ViewPgaeradapterCr(getActivity());


        return view;
    }
}