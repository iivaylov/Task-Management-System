package com.taskmanagementsystem.commands.list;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.tasks.contracts.Task;
import com.taskmanagementsystem.utils.ListingHelpers;

import java.util.List;

public class ListAllTasksCommand implements Command {

    private final List<Task> tasks;

    public ListAllTasksCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        tasks = taskManagementSystemRepository.getTasks();
    }


    @Override
    public String execute(List<String> parameters) {
        if (tasks.isEmpty()) {
            return "There are no tasks in the system.";
        }
        return ListingHelpers.listTasksToString(tasks);
    }
}
