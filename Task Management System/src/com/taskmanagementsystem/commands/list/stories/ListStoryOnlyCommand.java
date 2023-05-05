package com.taskmanagementsystem.commands.list.stories;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.tasks.contracts.Story;
import com.taskmanagementsystem.utils.ListingHelpers;

import java.util.List;

public class ListStoryOnlyCommand implements Command {

    private final List<Story> stories;

    public ListStoryOnlyCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        stories = taskManagementSystemRepository.getStories();
    }


    @Override
    public String execute(List<String> parameters) {
        if (stories.isEmpty()) {
            return "There are no stories in the system.";
        }
        return ListingHelpers.listStoryToString(stories);
    }
}
