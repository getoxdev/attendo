package com.attendo.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseScheduleViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private String Key = "";

    // Four Mrthods for adding classid,classcode,classjoinas and scheduleid.....
    public void AddClassId(String id){
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule");
        String UserId = mAuth.getCurrentUser().getUid();
        databaseReference.child(UserId).child("Class_Id").setValue(id);
    }

    public void AddCLassCode(String code){
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule");
        String UserId = mAuth.getCurrentUser().getUid();
        databaseReference.child(UserId).child("Class_Code").setValue(code);
    }

    public void AddClassJoinAs(String join){
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule");
        String UserId = mAuth.getCurrentUser().getUid();
        databaseReference.child(UserId).child("Join_As").setValue(join);
    }

    public void AddClassScheduleId(String id){
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule");
        String UserId = mAuth.getCurrentUser().getUid();
        databaseReference.child(UserId).child("Schedule_Id").setValue(id);
    }


    // Four Mrthods for retrieving classid,classcode,classjoinas and scheduleid.....
    public String RetrieveClassId(){
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule");
        databaseReference.orderByKey().equalTo(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String code = mAuth.getCurrentUser().getUid();
                    Key = snapshot.child(code).child("Class_Id").getValue(String.class);

                }else{

                    Key = "nothing";
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Key = "Error";
            }
        });
        return Key;
    }

    public String RetrieveClassJoinAs(){
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule");
        databaseReference.orderByKey().equalTo(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String code = mAuth.getCurrentUser().getUid();
                    Key = snapshot.child(code).child("Join_As").getValue(String.class);
                }else{
                    Key = "nothing";
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Key = "Error";
            }
        });
        return Key;
    }

    public String RetrieveClassCode(){
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule");
        databaseReference.orderByKey().equalTo(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String code = mAuth.getCurrentUser().getUid();
                    Key = snapshot.child(code).child("Class_Code").getValue(String.class);
                }else{
                    Key = "nothing";
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Key = "Error";

            }
        });
        return Key;
    }

    public String RetrieveSchdeuleId(){
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule");
        databaseReference.orderByKey().equalTo(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String code = mAuth.getCurrentUser().getUid();
                    Key = snapshot.child(code).child("Schedule_Id").getValue(String.class);
                }else{
                    Key = "nothing";
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Key = "Error";
            }
        });
        return Key;
    }


    //Leaveing the class or deleting Account will delete all schedule information of that user
    public String DeleteShedule(){
        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Schedule").child(id);
        ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Key = "done";
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Key = e.toString();
            }
        });
        return Key;
        }
}
