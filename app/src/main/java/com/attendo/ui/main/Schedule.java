package com.attendo.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.attendo.R;

public class Schedule extends AppCompatActivity {

    private ViewPager viewPager;
    FragmentCollectionAdapter fragmentCollectionAdapter;
    CrFragmentCollectionAdapter crFragmentCollectionAdapter;
    EditText Code;
    Button Student,Cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Code = findViewById(R.id.code);
        Student = findViewById(R.id.student);
        Cr = findViewById(R.id.cr);
        viewPager = findViewById(R.id.viewpage);

        Cr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = Code.getText().toString();
                if(id.length()!=0){
                    Code.setVisibility(View.GONE);
                    Student.setVisibility(View.GONE);
                    Cr.setVisibility(View.GONE);
                    crFragmentCollectionAdapter = new CrFragmentCollectionAdapter(getSupportFragmentManager());
                    viewPager.setAdapter(crFragmentCollectionAdapter);
                }
                else{
                    Toast.makeText(Schedule.this,"Enter the code",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = Code.getText().toString();
                if(id.length()!=0){
                    Code.setVisibility(View.GONE);
                    Student.setVisibility(View.GONE);
                    Cr.setVisibility(View.GONE);
                    fragmentCollectionAdapter = new FragmentCollectionAdapter(getSupportFragmentManager());
                    viewPager.setAdapter(fragmentCollectionAdapter);
                }
                else{
                    Toast.makeText(Schedule.this,"Enter the code",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}