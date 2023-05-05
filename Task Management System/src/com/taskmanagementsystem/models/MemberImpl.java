package com.taskmanagementsystem.models;

import com.taskmanagementsystem.models.contracts.History;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.models.tasks.contracts.Task;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class MemberImpl implements Member {

    public static final int NAME_MIN_LENGTH = 5;
    public static final int NAME_MAX_LENGTH = 15;
    public static final String NAME_ERROR_MESSAGE =
            String.format("Person name should be between %d and %d symbols.", NAME_MIN_LENGTH, NAME_MAX_LENGTH);
    private final List<Task> tasks;
    private final List<History> histories = new ArrayList<>();
    private String name;

    public MemberImpl(String name) {
        setName(name);
        this.tasks = new ArrayList<>();

        this.logHistory(format("Person with name %s was created.", this.viewInfo()));
    }

    @Override
    public String getName() {
        return name;
    }

    private void setName(String name) {
        ValidationHelpers.validateStringLength(name, NAME_MIN_LENGTH, NAME_MAX_LENGTH, NAME_ERROR_MESSAGE);
        this.name = name;
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);

        this.logHistory(format("Task with ID %d was added to member %s.", task.getId(), this.viewInfo()));
    }

    @Override
    public void logHistory(String message) {
        this.histories.add(new HistoryImpl(message));
    }

    @Override
    public String viewInfo() {
        return String.format("%s", this.getName());
    }

    @Override
    public String showMemberActivityHistory() {
        StringBuilder sb = new StringBuilder();
        sb.append("Activity history for member ").append(this.getName()).append(":").append(System.lineSeparator());
        for (History history : histories) {
            sb.append(history.viewInfo()).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
