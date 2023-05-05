package com.taskmanagementsystem.commands.change.story;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.tasks.contracts.Story;
import com.taskmanagementsystem.models.tasks.enums.Priority;
import com.taskmanagementsystem.utils.ParsingHelpers;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class ChangePriorityOfStoryCommand implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private static final String PRIORITY_STORY_CHANGED = "Priority of story with ID %d is changed to %s.";
    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public ChangePriorityOfStoryCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }


    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        int id = ParsingHelpers.tryParseInteger(parameters.get(0), "Invalid story ID!");
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(1), Priority.class,
                "Invalid story priority!");
        return changePriorityOfStory(id, priority);
    }

    private String changePriorityOfStory(int id, Priority priority) {
        Story story = taskManagementSystemRepository.findStoryById(id);
        story.changePriority(priority);
        return String.format(PRIORITY_STORY_CHANGED, id, story.getPriority());
    }
}
