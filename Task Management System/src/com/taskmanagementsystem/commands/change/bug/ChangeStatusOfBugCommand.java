package com.taskmanagementsystem.commands.change.bug;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.tasks.contracts.Bug;
import com.taskmanagementsystem.utils.ParsingHelpers;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class ChangeStatusOfBugCommand implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private static final String BUG_STATUS_CHANGED = "Status of bug with ID %d was changed to %s.";
    private static final String WRONG_BUG_ID = "Invalid bug ID!";
    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public ChangeStatusOfBugCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        int id = ParsingHelpers.tryParseInteger(parameters.get(0), WRONG_BUG_ID);
        return changeStatusOfBug(id);
    }

    private String changeStatusOfBug(int id) {
        Bug bug = taskManagementSystemRepository.findBugById(id);
        bug.changeBugStatus();
        return String.format(BUG_STATUS_CHANGED, id, bug.getStatus());
    }
}
