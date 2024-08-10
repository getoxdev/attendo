package com.attendo.Schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.attendo.databinding.FragmentNoticeBodyBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NoticeBodyBottomSheetFragment extends BottomSheetDialogFragment {

    private static final String TITLE = "title_notice";
    private static final String BODY = "body_notice";

    private FragmentNoticeBodyBottomSheetBinding binding;

    private String mTitle;
    private String mBody;

    public NoticeBodyBottomSheetFragment() {
        // Required empty public constructor
    }

    public static NoticeBodyBottomSheetFragment newInstance(String title, String body) {
        NoticeBodyBottomSheetFragment fragment = new NoticeBodyBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(BODY, body);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
            mBody = getArguments().getString(BODY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNoticeBodyBottomSheetBinding.inflate(inflater, container, false);

        binding.titleNotice.setText(mTitle);
        binding.bodyNotice.setText(mBody);

        return binding.getRoot();
    }
}