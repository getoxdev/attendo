package com.attendo.Schedule.Interface;

import com.attendo.Schedule.Model.SubjectRoutine;
import com.attendo.data.model.SubjectDetails;

import java.util.ArrayList;
import java.util.List;

public interface UpdateRecyclerView {
    public void callback(int position, List<SubjectDetails> subjectRoutines);
    public void sendPosition(int position);
}
