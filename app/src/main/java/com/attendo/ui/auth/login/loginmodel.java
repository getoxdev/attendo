package com.attendo.ui.auth.login;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginmodel implements logininterface.Model {

    private  logininterface.Tasklistener listener;
    private FirebaseAuth auth;


    public loginmodel(logininterface.Tasklistener listener) {
        this.listener = listener;
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void doLogin(String email, String password) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
