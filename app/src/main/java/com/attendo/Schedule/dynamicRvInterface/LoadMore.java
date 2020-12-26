package com.attendo.Schedule.dynamicRvInterface;

import com.attendo.Schedule.Model.SubjectRoutine;

import java.util.ArrayList;

public interface LoadMore {
    public void callback(int position, ArrayList<SubjectRoutine> subjectRoutines);
}
