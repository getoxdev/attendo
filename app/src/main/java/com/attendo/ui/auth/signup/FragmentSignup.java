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

import com.attendo.databinding.FragmentSignupBinding;
import com.attendo.ui.main.drawers.account.FragmentProfile;
import com.attendo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentSignup extends Fragment implements SignupInterface.View {

    private SignupInterface.Presenter presenter;
    private FragmentProfile fragment_profile;
    FirebaseAuth mAuth;
    private FragmentSignupBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater, container, false);

        presenter = new SignupPresenter(this);
        fragment_profile = new FragmentProfile();
        mAuth = FirebaseAuth.getInstance();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progressbar.setVisibility(View.VISIBLE);
                handleSignup();
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        return binding.getRoot();
    }

    public void setInputs(boolean enable){
        binding.editTextEmail.setEnabled(enable);
        binding.editTextTextPassword.setEnabled(enable);
        binding.editTextTextPassword2.setEnabled(enable);
        binding.button.setEnabled(enable);
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
            binding.progressbar.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(),"please enter a valid email",Toast.LENGTH_SHORT).show();
            binding.editTextEmail.setError("InValid");
        }
        else if(!isValidPassword()){
            binding.progressbar.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(),"Password length must be greater than 5",Toast.LENGTH_SHORT).show();
        }
        else if(!isValidConfPassword()){
            binding.progressbar.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(),"password ans confirm-password doen't match",Toast.LENGTH_SHORT).show();
            binding.editTextTextPassword2.setError("Error");
        }

        else{
            presenter.toLogin(binding.editTextEmail.getText().toString().trim(),binding.editTextTextPassword.getText().toString().trim(),binding.editTextTextPassword2.getText().toString().trim());
        }
    }



    @Override
    public boolean isValidEmail() {
        return Patterns.EMAIL_ADDRESS.matcher(binding.editTextEmail.getText().toString()).matches();
    }

    @Override
    public boolean isValidPassword() {
        if(TextUtils.isEmpty(binding.editTextTextPassword.getText().toString()) || binding.editTextTextPassword.getText().toString().length()<6){
            binding.editTextTextPassword.setError("Invalid data");
            return false;
        }
        else
            return true;
    }

    @Override
    public boolean isValidConfPassword() {
       if(binding.editTextTextPassword.getText().toString().equals(binding.editTextTextPassword2.getText().toString()))
           return true;
       else
           return false;
    }

    @Override
    public void onSignup() {
        binding.progressbar.setVisibility(View.INVISIBLE);
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
        binding.progressbar.setVisibility(View.INVISIBLE);
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.start_frame,fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }
}