package com.example.attendo.ui.sub;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendo.viewmodel.SubjectViewModel;
import com.example.attendo.R;
import com.example.attendo.data.SubEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class SubjectActivity extends AppCompatActivity{

    private static final int NEW_SUBJECT_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_SUBJECT_ACTIVITY_REQUEST_CODE = 2;
    private SubjectViewModel subViewModel;
    private SubListAdapter subListAdapter;
    private List<SubEntity> mSubjects=new ArrayList<>();

    @BindView(R.id.tvPres)
    TextView present;

    @BindView(R.id.tvTotal)
    TextView total;

    @BindView(R.id.recyclerview)
    RecyclerView subRView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_subject);

        final Vibrator vibrator = (Vibrator) SubjectActivity.this.getSystemService(Context.VIBRATOR_SERVICE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Subjects");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubjectActivity.this, Activity_Add_Subject.class);
                startActivityForResult(intent, NEW_SUBJECT_ACTIVITY_REQUEST_CODE);
            }
        });

        subListAdapter = new SubListAdapter(this,mSubjects);
        subViewModel = new ViewModelProvider(this).get(SubjectViewModel.class);
        subViewModel.getAllSubjects().observe(this, new Observer<List<SubEntity>>() {
            @Override
            public void onChanged(@Nullable List<SubEntity> subjects) {
                subListAdapter.setSubjects(subjects);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(subListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //over scroll animation
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
    }

    private void initViewModel()
    {
        /*Observer<List<SubEntity>> subObserver = new Observer<List<SubEntity>>() {
            @Override
            public void onChanged(List<SubEntity> subEntities) {
                mSubjects.clear();
                mSubjects.addAll(subEntities);

                if(subListAdapter == null)
                {
                    subListAdapter = new SubListAdapter(SubjectActivity.this,mSubjects);
                    subRView.setAdapter(subListAdapter);
                    if(subListAdapter.getItemCount()==0)
                    {
                        emptyText.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    mNotesAdapter.notifyDataSetChanged();
                }
            }
        };

        subViewModel = ViewModelProviders.of(this).get(SubjectViewModel.class);

        listViewModel.mNotesList.observe(MainActivity.this,notesObserver);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_SUBJECT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            // Code to insert note
           // final String sub_id = UUID.randomUUID().toString();
            SubEntity subject = new SubEntity(data.getStringExtra(Activity_Add_Subject.SUBJECT_ADDED), 0,0,0);
            subViewModel.insertSubject(subject);

            Toast.makeText(
                    getApplicationContext(),
                    "Subject Added",
                    Toast.LENGTH_SHORT).show();
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
                    "Subject Not Added",
                    Toast.LENGTH_SHORT).show();
        }
    }
}




