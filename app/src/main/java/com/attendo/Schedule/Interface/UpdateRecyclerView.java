package com.attendo.Schedule.Interface;

import com.attendo.data.model.schedule.SubjectDetails;

import java.util.List;

public interface UpdateRecyclerView {
    void callback(int position, List<SubjectDetails> subjectRoutines);
    void sendPosition(int position);

}
