package com.example.attendo.ui.sub;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.attendo.R;
import com.example.attendo.data.SubEntity;

public class Activity_Edit_Subject extends AppCompatActivity {
/*
    public static final String SUBJECT_ID = "sub_id";
    static final String UPDATED_SUBJECT = "sub_text";
    static final String UPDATED_PRESENT = "present";
    static final String UPDATED_ABSENT = "absent";
    private EditText etSubject;
    private TextView absent,present;
    private Bundle bundle;
    private String subId;
    private LiveData<SubEntity> SUBJECT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        etSubject = findViewById(R.id.etNote);
        absent 	 = findViewById(R.id.bAbsent);
        present	 = findViewById(R.id.bPresent);

        bundle = getIntent().getExtras();

        if (bundle != null) {
            subId = bundle.getString(SUBJECT_ID);
        }

        subModel= new ViewModelProvider(this).get(EditSubjectViewModel.class);
        SUBJECT = subModel.getSubject(subId);

        SUBJECT.observe(this, new Observer<SubEntity>() {
            @Override
            public void onChanged(@Nullable SubEntity SUBJECT) {
                etSubject.setText("ssjideje");
            }
        });
    }

    public void updateNote(View view) {
        String updatedSubject = etSubject.getText().toString();
        String updatedPresent = present.getText().toString();
        String updatedAbsent = absent.getText().toString();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(SUBJECT_ID, subId);
        resultIntent.putExtra(UPDATED_SUBJECT, updatedSubject);
        resultIntent.putExtra(UPDATED_PRESENT,updatedPresent);
        resultIntent.putExtra(UPDATED_ABSENT,updatedAbsent);

        setResult(RESULT_OK, resultIntent);
        finish();

    }


    public void cancelUpdate(View view) {
        finish();
    }
*/
}