package com.attendo.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.attendo.Schedule.Preference.AppPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseScheduleViewModel extends AndroidViewModel {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private String Key = "";
    private String joinAs = null;
    private String class_id = null;
    private String schedule_id = null;
    private String class_code = null;

    private AppPreferences appPreferences;

    public FirebaseScheduleViewModel(@NonNull Application application) {
        super(application);

        mAuth = FirebaseAuth.getInstance();
        appPreferences = AppPreferences.getInstance(application);
    }

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


    // Four Methods for retrieving classid,classcode,classjoinas and scheduleid.....
    public String RetrieveClassId(){
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule");
        databaseReference.orderByKey().equalTo(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String code = mAuth.getCurrentUser().getUid();
                    class_id = snapshot.child(code).child("Class_Id").getValue(String.class);
                    //appPreferences.AddClassId(snapshot.child(code).child("Class_Id").getValue(String.class));

                }else{

                    class_id = "nothing";
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                class_id = "Error";
            }
        });
        return class_id;
    }

    public String RetrieveClassJoinAs(){
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule");
        databaseReference.orderByKey().equalTo(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String code = mAuth.getCurrentUser().getUid();
                    joinAs = snapshot.child(code).child("Join_As").getValue(String.class);
                    //appPreferences.AddClassJoinAs(snapshot.child(code).child("Join_As").getValue(String.class));
                }else{
                    joinAs = "nothing";
                    //appPreferences.AddClassJoinAs(joinAs);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                joinAs = "Error";
            }
        });
        return joinAs;
    }

    public String RetrieveClassCode(){
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule");
        databaseReference.orderByKey().equalTo(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String code = mAuth.getCurrentUser().getUid();
                    class_code = snapshot.child(code).child("Class_Code").getValue(String.class);
                }else{
                    class_code = "nothing";
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                class_code = "Error";

            }
        });
        return class_code;
    }

    public String RetrieveSchdeuleId(){
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule");
        databaseReference.orderByKey().equalTo(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String code = mAuth.getCurrentUser().getUid();
                    schedule_id = snapshot.child(code).child("Schedule_Id").getValue(String.class);
                    //appPreferences.AddClassScheduleId(snapshot.child(code).child("Schedule_Id").getValue(String.class));
                }else{
                    schedule_id = "nothing";
                    //appPreferences.AddClassScheduleId(schedule_id);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                schedule_id = "Error";
            }
        });
        return schedule_id;
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
