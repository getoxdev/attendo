package com.example.attendo.ui.sub;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import androidx.transition.TransitionManager;

import android.os.Handler;
import android.os.Vibrator;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.attendo.R;
import com.example.attendo.data.SubEntity;
import com.example.attendo.ui.main.BottomNavMainActivity;
import com.example.attendo.viewmodel.SubjectViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.MaterialArcMotion;
import com.google.android.material.transition.MaterialContainerTransform;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import butterknife.BindView;

public class Fragment_Subject extends Fragment {

    private static final int NEW_SUBJECT_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_SUBJECT_ACTIVITY_REQUEST_CODE = 2;
    private SubjectViewModel subViewModel;
    private SubListAdapter subListAdapter;
    private List<SubEntity> mSubjects=new ArrayList<>();



    @BindView(R.id.tvPres)
    TextView present;

    @BindView(R.id.tvTotal)
    TextView total;

    @BindView(R.id.recyclerview)
    RecyclerView subRView;


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

        final Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);


        
        Transition transition = TransitionInflater.from(getContext()).inflateTransition(R.transition.card_transition);


        FloatingActionButton fab = view.findViewById(R.id.fab);
        LottieAnimationView subjectanim = view.findViewById(R.id.subject_lottie_animation_unique);
        TextView helpText = view.findViewById(R.id.help_text_subject);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);

        View bottomSheet = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_sheet_add_subject,
                (ConstraintLayout) view.findViewById(R.id.bottom_sheet_add_subject_container));












        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(SubjectActivity.this, Activity_Add_Subject.class);
                startActivityForResult(intent, NEW_SUBJECT_ACTIVITY_REQUEST_CODE);
                */


                bottomSheetDialog.setContentView(bottomSheet);
                bottomSheetDialog.setDismissWithAnimation(true);
                bottomSheetDialog.show();


                EditText subjectName = bottomSheetDialog.findViewById(R.id.add_subject_bottomsheet);
                Button addButton = bottomSheetDialog.findViewById(R.id.add_subject_btn);
                TextView update = bottomSheetDialog.findViewById(R.id.add_subject_id);
                LottieAnimationView celebration = bottomSheetDialog.findViewById(R.id.lottie_animation_add_subject);
                LottieAnimationView addsub = bottomSheetDialog.findViewById(R.id.lottie);
                celebration.setVisibility(View.INVISIBLE);


                subjectName.setText(null);
                addsub.setAnimation(R.raw.subject_new);

                update.setText("Add Subject");

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       subjectanim.setVisibility(View.GONE);
                        if(subjectName.getText().toString().trim().length()>0){
                            celebration.setVisibility(View.VISIBLE);
                            celebration.playAnimation();
                            SubEntity subEntity = new SubEntity(subjectName.getText().toString().trim(), 0, 0, 0);
                            subViewModel.insertSubject(subEntity);
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

                 }

        });

        Animation fadeIN = AnimationUtils.loadAnimation(getContext(), R.anim.fade_card);


        subListAdapter = new SubListAdapter(getActivity(),mSubjects,fetchVAlue());
        subViewModel = new ViewModelProvider(this).get(SubjectViewModel.class);
        subViewModel.getAllSubjects().observe(getActivity(), new Observer<List<SubEntity>>() {
            @Override
            public void onChanged(@Nullable List<SubEntity> subjects) {
                subListAdapter.setSubjects(subjects);

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

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(subListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



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