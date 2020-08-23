package com.example.attendo.ui.sub;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.attendo.R;
import com.example.attendo.data.SubEntity;
import com.example.attendo.viewmodel.EditNoteViewModel;

public class Activity_edit extends AppCompatActivity {

    public static final String NOTE_ID = "sub_id";
    static final String UPDATED_NOTE = "sub_text";
    private EditText etNote;
    private Bundle bundle;
    private String noteId;
    private LiveData<SubEntity> note;

    EditNoteViewModel noteModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        etNote = findViewById(R.id.etNote);

        bundle = getIntent().getExtras();

        if (bundle != null) {
            noteId = bundle.getString("note_id");
        }

       // noteModel = new  ViewModelProvider(this).get(EditNoteViewModel.class);
        note = noteModel.getNote(noteId);
        note.observe(this, new Observer<SubEntity>() {
            @Override
            public void onChanged(@Nullable SubEntity note) {
                etNote.setText(note.getNote());
            }
        });
    }

    public void updateNote(View view) {
        String updatedNote = etNote.getText().toString();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(NOTE_ID, noteId);
        resultIntent.putExtra(UPDATED_NOTE, updatedNote);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void cancelUpdate(View view) {
        finish();
    }

}