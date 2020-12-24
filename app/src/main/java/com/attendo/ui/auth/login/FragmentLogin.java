package com.attendo.ui.auth.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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

import com.attendo.ui.CustomLoadingDialog;
import com.attendo.ui.auth.FragmentForgetPassword;
import com.attendo.R;
import com.attendo.ui.auth.signup.FragmentSignup;
import com.attendo.ui.main.BottomNavMainActivity;
import com.attendo.ui.main.drawers.account.FragmentProfile;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.airbnb.lottie.L.TAG;

public class FragmentLogin extends Fragment implements logininterface.View {
    
    @BindView(R.id.editTextTextPersonName)
    EditText email;
    @BindView(R.id.editTextTextPassword)
    EditText password;
    @BindView(R.id.button)
    Button loginbtn;
    @BindView(R.id.other_signIn_options_btn)
    Button otherWaysbtn;
    @BindView(R.id.textViewforgot)
    TextView forgotpassword;
    @BindView(R.id.textViewregister)
    TextView register;
    @BindView(R.id.progress_circular)
    ProgressBar progress;


    int found = 0;
    private logininterface.Presenter presenter;
    private FragmentSignup fragmentSignup;
    private FragmentForgetPassword fragmentForgetpassword;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1234;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FragmentProfile fragmentProfile;

    private static final String EMAIL = "email";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_login, container, false);

        setAllowEnterTransitionOverlap(false);

        ButterKnife.bind(this, view);

        presenter = new loginPresenter(this);


        fragmentSignup = new FragmentSignup();
        fragmentForgetpassword = new FragmentForgetPassword();
        fragmentProfile = new FragmentProfile();
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule_Member");

        /*//stay logged in code
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(getActivity(),BottomNavMainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }*/

        MaterialSharedAxis enter = new MaterialSharedAxis(MaterialSharedAxis.Z, true);
        enter.setDuration(300);
        MaterialSharedAxis exit = new MaterialSharedAxis(MaterialSharedAxis.Z, false);
        exit.setDuration(300);

        fragmentSignup.setEnterTransition(enter);
        fragmentSignup.setExitTransition(exit);

        fragmentForgetpassword.setEnterTransition(enter);
        fragmentForgetpassword.setExitTransition(exit);

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

        mAuth = FirebaseAuth.getInstance();

        //google sign in option:
        View otherWaysToSignIn = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_sign_in,
                (ConstraintLayout) view.findViewById(R.id.sign_in_bottom_sheet));
        BottomSheetDialog signInBottomSheet = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);
        signInBottomSheet.setContentView(otherWaysToSignIn);
        signInBottomSheet.setDismissWithAnimation(true);

        //****************create google sign  in request************************
        createRequest();

        otherWaysbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                signInBottomSheet.show();
//                ImageView googleSignInbtn = signInBottomSheet.findViewById(R.id.google_sign_in);
//                ImageView facebookSignInbtn = signInBottomSheet.findViewById(R.id.facebook_sign_in);
//
//                googleSignInbtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //code goes here for google sign in
//                        signIn();
//                        signInBottomSheet.dismiss();
//                    }
//                });
                signIn();
            }
        });


        return view;
    }


    public void setInputs(boolean enable){
        email.setEnabled(enable);
        password.setEnabled(enable);
        loginbtn.setEnabled(enable);
    }

    //method for google signIn
    private void createRequest(){
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
    }

    //google sign in
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
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e);
                    // ...
                }
            }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        //TODO: Add the loadin animation dialog here
        CustomLoadingDialog loadingDialog = new CustomLoadingDialog(getActivity());
        loadingDialog.startDialog(false);
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener( getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loadingDialog.dismissDialog();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(getContext(), "Sign In succesful" ,Toast.LENGTH_SHORT).show();

                            checkUser();


                        }
                        else {
                            loadingDialog.dismissDialog();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(getView(), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private void setNextFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.start_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private void setDataGoogleSignIn(Fragment fragment) {
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
        String name = null;
        if(signInAccount!= null){
            name = signInAccount.getDisplayName();
        }
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("institution","");
        bundle.putString("city","");
        bundle.putString("phone","");
        fragment.setArguments(bundle);
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
        return Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches();
    }

    @Override
    public boolean isValidPassword() {
        if(TextUtils.isEmpty(password.getText().toString().trim()) || password.getText().toString().trim().length()<6){
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
        Intent intent=new Intent(getActivity(),BottomNavMainActivity.class);
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

    private void checkUser(){

        if(mAuth.getCurrentUser() != null) {
            final String id = mAuth.getCurrentUser().getUid();
            DatabaseReference mData = FirebaseDatabase.getInstance().getReference("data");

            mData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(mAuth.getCurrentUser().getUid())) {
                        Intent newIntent = new Intent(getActivity(), BottomNavMainActivity.class);
                        startActivity(newIntent);
                        getActivity().finish();
                    } else {
                        String userId = mAuth.getCurrentUser().getUid();
                        databaseReference.child(userId).child("Schedule_Code").setValue("");
                        databaseReference.child(userId).child("Schedule_Join_As").setValue("");
                        setNextFragment(fragmentProfile);
                        setDataGoogleSignIn(fragmentProfile);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }
}