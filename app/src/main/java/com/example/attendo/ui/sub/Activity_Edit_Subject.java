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
import com.example.attendo.viewmodel.EditSubjectViewModel;

public class Activity_Edit_Subject extends AppCompatActivity {

    public static final String SUBJECT_ID = "sub_id";
    static final String UPDATED_SUBJECT = "sub_text";
    private EditText etSubject;
    private Bundle bundle;
    private String subId;
    private LiveData<SubEntity> SUBJECT;

    EditSubjectViewModel subModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        etSubject = findViewById(R.id.etNote);

        bundle = getIntent().getExtras();

        if (bundle != null) {
            subId = bundle.getString("note_id");
        }

       // noteModel = new  ViewModelProvider(this).get(EditNoteViewModel.class);
        SUBJECT = subModel.getSubject(subId);
        SUBJECT.observe(this, new Observer<SubEntity>() {
            @Override
            public void onChanged(@Nullable SubEntity SUBJECT) {
                etSubject.setText(SUBJECT.getSubject());
            }
        });
    }

    public void updateNote(View view) {
        String updatedSubject = etSubject.getText().toString();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(SUBJECT_ID, subId);
        resultIntent.putExtra(UPDATED_SUBJECT, updatedSubject);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void cancelUpdate(View view) {
        finish();
    }

}