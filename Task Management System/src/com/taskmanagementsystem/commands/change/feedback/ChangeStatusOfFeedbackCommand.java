package com.taskmanagementsystem.commands.change.feedback;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.tasks.contracts.Feedback;
import com.taskmanagementsystem.utils.ParsingHelpers;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class ChangeStatusOfFeedbackCommand implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    public static final String INVALID_FEEDBACK_ID = "Invalid feedback ID!";
    public static final String STATUS_CHANGED_FEEDBACK = "Status of feedback with ID %d was changed to %s.";
    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public ChangeStatusOfFeedbackCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        int id = ParsingHelpers.tryParseInteger(parameters.get(0), INVALID_FEEDBACK_ID);
        return changeStatusOfFeedback(id);
    }

    private String changeStatusOfFeedback(int id) {
        Feedback feedback = taskManagementSystemRepository.findFeedbackById(id);
        feedback.changeFeedbackStatus();
        return String.format(STATUS_CHANGED_FEEDBACK, id, feedback.getStatus());
    }
}
