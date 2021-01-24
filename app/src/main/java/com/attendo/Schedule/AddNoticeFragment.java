package com.attendo.Schedule;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.attendo.R;

public class AddNoticeFragment extends Fragment {

    private EditText title,body;
    private Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_notice, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Notice");

        title = view.findViewById(R.id.Title);
        body = view.findViewById(R.id.body);
        btn = view.findViewById(R.id.btn_send);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().length()==0 || body.getText().toString().length()==0){
                    Toast.makeText(getActivity(),"Please fill the details",Toast.LENGTH_SHORT).show();
                }
                else{
                    sendatatoserver();
                }
            }
        });

        return  view;
    }

    private void sendatatoserver() {
        String TITLE = title.getText().toString().trim();
        String BODY = body.getText().toString().trim();
        Toast.makeText(getActivity(),"Notice sended!",Toast.LENGTH_SHORT).show();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}