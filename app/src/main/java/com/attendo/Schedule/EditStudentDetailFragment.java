package com.attendo.Schedule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.data.model.schedule.Schedule;
import com.attendo.ui.CustomLoadingDialog;

public class EditStudentDetailFragment extends Fragment {

    private EditText UserName,ScholarId;
    private Button btn;
    private CustomLoadingDialog customLoadingDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_student_detail, container, false);

        UserName = view.findViewById(R.id.edit_username);
        ScholarId = view.findViewById(R.id.edit_scholarId);
        btn = view.findViewById(R.id.update_userdetail);

        customLoadingDialog = new CustomLoadingDialog(getActivity());
//uYonvO
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserName.getText().toString().length()==0 || ScholarId.getText().toString().length()==0){
                    Toast.makeText(getActivity(),"Please fill your details",Toast.LENGTH_SHORT).show();
                }
                else{
                    customLoadingDialog.startDialog(false);
                    SendToServer();
                }
            }
        });

        return view;
    }

    private void SendToServer() {
        //code for sending date to server......
    }
}