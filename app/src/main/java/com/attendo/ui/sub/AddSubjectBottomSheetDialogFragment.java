package com.attendo.ui.sub;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.attendo.R;
import com.attendo.data.sub.SubEntity;
import com.attendo.viewmodel.SubjectViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddSubjectBottomSheetDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSubjectBottomSheetDialogFragment extends BottomSheetDialogFragment {

    //hooks for the widgets
    @BindView(R.id.add_subject_bottomsheet)
    EditText subjectName;

    @BindView(R.id.add_subject_btn)
    Button addButton;

    @BindView(R.id.add_subject_id)
    TextView update;

    @BindView(R.id.lottie_animation_add_subject)
    LottieAnimationView celebration;

    @BindView(R.id.lottie)
    LottieAnimationView addsub;

    @BindView(R.id.edittext_input_subject)
    TextInputLayout addSubLayout;

    private SubjectViewModel subViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_subject_bottom_sheet_dialog, container, false);
        ButterKnife.bind(this, view);

        celebration.setVisibility(View.INVISIBLE);
        subjectName.setText(null);
        addsub.setAnimation(R.raw.subject_new);
        update.setText("Add Subject");
        subViewModel = new ViewModelProvider(this).get(SubjectViewModel.class);




        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSubLayout.setErrorEnabled(false);
                if(subjectName.getText().toString().trim().length()>0){
                    celebration.setVisibility(View.VISIBLE);
                    celebration.playAnimation();
                    SubEntity subEntity = new SubEntity(subjectName.getText().toString().trim(), 0, 0, 0);
                    subViewModel.insertSubject(subEntity);
                    Handler mhandler = new Handler();
                    mhandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();

                        }
                    },600);

                }
                else{
                    addSubLayout.setError("Please enter the subject");

                }


            }
        });






        return view;
    }


}