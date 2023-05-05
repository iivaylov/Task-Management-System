package com.taskmanagementsystem.commands.list.stories;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.commands.enums.Order;
import com.taskmanagementsystem.commands.enums.StoryListing;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.models.tasks.contracts.Story;
import com.taskmanagementsystem.models.tasks.enums.StoryStatus;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;

import static com.taskmanagementsystem.utils.ParsingHelpers.tryParseEnum;

public class ListStoriesFilterAndSortCommand implements Command {

    public static final String ERROR_STATUS = "Invalid story status";
    public static final String ERROR_STORY_LISTING = "Invalid story listing";
    public static final String ERROR_ORDER = "Invalid order";
    public static final String NO_STORIES = "There are no stories in the database.";

    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 4;

    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public ListStoriesFilterAndSortCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        StoryStatus status = tryParseEnum(parameters.get(0), StoryStatus.class, ERROR_STATUS);
        Member assignee = taskManagementSystemRepository.findMemberByName(parameters.get(1));
        StoryListing storyListing = tryParseEnum(parameters.get(2), StoryListing.class, ERROR_STORY_LISTING);
        Order order = tryParseEnum(parameters.get(3), Order.class, ERROR_ORDER);
        return listStories(status, assignee, storyListing, order);
    }

    private String listStories(StoryStatus status, Member assignee, StoryListing storyListing, Order order) {
        StringBuilder sb = new StringBuilder();
        if (taskManagementSystemRepository.getFeedbacks().isEmpty()) {
            sb.append(NO_STORIES);
            return sb.toString();
        }
        if (storyListing == StoryListing.TITLE) {
            listingOrder(status, assignee, order, sb, Comparator.comparing(Story::getTitle));
        } else {
            listingOrder(status, assignee, order, sb, Comparator.comparing(Story::getSize));
        }
        return sb.toString().trim();
    }

    private void listingOrder(StoryStatus status, Member assignee, Order order, StringBuilder sb,
                              Comparator<Story> comparator) {
        if (order == Order.ASCENDING) {
            taskManagementSystemRepository.getStories().stream()
                    .filter(story -> story.getStatus().equals(status))
                    .filter(story -> story.getAssignee().equals(assignee))
                    .sorted(comparator)
                    .forEach(story ->
                    {
                        sb.append(story);
                        sb.append(System.lineSeparator());
                    });
        } else {
            taskManagementSystemRepository.getStories().stream()
                    .filter(story -> story.getStatus().equals(status))
                    .filter(story -> story.getAssignee().equals(assignee))
                    .sorted(comparator.reversed())
                    .forEach(story ->
                    {
                        sb.append(story);
                        sb.append(System.lineSeparator());
                    });
        }
    }
}
