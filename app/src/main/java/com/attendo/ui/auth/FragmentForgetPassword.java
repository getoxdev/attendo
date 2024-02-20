package com.attendo.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.attendo.databinding.FragmentForgetpasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class FragmentForgetPassword extends Fragment {
    private FirebaseAuth firebaseAuth;
    private FragmentForgetpasswordBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentForgetpasswordBinding.inflate(inflater, container, false);
        firebaseAuth = FirebaseAuth.getInstance();

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progresscircular.setVisibility(View.VISIBLE);
                String useremail = binding.edittextforgotemail.getText().toString().trim();
                if(useremail.isEmpty()){
                    binding.progresscircular.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(),"Please enter registered email ID",Toast.LENGTH_SHORT).show();
                }
                else {
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    binding.progresscircular.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getActivity(),"password reset email sent",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    binding.progresscircular.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getActivity(),"Error in sending password reset email",Toast.LENGTH_SHORT).show();
                                }
                        }
                    });
                }
            }
        });

        return binding.getRoot();
    }
}