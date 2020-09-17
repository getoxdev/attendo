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

import com.example.attendo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentEditAttendanceCriteria#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEditAttendanceCriteria extends Fragment {

    @BindView(R.id.button2)
    Button change;

    @BindView(R.id.editText)
    EditText criteria;

    @BindView(R.id.textView10)
    TextView percentage;
    private String text;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentEditAttendanceCriteria() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentEditAttendanceCriteria.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentEditAttendanceCriteria newInstance(String param1, String param2) {
        FragmentEditAttendanceCriteria fragment = new FragmentEditAttendanceCriteria();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit_attendance_criteria, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Attendance Criterion");
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(View.GONE);
        ButterKnife.bind(this,view);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                percentage.setText(criteria.getText().toString());
                SaveDate();



            }
        });

        loadData();
        updateDate();


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
    public String fetchVAlue()
    {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Mypref",getContext().MODE_PRIVATE);
        text = sharedPreferences.getString("Criterion","75");
        return text;


    }
    public void updateDate()
    {
        percentage.setText(text);
    }

}
