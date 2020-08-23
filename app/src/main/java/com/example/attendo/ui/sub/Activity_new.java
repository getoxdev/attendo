package com.example.attendo.ui.sub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.example.attendo.R;

public class Activity_new extends AppCompatActivity {

    public static final String NOTE_ADDED = "Subject Added";

    private EditText etNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);


        etNewNote = findViewById(R.id.etNewNote);

        Button button = findViewById(R.id.bAdd);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent resultIntent = new Intent();

                if (TextUtils.isEmpty(etNewNote.getText())) {
                    setResult(RESULT_CANCELED, resultIntent);
                } else {
                    String note = etNewNote.getText().toString();
                    resultIntent.putExtra(NOTE_ADDED, note);
                    setResult(RESULT_OK, resultIntent);
                }

                finish();
            }
        });

    }
}