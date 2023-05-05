package com.taskmanagementsystem.commands.list.bugs;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.commands.enums.BugListing;
import com.taskmanagementsystem.commands.enums.Order;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.models.tasks.contracts.Bug;
import com.taskmanagementsystem.models.tasks.enums.BugStatus;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;

import static com.taskmanagementsystem.utils.ParsingHelpers.tryParseEnum;

public class ListBugsFilterAndSortCommand implements Command {

    public static final String ERROR_STATUS = "Invalid bug status.";
    public static final String ERROR_LISTING = "Invalid bug listing.";
    public static final String ERROR_ORDER = "Invalid order.";
    public static final String NO_BUGS = "There are no bugs in the database.";

    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 4;

    private final TaskManagementSystemRepository taskManagementSystemRepository;


    public ListBugsFilterAndSortCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        BugStatus status = tryParseEnum(parameters.get(0), BugStatus.class, ERROR_STATUS);
        Member assignee = taskManagementSystemRepository.findMemberByName(parameters.get(1));
        BugListing bugListing = tryParseEnum(parameters.get(2), BugListing.class, ERROR_LISTING);
        Order order = tryParseEnum(parameters.get(3), Order.class, ERROR_ORDER);
        return listBug(status, assignee, bugListing, order);
    }

    private String listBug(BugStatus status, Member assignee, BugListing bugListing, Order order) {
        StringBuilder sb = new StringBuilder();
        if (taskManagementSystemRepository.getBugs().isEmpty()) {
            sb.append(NO_BUGS);
            return sb.toString();
        }
        if (bugListing == BugListing.TITLE) {
            listingOrder(status, assignee, order, sb, Comparator.comparing(Bug::getTitle));
        } else if (bugListing == BugListing.PRIORITY) {
            listingOrder(status, assignee, order, sb, Comparator.comparing(Bug::getPriority));
        } else {
            listingOrder(status, assignee, order, sb, Comparator.comparing(Bug::getSeverity));
        }
        return sb.toString().trim();
    }

    private void listingOrder(BugStatus status, Member assignee, Order order, StringBuilder sb,
                              Comparator<Bug> comparing) {
        if (order == Order.ASCENDING) {
            taskManagementSystemRepository.getBugs().stream()
                    .filter(bug -> bug.getStatus().equals(status))
                    .filter(bug -> bug.getAssignee().equals(assignee))
                    .sorted(comparing)
                    .forEach(bug ->
                    {
                        sb.append(bug);
                        sb.append(System.lineSeparator());
                    });
        } else {
            taskManagementSystemRepository.getBugs().stream()
                    .filter(bug -> bug.getStatus().equals(status))
                    .filter(bug -> bug.getAssignee().equals(assignee))
                    .sorted(comparing.reversed())
                    .forEach(bug ->
                    {
                        sb.append(bug);
                        sb.append(System.lineSeparator());
                    });
        }
    }
}
