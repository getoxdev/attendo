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
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentAppRate extends Fragment {
    @BindView(R.id.feedback)
    EditText feedback;
    @BindView(R.id.btnFeedback)
    Button btnFeedback;

    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Firebase firebase;
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_app_rate, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Rate our App");
        ButterKnife.bind(this,view);

        mAuth=FirebaseAuth.getInstance();
        String user=mAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child(user);


        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFeedback.setEnabled(true);
                final String msg = feedback.getText().toString().trim();


                if (msg.isEmpty()) {
                    feedback.setError("this is a required field");
                    //btnFeedback.setEnabled(false);
                } else {
                    feedback.setError(null);
                    btnFeedback.setEnabled(true);
                    databaseReference.child("Feedback").setValue(msg).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getContext(), "Thank You", Toast.LENGTH_SHORT).show();

                        }
                    });

                }



            }
        });


        return  view;
    }













}
