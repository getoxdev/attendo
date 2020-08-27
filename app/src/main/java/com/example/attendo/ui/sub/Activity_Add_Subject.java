package com.example.attendo.ui.sub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.attendo.R;

public class Activity_Add_Subject extends AppCompatActivity {

    public static final String SUBJECT_ADDED = "Subject Added";
    public static final String Present = "p added";
    public static final String Absent = "a added";
    TextView absent,present;


    private EditText etNewSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);


        etNewSub = findViewById(R.id.etNewNote);
        absent 	 = findViewById(R.id.bAbsent);
        present	 = findViewById(R.id.bPresent);

        Button button = findViewById(R.id.bAdd);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent resultIntent = new Intent();

                if (TextUtils.isEmpty(etNewSub.getText())) {
                    setResult(RESULT_CANCELED, resultIntent);
                } else {
                    String ADD = etNewSub.getText().toString().trim();
//                    String p = present.getText().toString();
//                    String a = absent.getText().toString();
                    resultIntent.putExtra(SUBJECT_ADDED, ADD);
//                    resultIntent.putExtra(Present, p);
//                    resultIntent.putExtra(Absent, a);
                    setResult(RESULT_OK, resultIntent);
                }

                finish();
            }
        });
    }
}