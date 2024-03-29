package com.attendo.ui.auth.signup;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.attendo.ui.main.drawers.account.FragmentProfile;
import com.attendo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentSignup extends Fragment implements SignupInterface.View {

    @BindView(R.id.editTextEmail)
    EditText email;
    @BindView(R.id.editTextTextPassword)
    EditText password;
    @BindView(R.id.editTextTextPassword2)
    EditText confpassword;
    @BindView(R.id.button)
    Button SignUp;
    @BindView(R.id.progressbar)
    ProgressBar progress;


    private SignupInterface.Presenter presenter;
    private FragmentProfile fragment_profile;
    FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        ButterKnife.bind(this, view);

        presenter = new SignupPresenter(this);
        fragment_profile = new FragmentProfile();
        mAuth = FirebaseAuth.getInstance();

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                handleSignup();
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        return view;
    }

    public void setInputs(boolean enable){
        email.setEnabled(enable);
        password.setEnabled(enable);
        confpassword.setEnabled(enable);
        SignUp.setEnabled(enable);
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
    public void handleSignup() {
        if(!isValidEmail()){
            progress.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(),"please enter a valid email",Toast.LENGTH_SHORT).show();
            email.setError("InValid");
        }
        else if(!isValidPassword()){
            progress.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(),"Password length must be greater than 5",Toast.LENGTH_SHORT).show();
        }
        else if(!isValidConfPassword()){
            progress.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(),"password ans confirm-password doen't match",Toast.LENGTH_SHORT).show();
            confpassword.setError("Error");
        }

        else{
            presenter.toLogin(email.getText().toString().trim(),password.getText().toString().trim(),confpassword.getText().toString().trim());
        }
    }



    @Override
    public boolean isValidEmail() {
        return Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches();
    }

    @Override
    public boolean isValidPassword() {
        if(TextUtils.isEmpty(password.getText().toString()) || password.getText().toString().length()<6){
            password.setError("Invalid data");
            return false;
        }
        else
            return true;
    }

    @Override
    public boolean isValidConfPassword() {
       if(password.getText().toString().equals(confpassword.getText().toString()))
           return true;
       else
           return false;
    }

    @Override
    public void onSignup() {
        progress.setVisibility(View.INVISIBLE);
        Toast.makeText(getActivity(),"Signup Successful",Toast.LENGTH_SHORT).show();
        //Passing empty data
        Bundle bundle = new Bundle();
        bundle.putString("name","");
        bundle.putString("institution","");
        bundle.putString("city","");
        bundle.putString("phone","");
        fragment_profile.setArguments(bundle);
        //
        setFragment(fragment_profile);
    }



    @Override
    public void onError(String message) {
        progress.setVisibility(View.INVISIBLE);
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.start_frame,fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }
}