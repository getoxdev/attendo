package com.attendo.ui.main.menu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.ui.auth.AuthenticationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FragmentSettings extends Fragment {

    TextView delete;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseUser firebaseUser;
    ProgressBar pb;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_setting, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Settings");

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("data");
        firebaseUser = mAuth.getCurrentUser();

        pb = view.findViewById(R.id.progressbar_delete_account);
        String user_Id = mAuth.getCurrentUser().getUid();

        delete = view.findViewById(R.id.delete_account);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Are You Sure?");
                dialog.setMessage("Deleting this account will result in completely removing your account and profile from the system");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pb.setVisibility(View.VISIBLE);
                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        storageReference = storage.getReference();
                                        storageReference.child("images/" + user_Id.toString()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                databaseReference.child(user_Id).removeValue();
                                                pb.setVisibility(View.INVISIBLE);
                                                Toast.makeText(getActivity(),"Account has been deleted",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                                                startActivity(intent);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                databaseReference.child(user_Id).removeValue();
                                                pb.setVisibility(View.INVISIBLE);
                                                Toast.makeText(getActivity(),"Account has been deleted",Toast.LENGTH_SHORT).show();
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
                        pb.setVisibility(View.INVISIBLE);
                            dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

        return view;
    }
}
