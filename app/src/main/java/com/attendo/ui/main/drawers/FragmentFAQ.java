package com.attendo.ui.main.drawers;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.attendo.R;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class FragmentFAQ extends Fragment {
    private LinearLayout faq;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("FAQ");

        faq = view.findViewById(R.id.faq_liner_layout);
        OverScrollDecoratorHelper.setUpStaticOverScroll(faq, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        return view;
    }

}