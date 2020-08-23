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

import com.example.attendo.viewmodel.NoteViewModel;
import com.example.attendo.R;
import com.example.attendo.data.SubEntity;
import com.example.attendo.data.SubListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.UUID;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class SubActivity extends AppCompatActivity {

    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;
    private String TAG = this.getClass().getSimpleName();
    private NoteViewModel noteViewModel;
    private SubListAdapter noteListAdapter;
    private List<SubEntity> mNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_subject);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Subjects");
        TextView total = (TextView)findViewById(R.id.total);
        TextView presnt = (TextView)findViewById(R.id.tv1);


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        noteListAdapter = new SubListAdapter(this,this);
        recyclerView.setAdapter(noteListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //over scroll animation
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubActivity.this, Activity_new.class);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });

       noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<SubEntity>>() {
            @Override
            public void onChanged(@Nullable List<SubEntity> notes) {
                noteListAdapter.setNotes(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(noteListAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(SubActivity.this,"subject deleted",Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            // Code to insert note
            final String note_id = UUID.randomUUID().toString();
            SubEntity note = new SubEntity( note_id,data.getStringExtra(Activity_new.NOTE_ADDED),0,0);
            noteViewModel.insert(note);

            Toast.makeText(
                    getApplicationContext(),
                    "Subject added",
                    Toast.LENGTH_LONG).show();
        } else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

             //Code to update the note
//            Note note = new Note(
//                    data.getStringExtra(Activity_edit.NOTE_ID),
//                    data.getStringExtra(Activity_edit.UPDATED_NOTE));
//            noteViewModel.update(note);

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

}