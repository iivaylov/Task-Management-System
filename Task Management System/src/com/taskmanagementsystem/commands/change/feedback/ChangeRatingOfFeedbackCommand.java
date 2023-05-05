package com.taskmanagementsystem.commands.change.feedback;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.tasks.contracts.Feedback;
import com.taskmanagementsystem.utils.ParsingHelpers;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class ChangeRatingOfFeedbackCommand implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    public static final String INVALID_FEEDBACK_ID = "Invalid feedback ID!";
    public static final String INVALID_RATING = "Invalid rating!";
    public static final String RATING_CHANGED = "Rating of feedback with ID %d was changed to %d.";
    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public ChangeRatingOfFeedbackCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        int id = ParsingHelpers.tryParseInteger(parameters.get(0), INVALID_FEEDBACK_ID);
        int rating = ParsingHelpers.tryParseInteger(parameters.get(1), INVALID_RATING);
        return changeRatingOfFeedback(id, rating);
    }

    private String changeRatingOfFeedback(int id, int rating) {
        Feedback feedback = taskManagementSystemRepository.findFeedbackById(id);
        feedback.changeRating(rating);
        return String.format(RATING_CHANGED, id, rating);
    }
}
