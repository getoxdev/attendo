package com.attendo.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.attendo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentForgetPassword extends Fragment {

    @BindView(R.id.edittextforgotemail)
    EditText email;
    @BindView(R.id.submit)
    Button btn;
    @BindView(R.id.progresscircular)
    ProgressBar progress;

    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmen
        View view = inflater.inflate(R.layout.fragment_forgetpassword, container, false);
        ButterKnife.bind(this, view);

        firebaseAuth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                String useremail = email.getText().toString().trim();
                if(useremail.isEmpty()){
                    progress.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(),"Please enter registered email ID",Toast.LENGTH_SHORT).show();
                }
                else {
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    progress.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getActivity(),"password reset email sent",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    progress.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getActivity(),"Error in sending password reset email",Toast.LENGTH_SHORT).show();
                                }
                        }
                    });
                }
            }
        });

        return view;
    }
}