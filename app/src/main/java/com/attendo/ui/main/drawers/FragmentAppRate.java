package com.attendo.ui.main.drawers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.databinding.FragmentAppRateBinding;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentAppRate extends Fragment {

    private FragmentAppRateBinding binding;

    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Firebase firebase;
        // Inflate the layout for this fragment
        binding =  FragmentAppRateBinding.inflate(inflater, container, false);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Rate our App");

        mAuth=FirebaseAuth.getInstance();
        String user=mAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child(user);


        binding.btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnFeedback.setEnabled(true);
                final String msg = binding.btnFeedback.getText().toString().trim();


                if (msg.isEmpty()) {
                    binding.feedback.setError("this is a required field");
                    //btnFeedback.setEnabled(false);
                } else {
                    binding.feedback.setError(null);
                    binding.btnFeedback.setEnabled(true);
                    databaseReference.child("Feedback").setValue(msg).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getActivity(), "Thank You", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });

        return  binding.getRoot();
    }
}
