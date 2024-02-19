package com.attendo.Schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.attendo.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.transition.MaterialSharedAxis;

public class CreateAndJoinClassBottomSheetDialogFragment extends BottomSheetDialogFragment {

    Button joinClass;
    Button createClass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_join_class_bottom_sheet, container, false);

        // reference to views in old way
        joinClass = (Button) view.findViewById(R.id.join_class_btn);
        createClass = (Button) view.findViewById(R.id.create_class_btn);

        joinClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment studentFragment = new StudentDetailsInputFragment();
                setFragment(studentFragment);
                dismiss();

            }
        });
        createClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create class fragment for data collection
                Fragment crfragment = new CRDetailsInputFragment();
                setFragment(crfragment);
                dismiss();
            }
        });

        return view;
    }

    public void setFragment(Fragment fragment){
        fragment.setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, true));
        fragment.setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, false));
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame, fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }
}
