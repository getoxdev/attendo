package com.attendo.ui.main.drawers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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


public class FragmentBug extends Fragment{
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    String user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EditText msgdata;
        Button send, details;
        Firebase firebase;
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_bug, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Report Bug");

        msgdata = view.findViewById(R.id.msgData);
        send = view.findViewById(R.id.btn_send);
        details = view.findViewById(R.id.btn_details);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference("Bugs").child(user);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details.setEnabled(true);
                send.setEnabled(true);

              String msg = msgdata.getText().toString().trim();

                if (msg.isEmpty()) {
                    msgdata.setError("this is a required field");
                    //send.setEnabled(false);
                } else {
                    msgdata.setError(null);
                    send.setEnabled(true);
                    databaseReference.child("Bug").setValue(msg).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getContext(), "Your data is sent to server", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

                details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(getContext())
                                .setTitle("Sent Details :")
                                .setMessage("" + msg).show();

                    }
                });
            }
        });
        return view;
    }
}
