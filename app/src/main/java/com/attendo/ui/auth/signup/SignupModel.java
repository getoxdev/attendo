package com.attendo.ui.auth.signup;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupModel implements SignupInterface.Model {

    private  SignupInterface.Tasklistener listener;
    private FirebaseAuth auth;

    public SignupModel(SignupInterface.Tasklistener listener) {
        this.listener = listener;
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void doSignup(String email, String password, String institution) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    listener.OnSuccess();
                }
                else{
                    if(task.getException()!=null){
                        listener.OnError(task.getException().getMessage());
                    }
                }
            }
        });
    }

}
