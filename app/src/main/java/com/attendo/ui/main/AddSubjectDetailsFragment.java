package com.attendo.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.attendo.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddSubjectDetailsFragment extends BottomSheetDialogFragment {


    private EditText subject,faculty,time;
    private Button submit;
    private LottieAnimationView celebration;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

   private String mParam1;
    private String mParam2;

    public AddSubjectDetailsFragment() {
        }
 public static AddSubjectDetailsFragment newInstance(String param1, String param2) {
        AddSubjectDetailsFragment fragment = new AddSubjectDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_subject_details, container, false);

        celebration = view.findViewById(R.id.lottie_animation_add_subject_details);
        celebration.setVisibility(View.INVISIBLE);

        subject = view.findViewById(R.id.add_subject_bottomsheet);
        faculty = view.findViewById(R.id.add_faculty);
        time = view.findViewById(R.id.add_Time);
        submit = view.findViewById(R.id.add_subject_btn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sub = subject.getText().toString();
                String teacher = faculty.getText().toString();
                String clock = time.getText().toString();
                if(sub.length()>0 && teacher.length()>0 && clock.length()>0){
                    celebration.setVisibility(View.VISIBLE);
                    celebration.playAnimation();
                    Handler mhandler = new Handler();
                    mhandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();

                        }
                    },600);
                }
                else{
                    Toast.makeText(getActivity(),"Please fill all details",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return  view;
    }
}