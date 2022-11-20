package com.attendo.ui.main.drawers;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.databinding.FragmentEditAttendanceCriteriaBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentEditAttendanceCriteria extends Fragment {
    FragmentEditAttendanceCriteriaBinding binding;
    Button change;
    EditText criteria;
    TextView percentage;
    private String text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditAttendanceCriteriaBinding.inflate(inflater,container,false);
        change = binding.button2;
        criteria = binding.editText;
        percentage = binding.textView9;
        // Inflate the layout for this fragmen
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Attendance Criterion");
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(View.GONE);
        loadData();
        updateDate();
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String limit = criteria.getText().toString();
                if(limit.length() >= 3 && !(limit.equals("100"))){
                    Toast.makeText(getActivity(),"Out Of Range Criteria!",Toast.LENGTH_SHORT).show();
                }
                else if(limit.length()==0)
                {
                    Toast.makeText(getActivity(), "Please enter the Criteria", Toast.LENGTH_SHORT).show();
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

        return binding.getRoot();
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
