package com.attendo.ui.sub;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Vibrator;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.attendo.R;
import com.attendo.data.sub.SubEntity;
import com.attendo.viewmodel.SubjectViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_Subject extends Fragment {

    private static final int NEW_SUBJECT_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_SUBJECT_ACTIVITY_REQUEST_CODE = 2;
    private SubjectViewModel subViewModel;
    private SubListAdapter subListAdapter;
    private List<SubEntity> mSubjects=new ArrayList<>();


    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.subject_lottie_animation_unique)
    LottieAnimationView subjectanim;

    @BindView(R.id.help_text_subject)
    TextView helpText;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Fragment_Subject() {
        // Required empty public constructor
    }

    public static Fragment_Subject newInstance(String param1, String param2) {
        Fragment_Subject fragment = new Fragment_Subject();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment__subject, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Subjects");


        ButterKnife.bind(this,view);



        fab.setOnClickListener(new View.OnClickListener() {
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

                if(subjects.isEmpty()){
                    helpText.setAnimation(fadeIN);
                    subjectanim.setAnimation(fadeIN);
                    helpText.setVisibility(View.VISIBLE);
                    subjectanim.setVisibility(View.VISIBLE);
                }
                else{
                    helpText.setVisibility(View.GONE);
                    subjectanim.setVisibility(View.GONE);
                }
            }
        });


        recyclerView.setAdapter(subListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Animation scale = AnimationUtils.loadAnimation(getContext(), R.anim.scale_fab);
        fab.setAnimation(scale);


//
     return  view;
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