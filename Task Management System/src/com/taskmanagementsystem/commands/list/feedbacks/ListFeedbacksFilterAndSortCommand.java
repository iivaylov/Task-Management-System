package com.taskmanagementsystem.commands.list.feedbacks;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.commands.enums.FeedbackListing;
import com.taskmanagementsystem.commands.enums.Order;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.tasks.contracts.Feedback;
import com.taskmanagementsystem.models.tasks.enums.FeedbackStatus;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;

import static com.taskmanagementsystem.utils.ParsingHelpers.tryParseEnum;

public class ListFeedbacksFilterAndSortCommand implements Command {

    public static final String ERROR_STATUS = "Invalid feedback rate";
    public static final String ERROR_LISTING = "Invalid feedback listing";
    public static final String NO_FEEDBACKS = "There are no feedbacks in the database.";
    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 3;

    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public ListFeedbacksFilterAndSortCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        FeedbackStatus status = tryParseEnum(parameters.get(0), FeedbackStatus.class, ERROR_STATUS);
        FeedbackListing feedbackListing = tryParseEnum(parameters.get(1), FeedbackListing.class, ERROR_LISTING);
        Order order = tryParseEnum(parameters.get(2), Order.class, ERROR_LISTING);
        return listFeedback(status, feedbackListing, order);
    }

    private String listFeedback(FeedbackStatus status, FeedbackListing feedbackListing, Order order) {
        StringBuilder sb = new StringBuilder();
        if (taskManagementSystemRepository.getFeedbacks().isEmpty()) {
            sb.append(NO_FEEDBACKS);
            return sb.toString();
        }
        if (feedbackListing == FeedbackListing.TITLE) {
            listingOrder(status, order, sb, Comparator.comparing(Feedback::getTitle));
        } else {
            listingOrder(status, order, sb, Comparator.comparing(Feedback::getRating));
        }
        return sb.toString().trim();
    }

    private void listingOrder(FeedbackStatus status, Order order, StringBuilder sb, Comparator<Feedback> comparator) {
        if (order == Order.ASCENDING) {
            taskManagementSystemRepository.getFeedbacks().stream()
                    .filter(feedback -> feedback.getStatus().equals(status))
                    .sorted(comparator)
                    .forEach(feedback ->
                    {
                        sb.append(feedback);
                        sb.append(System.lineSeparator());
                    });
        } else {
            taskManagementSystemRepository.getFeedbacks().stream()
                    .filter(feedback -> feedback.getStatus().equals(status))
                    .sorted(comparator.reversed())
                    .forEach(feedback ->
                    {
                        sb.append(feedback);
                        sb.append(System.lineSeparator());
                    });
        }
    }
}
