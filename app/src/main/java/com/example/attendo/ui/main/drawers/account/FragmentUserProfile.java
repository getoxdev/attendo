package com.example.attendo.ui.main.drawers.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendo.R;
import com.example.attendo.ui.auth.AuthenticationActivity;
import com.example.attendo.ui.main.drawers.account.FragmentProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class FragmentUserProfile extends Fragment {

    Button btn;
    private FragmentProfile fragment_profile;
    TextView name,college,city,contact;
    String user_id;
    ProgressBar pgb;
    ImageView profile,delete;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
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

        name = view.findViewById(R.id.USER_NAME);
        college = view.findViewById(R.id.USER_COLLEGE);
        city = view.findViewById(R.id.USER_CITY);
        contact = view.findViewById(R.id.USER_PHONE);
        pgb = view.findViewById(R.id.progressbar);
        profile = view.findViewById(R.id.USER_IMAGE);
        delete = view.findViewById(R.id.Delete_Account);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAccount();
            }
        });

        fragment_profile = new FragmentProfile();

        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        firebaseUser = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("data");
        storageReference = firebaseStorage.getReference();
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

        btn = view.findViewById(R.id.edit_profile);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(fragment_profile);
            }
        });
        return view;
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }


    //************ACCOUNT DELETION CODE**************


    private void DeleteAccount() {
        String user_Id = mAuth.getCurrentUser().getUid();

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Are You Sure?");
        dialog.setMessage("Deleting this account will result in completely removing your account and profile from the system");
        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pgb.setVisibility(View.VISIBLE);
                firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            storageReference = storage.getReference();
                            storageReference.child("images/" + user_Id.toString()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    databaseReference.child(user_Id).removeValue();
                                    pgb.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getActivity(),"Your Account has been deleted",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    databaseReference.child(user_Id).removeValue();
                                    pgb.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getActivity(),"Your Account has been deleted",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                });
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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