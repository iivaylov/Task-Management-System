package com.taskmanagementsystem.models.contracts;

import com.taskmanagementsystem.models.tasks.contracts.Task;

import java.util.List;

public interface Member {

    String getName();

    List<Task> getTasks();

    String showMemberActivityHistory();

    String viewInfo();

    void addTask(Task task);

    void logHistory(String message);

}
