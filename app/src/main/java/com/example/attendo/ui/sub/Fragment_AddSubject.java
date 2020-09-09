package com.example.attendo.ui.sub;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attendo.Fragment_Subject;
import com.example.attendo.R;

public class Fragment_AddSubject extends Fragment {

    EditText etNewSub;
    Button button;
    private Fragment_Subject fragment_subject;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Fragment_AddSubject() {
        // Required empty public constructor
    }
    public static Fragment_AddSubject newInstance(String param1, String param2) {
        Fragment_AddSubject fragment = new Fragment_AddSubject();
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
        View view = inflater.inflate(R.layout.fragment__add_subject, container, false);




        fragment_subject = new Fragment_Subject();

        etNewSub = view.findViewById(R.id.etNewNote);
        button = view.findViewById(R.id.bAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etNewSub.getText().toString().trim();
                etNewSub.setText("");
                if(data.isEmpty()){
                    Toast.makeText(getActivity()," Subject in not added ",Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("key","");
                    fragment_subject.setArguments(bundle);
                    setFragment(fragment_subject);
                }
                else{
                    Bundle bundle = new Bundle();
                    bundle.putString("key",data);
                    fragment_subject.setArguments(bundle);
                    setFragment(fragment_subject);
                }
            }
        });

    return view;
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }


}
