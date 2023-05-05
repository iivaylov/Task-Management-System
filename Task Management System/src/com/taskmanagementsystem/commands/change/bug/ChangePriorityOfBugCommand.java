package com.taskmanagementsystem.commands.change.bug;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.tasks.contracts.Bug;
import com.taskmanagementsystem.models.tasks.enums.Priority;
import com.taskmanagementsystem.utils.ParsingHelpers;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class ChangePriorityOfBugCommand implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private static final String WRONG_BUG_ID = "Invalid bug ID!";
    private static final String PRIORITY_ERROR = "Invalid bug priority!";
    private static final String BUG_PRIORITY_CHANGED = "Priority of bug with ID %d was changed to %s.";
    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public ChangePriorityOfBugCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }


    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        int id = ParsingHelpers.tryParseInteger(parameters.get(0), WRONG_BUG_ID);
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(1), Priority.class, PRIORITY_ERROR);
        return changePriorityOfBug(id, priority);
    }

    private String changePriorityOfBug(int id, Priority priority) {
        Bug bug = taskManagementSystemRepository.findBugById(id);
        bug.changePriority(priority);
        return String.format(BUG_PRIORITY_CHANGED, id, priority);
    }
}
