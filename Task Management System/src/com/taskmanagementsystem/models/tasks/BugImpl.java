package com.taskmanagementsystem.models.tasks;

import com.taskmanagementsystem.exceptions.InvalidUserInputException;
import com.taskmanagementsystem.models.contracts.Comment;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.models.tasks.contracts.Bug;
import com.taskmanagementsystem.models.tasks.enums.BugStatus;
import com.taskmanagementsystem.models.tasks.enums.Priority;
import com.taskmanagementsystem.models.tasks.enums.Severity;

import java.util.ArrayList;
import java.util.List;

public class BugImpl extends AbstractTaskBase implements Bug {

    private static final BugStatus FINAL_STATUS = BugStatus.FIXED;

    private static final String STATUS_CHANGE_ERROR = "The status cannot be changed! It is already fixed!";
    private final List<String> steps;
    private Severity severity;
    private BugStatus status;

    public BugImpl(int id, String title, String description, List<String> steps,
                   Priority priority, Severity severity, Member assignee) {
        super(id, title, description, priority, assignee);
        setSeverity(severity);
        this.steps = steps;
        this.status = BugStatus.ACTIVE;
    }

    @Override
    public Severity getSeverity() {
        return severity;
    }

    private void setSeverity(Severity severity) {
        this.severity = severity;
    }

    @Override
    public BugStatus getStatus() {
        return status;
    }

    private void setStatus(BugStatus status) {
        this.status = status;
    }

    @Override
    public List<String> getSteps() {
        return new ArrayList<>(steps);
    }

    @Override
    public void changeBugStatus() {
        if (status != FINAL_STATUS) {
            setStatus(BugStatus.values()[status.ordinal() + 1]);
        } else {
            throw new InvalidUserInputException(STATUS_CHANGE_ERROR.trim());
        }
    }

    @Override
    public void changeSeverity(Severity severity) {
        if (this.severity == severity) {
            throw new InvalidUserInputException(String.format("The severity is already %s!", severity));
        }
        this.severity = severity;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%s", super.toString()))
                .append(System.lineSeparator())
                .append(String.format("Severity: %s", getSeverity()))
                .append(System.lineSeparator())
                .append(String.format("Status: %s", getStatus()))
                .append(System.lineSeparator())
                .append("Steps to reproduce:")
                .append(System.lineSeparator());

        int counter = 1;
        for (String step : steps) {
            stringBuilder.append(String.format("%d. %s%n", counter++, step));
        }

        if (getComments().isEmpty()) {
            stringBuilder.append("Comments: None").append(System.lineSeparator());
        } else {
            stringBuilder.append("Comments--->").append(System.lineSeparator());
            for (Comment comment : getComments()) {
                stringBuilder.append(comment).append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }
}
