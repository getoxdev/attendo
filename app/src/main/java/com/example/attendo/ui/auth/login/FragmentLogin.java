package com.example.attendo.ui.auth.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.attendo.ui.main.BottomNavMainActivity;
import com.example.attendo.ui.main.MainActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executor;

import static com.airbnb.lottie.L.TAG;

public class FragmentLogin extends Fragment implements logininterface.View {

    private EditText email,password;
    private Button loginbtn;
    private TextView forgotpassword;
    private TextView register,Google_Signup;
    private logininterface.Presenter presenter;
    private FragmentSignup fragmentSignup;
    private FragmentForgetPassword fragmentForgetpassword;
    private ProgressBar progress;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_login, container, false);

        //Google SignUp
        createRequest();
        mAuth = FirebaseAuth.getInstance();

        email = view.findViewById(R.id.editTextTextPersonName);
        password = view.findViewById(R.id.editTextTextPassword);
        loginbtn = view.findViewById(R.id.button);
        forgotpassword = view.findViewById(R.id.textViewforgot);
        register = view.findViewById(R.id.textViewregister);
        progress = view.findViewById(R.id.progress_circular);
        presenter = new loginPresenter(this);
        Google_Signup = view.findViewById(R.id.GoogleSignUp);

        Google_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        fragmentSignup = new FragmentSignup();
        fragmentForgetpassword = new FragmentForgetPassword();

        //stay logged in code
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(getActivity(),BottomNavMainActivity.class);
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
        Intent intent=new Intent(getActivity(), BottomNavMainActivity.class);
        startActivity(intent);
        getActivity().finish();
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


        //********GOOGLE SIGN UP CODE*********

    
    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.app_name))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
               GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                // ...
                Toast.makeText(getActivity(),""+e,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getActivity(),"Signup",Toast.LENGTH_SHORT).show();
                        } else {
                            /* If sign in fails, display a message to the user. */
                            Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

}