package com.taskmanagementsystem.models.tasks.contracts;

import com.taskmanagementsystem.models.tasks.enums.BugStatus;
import com.taskmanagementsystem.models.tasks.enums.Severity;

import java.util.List;

public interface Bug extends Task, AbstractTask {

    Severity getSeverity();

    BugStatus getStatus();

    List<String> getSteps();

    void changeBugStatus();

    void changeSeverity(Severity severity);
}
