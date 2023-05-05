package com.taskmanagementsystem.commands.change.bug;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.tasks.contracts.Bug;
import com.taskmanagementsystem.models.tasks.enums.Severity;
import com.taskmanagementsystem.utils.ParsingHelpers;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class ChangeSeverityOfBugCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private static final String WRONG_BUG_ID = "Invalid bug ID!";
    private static final String BUG_SEVERITY_ERROR = "Invalid bug severity!";
    private static final String BUG_SEVERITY_CHANGED = "Severity of bug with ID %d was changed to %s.";
    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public ChangeSeverityOfBugCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        int id = ParsingHelpers.tryParseInteger(parameters.get(0), WRONG_BUG_ID);
        Severity severity = ParsingHelpers.tryParseEnum(parameters.get(1), Severity.class, BUG_SEVERITY_ERROR);
        return changeSeverityOfBug(id, severity);
    }

    private String changeSeverityOfBug(int id, Severity severity) {
        Bug bug = taskManagementSystemRepository.findBugById(id);
        bug.changeSeverity(severity);
        return String.format(BUG_SEVERITY_CHANGED, id, severity);
    }
}
