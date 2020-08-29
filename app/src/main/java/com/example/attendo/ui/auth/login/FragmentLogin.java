package com.example.attendo.ui.auth.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendo.ui.auth.FragmentForgetPassword;
import com.example.attendo.R;
import com.example.attendo.ui.auth.signup.FragmentSignup;
import com.example.attendo.ui.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.airbnb.lottie.L.TAG;

public class FragmentLogin extends Fragment implements logininterface.View {

    private EditText email,password;
    private Button loginbtn;
    private TextView forgotpassword;
    private TextView register;
    private logininterface.Presenter presenter;
    private FragmentSignup fragmentSignup;
    private FragmentForgetPassword fragmentForgetpassword;
    private ProgressBar progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_login, container, false);

        email = view.findViewById(R.id.editTextTextPersonName);
        password = view.findViewById(R.id.editTextTextPassword);
        loginbtn = view.findViewById(R.id.button);
        forgotpassword = view.findViewById(R.id.textViewforgot);
        register = view.findViewById(R.id.textViewregister);
        progress = view.findViewById(R.id.progress_circular);
        presenter = new loginPresenter(this);

        fragmentSignup = new FragmentSignup();
        fragmentForgetpassword = new FragmentForgetPassword();

        //stay logged in code
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(getActivity(), MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                            setFragment(fragmentForgetpassword);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(fragmentSignup);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                handleLogin();
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        return view;
    }

    public void setInputs(boolean enable){
        email.setEnabled(enable);
        password.setEnabled(enable);
        loginbtn.setEnabled(enable);
    }

    @Override
    public void disableinput() {
        setInputs(false);
    }

    @Override
    public void enableInput() {
        setInputs(true);
    }

    @Override
    public void handleLogin() {
        if(!isValidEmail()){
            progress.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(),"please enter a valid email",Toast.LENGTH_SHORT).show();
            email.setError("InValid email");
        }
        else if(!isValidPassword()){
            progress.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(),"please enter a valid password",Toast.LENGTH_SHORT).show();
        }
        else {
            presenter.toLogin(email.getText().toString().trim(),password.getText().toString().trim());
        }
    }

    @Override
    public boolean isValidEmail() {
        return Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches();
    }

    @Override
    public boolean isValidPassword() {
        if(TextUtils.isEmpty(password.getText().toString()) || password.getText().toString().length()<6){
            Toast.makeText(getActivity(),"please enter a valid password",Toast.LENGTH_SHORT).show();
            password.setError("Invalid data");
            return false;
        }
        else
            return true;
    }

    @Override
    public void onLogin() {
        progress.setVisibility(View.INVISIBLE);
        Toast.makeText(getActivity(),"Login Successful by " + email.getText().toString().trim(),Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getActivity(),MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onError(String message) {
        progress.setVisibility(View.INVISIBLE);
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.start_frame,fragment);
        fragmentTransaction.commit();
    }
}