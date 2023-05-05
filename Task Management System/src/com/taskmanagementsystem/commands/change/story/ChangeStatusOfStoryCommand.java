package com.taskmanagementsystem.commands.change.story;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.tasks.contracts.Story;
import com.taskmanagementsystem.utils.ParsingHelpers;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class ChangeStatusOfStoryCommand implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public ChangeStatusOfStoryCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }


    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        int id = ParsingHelpers.tryParseInteger(parameters.get(0), "Invalid story ID!");
        return changeStatusOfStory(id);
    }

    private String changeStatusOfStory(int id) {
        Story story = taskManagementSystemRepository.findStoryById(id);
        story.changeStoryStatus();
        return String.format("Status of story with ID %d was changed to %s.", id, story.getStatus());
    }
}
