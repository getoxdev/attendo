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

    private static final int NEW_SUBJECT_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_SUBJECT_ACTIVITY_REQUEST_CODE = 2;
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

        fragment_addSubject = new Fragment_AddSubject();

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(SubjectActivity.this, Activity_Add_Subject.class);
                startActivityForResult(intent, NEW_SUBJECT_ACTIVITY_REQUEST_CODE);
                */
                 //setFragment(fragment_addSubject);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);
                View bottomSheet = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_sheet_add_subject,
                        (ConstraintLayout) view.findViewById(R.id.bottom_sheet_add_subject_container));
                bottomSheetDialog.setContentView(bottomSheet);
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

                        celebration.setVisibility(View.VISIBLE);
                        celebration.playAnimation();

                        if(subjectName.getText().toString() != null){
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

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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

//        Bundle bundle = getArguments();
//        String data = bundle.getString("key");
//        if(!data.isEmpty()){
//            SubEntity subject = new SubEntity(data, 0,0,0);
//            data= "";
//            subViewModel.insertSubject(subject);
//            Toast.makeText(getActivity(), "  Subject is Added ",
//                    Toast.LENGTH_SHORT).show();
//        }
     return  view;
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }

}