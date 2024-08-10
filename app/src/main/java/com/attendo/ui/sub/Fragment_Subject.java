package com.attendo.ui.sub;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.attendo.R;
import com.attendo.data.sub.SubEntity;
import com.attendo.databinding.FragmentSubjectBinding;
import com.attendo.viewmodel.SubjectViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Subject extends Fragment {

    private FragmentSubjectBinding binding;

    private static final int NEW_SUBJECT_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_SUBJECT_ACTIVITY_REQUEST_CODE = 2;
    private SubjectViewModel subViewModel;
    private SubListAdapter subListAdapter;
    private List<SubEntity> mSubjects = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSubjectBinding.inflate(inflater, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Subjects");
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(View.VISIBLE);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddSubjectBottomSheetDialogFragment bottomSheetDialogFragment = new AddSubjectBottomSheetDialogFragment();
                bottomSheetDialogFragment.show(getParentFragmentManager(), "Add_Fragment");

            }

        });


        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.recycler_view_layout_anim);

        Animation fadeIN = AnimationUtils.loadAnimation(getContext(), R.anim.fade_card);
        //RecyclerView recyclerView = view.findViewById(R.id.recyclerview);


        subListAdapter = new SubListAdapter(getActivity(),mSubjects,fetchVAlue());
        subViewModel = new ViewModelProvider(this).get(SubjectViewModel.class);
        subViewModel.getAllSubjects().observe(getActivity(), new Observer<List<SubEntity>>() {
            @Override
            public void onChanged(@Nullable List<SubEntity> subjects) {
                subListAdapter.setSubjects(subjects);
                //recyclerView.setLayoutAnimation(animationController);

                if(subjects.isEmpty()) {
                    binding.helpTextSubject.setAnimation(fadeIN);
                    binding.helpTextSubject.setVisibility(View.VISIBLE);
                    binding.subjectLottieAnimationUnique.setAnimation(fadeIN);
                    binding.subjectLottieAnimationUnique.setVisibility(View.VISIBLE);
                } else {
                    binding.helpTextSubject.setVisibility(View.GONE);
                    binding.subjectLottieAnimationUnique.setVisibility(View.GONE);
                }
            }
        });


        binding.recyclerview.setAdapter(subListAdapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        Animation scale = AnimationUtils.loadAnimation(getContext(), R.anim.scale_fab);
        binding.fab.setAnimation(scale);

        return binding.getRoot();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.commit();
    }

    public String fetchVAlue()
    {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Mypref",getContext().MODE_PRIVATE);
        String value = sharedPreferences.getString("Criterion","75");
        return value;


    }

}