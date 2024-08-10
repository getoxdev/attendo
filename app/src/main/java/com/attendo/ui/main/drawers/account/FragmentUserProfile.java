package com.attendo.ui.main.drawers.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.attendo.R;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.databinding.FragmentUserProfileBinding;
import com.attendo.ui.auth.AuthenticationActivity;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class FragmentUserProfile extends Fragment {

    private FragmentUserProfileBinding binding;

    private ScheduleViewModel scheduleViewModel;
    private FragmentProfile fragment_profile;
    String user_id;
    private FirebaseScheduleViewModel firebaseScheduleViewModel;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    FirebaseStorage firebaseStorage;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseUser firebaseUser;
    AppPreferences appPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserProfileBinding.inflate(inflater, container, false);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Account Profile");

        firebaseScheduleViewModel = new ViewModelProvider(this).get(FirebaseScheduleViewModel.class);
        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);

        binding.DeleteAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAccount();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        user_id = mAuth.getCurrentUser().getUid();
        firebaseUser = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("data");
        storageReference = firebaseStorage.getReference();
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Schedule");


        fragment_profile = new FragmentProfile();
        storageReference.child("images/" + user_id).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri)
            {
                Picasso.with(getActivity()).load(uri).into(binding.USERIMAGE);
                binding.progressbar.setVisibility(View.INVISIBLE);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Toast.makeText(getActivity(),"No Account is Created",Toast.LENGTH_SHORT).show();
                        binding.progressbar.setVisibility(View.INVISIBLE);
                    }
                });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {
                    binding.USERNAME.setText(snapshot.child(user_id).child("username").getValue(String.class));
                    binding.USERCOLLEGE.setText(snapshot.child(user_id).child("college").getValue(String.class));
                    binding.USERCITY.setText(snapshot.child(user_id).child("city").getValue(String.class));
                    binding.USERPHONE.setText(snapshot.child(user_id).child("contact").getValue(String.class));
                }
                else{
                    Toast.makeText(getActivity(),"No Account is Created",Toast.LENGTH_SHORT).show();
                    binding.progressbar.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        MaterialSharedAxis enter = new MaterialSharedAxis(MaterialSharedAxis.Z, true);
        MaterialSharedAxis exit = new MaterialSharedAxis(MaterialSharedAxis.Z , false);

        fragment_profile.setEnterTransition(enter);
        fragment_profile.setExitTransition(exit);


        binding.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Passing empty data
                Bundle bundle = new Bundle();
                bundle.putString("name",binding.USERNAME.getText().toString());
                bundle.putString("institution",binding.USERCOLLEGE.getText().toString());
                bundle.putString("city",binding.USERCITY.getText().toString());
                bundle.putString("phone",binding.USERPHONE.getText().toString());
                fragment_profile.setArguments(bundle);
                Toast.makeText(getActivity(),"Edit Your Profile",Toast.LENGTH_SHORT).show();

                setFragment(fragment_profile);
            }
        });

//        LoadData();

        return binding.getRoot();
    }

//    private void LoadData()
//    {
//        databaseReference2.child(user_id).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                if(snapshot.exists())
//                {
//                    if(snapshot.child("Join_As").getValue()!=null)
//                       binding.classjoinas.setText(Objects.requireNonNull(snapshot.child("Join_As").getValue()).toString());
//
//                    if(snapshot.child("Class_Code").getValue()!=null)
//                        binding.classcode.setText(Objects.requireNonNull(snapshot.child("Class_Code").getValue()).toString());
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

    private void DeleteAccount() {
        String user_Id = mAuth.getCurrentUser().getUid();
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Are You Sure?");
        dialog.setMessage("Deleting this account will result in completely removing your account and profile from the system");
        dialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.progressbar.setVisibility(View.VISIBLE);
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("data").child(user_Id);
                        DatabaseReference refbugs = FirebaseDatabase.getInstance().getReference("Bugs").child(user_Id);
                        ref.removeValue();
                        refbugs.removeValue();
                        ServerDelete();
                        NullSharedPreferenceData();
                        firebaseScheduleViewModel.DeleteShedule();
                        storageReference = storage.getReference();
                        storageReference.child("images/" + user_Id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //LETS SEE
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                               //LETS SEE
                            }
                        });
                        binding.progressbar.setVisibility(View.INVISIBLE);
                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Your Account has been deleted ", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(),"Please Login again to delete account",Toast.LENGTH_LONG).show();
                                binding.progressbar.setVisibility(View.INVISIBLE);
                                mAuth.signOut();
                                Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        });
                    }
                });
                dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.progressbar.setVisibility(View.INVISIBLE);
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }

    private void ServerDelete() {
        scheduleViewModel.leaveClass(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail());
        scheduleViewModel.leaveClassResponse().observe(requireActivity(), data -> {
            if (data == null) {
                Log.i("ApiCall", "Failed");
            } else {
                Log.i("Api Call :","Success");
            }
        });
    }

    private void NullSharedPreferenceData() {
        appPreferences = new AppPreferences(getContext());
        appPreferences.AddJoinAs(null);
        appPreferences.AddClassId(null);
        appPreferences.AddClassScheduleId(null);
        appPreferences.AddFcm(null);
    }
}