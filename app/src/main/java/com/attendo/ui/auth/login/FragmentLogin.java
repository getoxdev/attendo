package com.attendo.ui.auth.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.databinding.FragmentLoginBinding;
import com.attendo.ui.CustomLoadingDialog;
import com.attendo.ui.auth.FragmentForgetPassword;
import com.attendo.R;
import com.attendo.ui.auth.signup.FragmentSignup;
import com.attendo.ui.main.BottomNavMainActivity;
import com.attendo.ui.main.drawers.account.FragmentProfile;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
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

public class FragmentLogin extends Fragment implements logininterface.View {
    private FragmentLoginBinding binding;
    int found = 0;
    private FirebaseScheduleViewModel firebaseScheduleViewModel;
    private logininterface.Presenter presenter;
    private AppPreferences appPreferences;
    private FragmentSignup fragmentSignup;
    private FragmentForgetPassword fragmentForgetpassword;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1234;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FragmentProfile fragmentProfile;
    FragmentActivity mActivity;
    private static final String EMAIL = "email";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        setAllowEnterTransitionOverlap(false);
        presenter = new loginPresenter(this);
        fragmentSignup = new FragmentSignup();
        fragmentForgetpassword = new FragmentForgetPassword();
        fragmentProfile = new FragmentProfile();
        firebaseScheduleViewModel = new ViewModelProvider(this).get(FirebaseScheduleViewModel.class);
        appPreferences = AppPreferences.getInstance(getContext());
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule");

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

        binding.textViewforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(fragmentForgetpassword);
            }
        });

        binding.textViewregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(fragmentSignup);
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progressCircular.setVisibility(View.VISIBLE);
                handleLogin();

            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        //google sign in option:
        View otherWaysToSignIn = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_sign_in,
                (ConstraintLayout) binding.getRoot().findViewById(R.id.sign_in_bottom_sheet));
        BottomSheetDialog signInBottomSheet = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);
        signInBottomSheet.setContentView(otherWaysToSignIn);
        signInBottomSheet.setDismissWithAnimation(true);

        //****************create google sign  in request************************
        createRequest();

        binding.otherSignInOptionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FragmentActivity){
            mActivity = (FragmentActivity) context;
        }
    }

    public void setInputs(boolean enable){
        binding.editTextTextPersonName.setEnabled(enable);
        binding.editTextTextPassword.setEnabled(enable);
        binding.button.setEnabled(enable);
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
                    // Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    // Log.w(TAG, "Google sign in failed", e);
                    // ...
                }
            }
    }

    private void firebaseAuthWithGoogle(String idToken)
    {
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
                            // Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(getActivity(), "Sign In succesful" ,Toast.LENGTH_SHORT).show();

                            checkUser();


                        }
                        else {
                            loadingDialog.dismissDialog();
                            // If sign in fails, display a message to the user.
                            // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(getView(), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private void setNextFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.start_frame, fragment);
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
            binding.progressCircular.setVisibility(View.INVISIBLE);
            Toast.makeText(mActivity,"please enter a valid email",Toast.LENGTH_SHORT).show();
            binding.editTextTextPersonName.setError("InValid email");
        }
        else if(!isValidPassword()){
            binding.progressCircular.setVisibility(View.INVISIBLE);
            Toast.makeText(mActivity,"please enter a valid password",Toast.LENGTH_SHORT).show();
        }
        else {
            presenter.toLogin(binding.editTextTextPersonName.getText().toString().trim(),binding.editTextTextPassword.getText().toString().trim());
        }
    }

    @Override
    public boolean isValidEmail() {
        return Patterns.EMAIL_ADDRESS.matcher(binding.editTextTextPersonName.getText().toString().trim()).matches();
    }

    @Override
    public boolean isValidPassword() {
        if(TextUtils.isEmpty(binding.editTextTextPassword.getText().toString().trim()) || binding.editTextTextPassword.getText().toString().trim().length()<6){
            Toast.makeText(getActivity(),"please enter a valid password",Toast.LENGTH_SHORT).show();
            binding.editTextTextPassword.setError("Invalid data");
            return false;
        }
        else
            return true;
    }

    @Override
    public void onLogin() {
        binding.progressCircular.setVisibility(View.INVISIBLE);
        RetrieveFcm();
        Toast.makeText(getActivity(),"Login Successful ",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getActivity(),BottomNavMainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void RetrieveFcm() {
        mAuth = FirebaseAuth.getInstance();
        databaseReference.orderByKey().equalTo(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String code = mAuth.getCurrentUser().getUid();
                    String fcmcode = snapshot.child(code).child("FCM").getValue(String.class);
                    appPreferences.AddFcm(fcmcode);
                    Log.i("Fcm:",""+appPreferences.RetrieveFcm());
                     }else{
                    // schedule_id = "nothing";
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onError(String message) {
        binding.progressCircular.setVisibility(View.INVISIBLE);
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.start_frame,fragment);
        fragmentTransaction.commit();
    }

    private void checkUser(){

        if(mAuth.getCurrentUser() != null) {
            DatabaseReference mData = FirebaseDatabase.getInstance().getReference("data");

            mData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(mAuth.getCurrentUser().getUid())) {
                        Intent newIntent = new Intent(mActivity, BottomNavMainActivity.class);
                        mActivity.startActivity(newIntent);
                        mActivity.finish();
                    } else {
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