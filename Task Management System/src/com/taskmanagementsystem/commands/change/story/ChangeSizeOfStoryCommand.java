package com.taskmanagementsystem.commands.change.story;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.tasks.contracts.Story;
import com.taskmanagementsystem.models.tasks.enums.StorySize;
import com.taskmanagementsystem.utils.ParsingHelpers;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class ChangeSizeOfStoryCommand implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public ChangeSizeOfStoryCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }


    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        int id = ParsingHelpers.tryParseInteger(parameters.get(0), "Invalid story ID!");
        StorySize size = ParsingHelpers.tryParseEnum(parameters.get(1), StorySize.class,
                "Invalid story size!");
        return changeSizeOfStory(id, size);
    }

    private String changeSizeOfStory(int id, StorySize size) {
        Story story = taskManagementSystemRepository.findStoryById(id);
        story.changeSize(size);
        return String.format("Size of story with ID %d was changed to %s.", id, story.getSize());
    }
}
