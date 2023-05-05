package com.taskmanagementsystem.commands.list.feedbacks;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.tasks.contracts.Feedback;
import com.taskmanagementsystem.utils.ListingHelpers;

import java.util.List;

public class ListFeedbacksOnlyCommand implements Command {

    private final List<Feedback> feedbacks;

    public ListFeedbacksOnlyCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        feedbacks = taskManagementSystemRepository.getFeedbacks();
    }

    @Override
    public String execute(List<String> parameters) {
        if (feedbacks.isEmpty()) {
            return "There are no feedbacks in the system.";
        }
        return ListingHelpers.listFeedbackToString(feedbacks);
    }
}
