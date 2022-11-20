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
import com.attendo.databinding.FragmentAddSubjectBottomSheetDialogBinding;
import com.attendo.viewmodel.SubjectViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddSubjectBottomSheetDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSubjectBottomSheetDialogFragment extends BottomSheetDialogFragment {
    
    FragmentAddSubjectBottomSheetDialogBinding binding;

    //hooks for the widgets
   



    private SubjectViewModel subViewModel;
  

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddSubjectBottomSheetDialogBinding.inflate(inflater,container,false);

        binding.lottieAnimationAddSubject.setVisibility(View.INVISIBLE);
        binding.addSubjectBottomsheet.setText(null);
        binding.lottie.setAnimation(R.raw.subject_new);
        binding.addSubjectId.setText("Add Subject");
        subViewModel = new ViewModelProvider(this).get(SubjectViewModel.class);




        binding.addSubjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.edittextInputSubject.setErrorEnabled(false);
                if(binding.addSubjectBottomsheet.getText().toString().trim().length()>0){
                    binding.lottieAnimationAddSubject.setVisibility(View.VISIBLE);
                    binding.lottieAnimationAddSubject.playAnimation();
                    SubEntity subEntity = new SubEntity(binding.addSubjectBottomsheet.getText().toString().trim(), 0, 0, 0);
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
                    binding.edittextInputSubject.setError("Please enter the subject");

                }


            }
        });






        return binding.getRoot();
    }


}