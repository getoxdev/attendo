package com.example.attendo.ui.sub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendo.data.SubDao;
import com.example.attendo.data.database.SubDatabase;
import com.example.attendo.viewmodel.SubjectViewModel;
import com.example.attendo.R;
import com.example.attendo.data.SubEntity;
import com.example.attendo.data.SubListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.UUID;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class SubjectActivity extends AppCompatActivity implements SubListAdapter.onclick{

    private static final int NEW_SUBJECT_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_SUBJECT_ACTIVITY_REQUEST_CODE = 2;
    private String TAG = this.getClass().getSimpleName();
    private SubjectViewModel subViewModel;
    private SubListAdapter subListAdapter;
    private List<SubEntity> mSubjects;
    private SubEntity subEntity;
    SubListAdapter.onclick mOnclick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_subject);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Subjects");
        //TextView total = (TextView)findViewById(R.id.total);
        TextView present = (TextView)findViewById(R.id.tv1);
        TextView subname = (TextView)findViewById(R.id.subName);


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        subListAdapter = new SubListAdapter(this,this,mOnclick);
        recyclerView.setAdapter(subListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //over scroll animation
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubjectActivity.this, Activity_Add_Subject.class);
                startActivityForResult(intent, NEW_SUBJECT_ACTIVITY_REQUEST_CODE);
            }
        });

       subViewModel = new ViewModelProvider(this).get(SubjectViewModel.class);


        subViewModel.getAllSubjects().observe(this, new Observer<List<SubEntity>>() {
            @Override
            public void onChanged(@Nullable List<SubEntity> subjects) {
                subListAdapter.setmSubjects(subjects);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                subViewModel.delete(subListAdapter.getSubjectAt(viewHolder.getAdapterPosition()));     //here check
                Toast.makeText(SubjectActivity.this,"subject deleted",Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_SUBJECT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            // Code to insert note
            final String sub_id = UUID.randomUUID().toString();
            SubEntity Subject = new SubEntity(sub_id,data.getStringExtra(Activity_Add_Subject.SUBJECT_ADDED), 0,0);
            subViewModel.insert(Subject);

            Toast.makeText(
                    getApplicationContext(),
                    "Subject added",
                    Toast.LENGTH_LONG).show();
        } else if (requestCode == UPDATE_SUBJECT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            // Code to update the note

//
//            SubEntity subEntity = new SubEntity(data.getStringExtra(Activity_Edit_Subject.SUBJECT_ID), data.getStringExtra(Activity_Edit_Subject.UPDATED_SUBJECT), data.getStringExtra(Activity_Edit_Subject.UPDATED_PRESENT),
//                    data.getStringExtra(Activity_Edit_Subject.UPDATED_ABSENT));
//            subViewModel.update(subEntity);



            Toast.makeText(
                    getApplicationContext(),
                    "Subject Updated",
                    Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Subject not added",
                    Toast.LENGTH_LONG).show();
        }
    }




    @Override
    public void present(View v, int position, String id) {
        TextView present = (TextView)findViewById(R.id.tv1);
        TextView subname = (TextView)findViewById(R.id.subName);

        int pre = Integer.parseInt((String)present .getText());

        subViewModel.updatePresent(pre, id);
        subEntity.setPresent(pre);



    }

    @Override
    public void absent(View v, int position, String id) {
        //pending
    }

    }




