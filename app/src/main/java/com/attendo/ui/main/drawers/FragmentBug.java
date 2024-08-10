package com.attendo.ui.main.drawers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.attendo.R;
import com.attendo.databinding.FragmentBugBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class FragmentBug extends Fragment {

    private FragmentBugBinding binding;

    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    int found = 0;
    String value = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBugBinding.inflate(inflater, container, false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Report Bug");

        mAuth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Bugs").child(mAuth.getCurrentUser().getUid()).child("Bug");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child : snapshot.getChildren()) {
                    found++;
                    String bugText = child.getValue().toString();
                    value += "- " + bugText + "\n";
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("bugreport" , "Error: " + error);
            }
        });

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() != null) {
                    String text = binding.msgData.getText().toString().trim();
                    if (text.length() > 0) {
                        databaseReference.child(mAuth.getCurrentUser().getUid()).child("Bug").push().setValue(text).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Your response send", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                else{
                    Toast.makeText(getActivity(),"Please enter your Message",Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (found <= 0) {
                    new MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Sent Details :")
                            .setMessage("No bugs reported by you")
                            .show();
                } else {
                    new MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Sent Details :")
                            .setMessage(value)
                            .show();
                }
            }
        });

        return binding.getRoot();
    }
}
