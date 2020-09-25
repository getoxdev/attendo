package com.attendo.ui.main.drawers;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.attendo.R;

public class FragmentHelp extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Help");
        CardView info, faq;
        FragmentInfo fragmentinfo = new FragmentInfo();
        FragmentFAQ fragmentfaq = new FragmentFAQ();
        info = view.findViewById(R.id.info);
        faq = view.findViewById(R.id.faq);
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpFragment1(fragmentfaq);
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpFragment(fragmentinfo);
            }
        });
        return view;

    }

    private void helpFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame, fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

    private void helpFragment1(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame, fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }
}


