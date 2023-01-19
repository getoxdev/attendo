package com.attendo.ui.main.drawers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.attendo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentBug extends Fragment{
    @BindView(R.id.msgData)
    EditText TextMsg;
    @BindView(R.id.btn_send)
    Button send;
    @BindView(R.id.btn_details)
    Button details;

    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    ProgressBar Pb;
    int found = 0;
    String value = "No Data Found";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_bug, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Report Bug");
        ButterKnife.bind(this,view);

        mAuth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Bugs");
        Pb = view.findViewById(R.id.detail_progress);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser() != null) {
                    String text = TextMsg.getText().toString().trim();
                    if(text.length()>0){
                        databaseReference.child(mAuth.getCurrentUser().getUid()).child("Bug").push().setValue(text).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getActivity(),"Your response send",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getActivity(),"something went wrong!",Toast.LENGTH_SHORT).show();
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

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pb.setVisibility(View.VISIBLE);
                databaseReference.orderByKey().equalTo(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Pb.setVisibility(View.INVISIBLE);
                            found = 1;
                            value = snapshot.child(mAuth.getCurrentUser().getUid()).child("Bug").getValue().toString();
                        }
                        else{

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(),""+error, Toast.LENGTH_SHORT).show();
                    }
                });
                    new AlertDialog.Builder(getContext())
                            .setTitle("Sent Details :")
                            .setMessage(""+value).show();
                Pb.setVisibility(View.INVISIBLE);
            }

        });
        return view;
    }


}
