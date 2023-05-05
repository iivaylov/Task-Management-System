package com.taskmanagementsystem.commands.list.bugs;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.tasks.contracts.Bug;
import com.taskmanagementsystem.utils.ListingHelpers;

import java.util.List;

public class ListBugsOnlyCommand implements Command {

    private final List<Bug> bugs;

    public ListBugsOnlyCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        bugs = taskManagementSystemRepository.getBugs();
    }

    @Override
    public String execute(List<String> parameters) {
        if (bugs.isEmpty()) {
            return "There are no bugs in the system.";
        }
        return ListingHelpers.listBugToString(bugs);
    }
}
