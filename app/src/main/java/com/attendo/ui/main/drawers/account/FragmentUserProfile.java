package com.attendo.ui.main.drawers.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.ui.auth.AuthenticationActivity;
import com.attendo.ui.main.BottomNavMainActivity;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
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

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class FragmentUserProfile extends Fragment {

    @BindView(R.id.USER_NAME)
     TextView name;
    @BindView(R.id.USER_COLLEGE)
    TextView college;
    @BindView(R.id.USER_CITY)
    TextView city;
    @BindView(R.id.USER_PHONE)
    TextView contact;
    @BindView(R.id.delete__account)
    TextView DELETE;
    @BindView(R.id.edit_profile)
    Button btn;
    @BindView(R.id.progressbar)
    ProgressBar pgb;
    @BindView(R.id.USER_IMAGE)
    ImageView profile;
    @BindView(R.id.Delete_Account)
    ImageView delete;


    private FragmentProfile fragment_profile;
    String user_id;
    private TextView userjoinas;
    private TextView usercode;
    private FirebaseScheduleViewModel firebaseScheduleViewModel;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    FirebaseStorage firebaseStorage;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Account Profile");

        usercode = view.findViewById(R.id.USER_CLASS_CODE);
        userjoinas = view.findViewById(R.id.USER_CLASS_JOIN_AS);
        firebaseScheduleViewModel = new ViewModelProvider(this).get(FirebaseScheduleViewModel.class);


        ButterKnife.bind(this,view);

        DELETE.setOnClickListener(new View.OnClickListener() {
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

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAccount();
            }
        });

        fragment_profile = new FragmentProfile();
        storageReference.child("images/" + user_id.toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri)
            {
                Picasso.with(getContext()).load(uri).into(profile);
                pgb.setVisibility(View.INVISIBLE);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Toast.makeText(getActivity(),"No Account is Created",Toast.LENGTH_SHORT).show();
                        pgb.setVisibility(View.INVISIBLE);
                    }
                });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {
                    name.setText(snapshot.child(user_id).child("username").getValue(String.class));
                    college.setText(snapshot.child(user_id).child("college").getValue(String.class));
                    city.setText(snapshot.child(user_id).child("city").getValue(String.class));
                    contact.setText(snapshot.child(user_id).child("contact").getValue(String.class));
                }
                else{
                    Toast.makeText(getActivity(),"No Account is Created",Toast.LENGTH_SHORT).show();
                    pgb.setVisibility(View.INVISIBLE);
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


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Passing empty data
                Bundle bundle = new Bundle();
                bundle.putString("name",name.getText().toString());
                bundle.putString("institution",college.getText().toString());
                bundle.putString("city",city.getText().toString());
                bundle.putString("phone",contact.getText().toString());
                fragment_profile.setArguments(bundle);
                Toast.makeText(getActivity(),"Edit Your Profile",Toast.LENGTH_SHORT).show();

                setFragment(fragment_profile);
            }
        });

        LoadData();

        return view;
    }

    private void LoadData() {

        databaseReference2.child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {
                    userjoinas.setText(snapshot.child("Join_As").getValue().toString());
                    usercode.setText(snapshot.child("Class_Code").getValue().toString());
                }
                else{ }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }


    //*****************ACCOUNT DELETION CODE********************


    private void DeleteAccount() {
        String user_Id = mAuth.getCurrentUser().getUid();
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Are You Sure?");
        dialog.setMessage("Deleting this account will result in completely removing your account and profile from the system");
        dialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pgb.setVisibility(View.VISIBLE);
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("data").child(user_Id);
                        DatabaseReference refbugs = FirebaseDatabase.getInstance().getReference("Bugs").child(user_Id);
                        ref.removeValue();
                        refbugs.removeValue();
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
                        pgb.setVisibility(View.INVISIBLE);
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
                                pgb.setVisibility(View.INVISIBLE);
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
                        pgb.setVisibility(View.INVISIBLE);
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
                }




                /*
                firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            storageReference = storage.getReference();
                            storageReference.child("images/" + user_Id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    pgb.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getActivity(),"Your Account has been deleted",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pgb.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getActivity(),"YOUR ACCOUNT HAS BEEN DELETED "+e,Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),""+e.toString(),Toast.LENGTH_LONG).show();
                        pgb.setVisibility(View.INVISIBLE);
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
                pgb.setVisibility(View.INVISIBLE);
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }
}
*/