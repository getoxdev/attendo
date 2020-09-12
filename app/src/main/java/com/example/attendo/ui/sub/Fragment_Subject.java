package com.example.attendo.ui.sub;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.attendo.R;
import com.example.attendo.data.SubEntity;
import com.example.attendo.viewmodel.SubjectViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import butterknife.BindView;

public class Fragment_Subject extends Fragment {

    private SubjectViewModel subViewModel;
    private SubListAdapter subListAdapter;
    private List<SubEntity> mSubjects=new ArrayList<>();

    private Fragment_AddSubject fragment_addSubject;

    @BindView(R.id.tvPres)
    TextView present;

    @BindView(R.id.tvTotal)
    TextView total;

    @BindView(R.id.recyclerview)
    RecyclerView subRView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment__subject, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Subjects");

        final Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        fragment_addSubject = new Fragment_AddSubject();

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);
                View bottomSheet = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_sheet_add_subject,
                        (ConstraintLayout) view.findViewById(R.id.bottom_sheet_add_subject_container));
                bottomSheetDialog.setContentView(bottomSheet);
                bottomSheetDialog.setDismissWithAnimation(true);
                bottomSheetDialog.show();

                EditText subjectName = bottomSheetDialog.findViewById(R.id.add_subject_bottomsheet);
                Button addButton = bottomSheetDialog.findViewById(R.id.add_subject_btn);
                Button cancelButton = bottomSheetDialog.findViewById(R.id.cancel_subject_button);
                TextView update = bottomSheetDialog.findViewById(R.id.add_subject_id);
                LottieAnimationView celebration = bottomSheetDialog.findViewById(R.id.lottie_animation_add_subject);
                celebration.setVisibility(View.INVISIBLE);

                update.setText("Add Subject");

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(subjectName.getText().toString() != null)
                        {
                            SubEntity subEntity = new SubEntity(subjectName.getText().toString().trim(), 0, 0, 0);
                            subViewModel.insertSubject(subEntity);
                            celebration.setVisibility(View.VISIBLE);
                            celebration.playAnimation();
                            Handler mhandler = new Handler();
                            mhandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    bottomSheetDialog.dismiss();
                                }
                            },600);
                        }
                        else{
                            Toast.makeText(getContext(), "Please enter the subject name", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.setDismissWithAnimation(true);
                        bottomSheetDialog.dismiss();
                    }
                });

                 }

        });

        subListAdapter = new SubListAdapter(getActivity(),mSubjects);
        subViewModel = new ViewModelProvider(this).get(SubjectViewModel.class);
        subViewModel.getAllSubjects().observe(getActivity(), new Observer<List<SubEntity>>() {
            @Override
            public void onChanged(@Nullable List<SubEntity> subjects) {
                subListAdapter.setSubjects(subjects);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(subListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

     return  view;
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.commit();
    }

}