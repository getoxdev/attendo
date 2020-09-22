package com.example.attendo.ui.main.drawers;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentEditAttendanceCriteria extends Fragment {

    @BindView(R.id.button2)
    Button change;

    @BindView(R.id.editText)
    EditText criteria;

    @BindView(R.id.textView10)
    TextView percentage;
    private String text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit_attendance_criteria, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Attendance Criterion");
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(View.GONE);
        ButterKnife.bind(this,view);
        loadData();
        updateDate();
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String limit = criteria.getText().toString();
                if(limit.length() >= 3 && !(limit.equals("100"))){
                    Toast.makeText(getActivity(),"Out Of Range Critaria!",Toast.LENGTH_SHORT).show();
                }
                else {
                    percentage.setText(criteria.getText().toString());
                    SaveDate();
                    loadData();
                    updateDate();
                    getParentFragmentManager().popBackStack();
                }
            }
        });

        return view;
    }

    public void SaveDate()
    {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Mypref",getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("Criterion",percentage.getText().toString());
        editor.apply();
    }

    public void loadData()
    {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Mypref",getContext().MODE_PRIVATE);
        text = sharedPreferences.getString("Criterion","75");
    }

    public void updateDate()
    {
        percentage.setText(text + "%");
    }
}
