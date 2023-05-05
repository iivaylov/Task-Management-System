package com.taskmanagementsystem.commands.creation;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.contracts.Board;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.models.contracts.Team;
import com.taskmanagementsystem.models.tasks.contracts.Story;
import com.taskmanagementsystem.models.tasks.enums.Priority;
import com.taskmanagementsystem.models.tasks.enums.StorySize;
import com.taskmanagementsystem.utils.ParsingHelpers;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class CreateStoryInBoardCommand implements Command {
    public static final String INVALID_PRIORITY = "Invalid priority!";
    public static final String INVALID_STORY_SIZE = "Invalid story size!";
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 7;
    private static final String STORY_CREATE_MESSAGE = "Story with ID %d was created in board %s of team %s.";
    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public CreateStoryInBoardCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.findTeamByName(parameters.get(0));
        Board board = taskManagementSystemRepository.findBoardInTeam(team, parameters.get(1));
        String title = parameters.get(2);
        String description = parameters.get(3);
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(4), Priority.class, INVALID_PRIORITY);
        StorySize storySize = ParsingHelpers.tryParseEnum(parameters.get(5), StorySize.class, INVALID_STORY_SIZE);
        Member assignee = taskManagementSystemRepository.findMemberByName(parameters.get(6));
        return createStoryInBoard(team, board, title, description, priority, storySize, assignee);
    }

    private String createStoryInBoard(Team team, Board board, String title, String description,
                                      Priority priority, StorySize size, Member assignee) {
        Story story = taskManagementSystemRepository.createStory(team, board, title, description,
                priority, size, assignee);
        taskManagementSystemRepository.findTeamByName(team.getName()).findBoardByName(board.getName()).addStory(story);
        assignee.addTask(story);
        return String.format(STORY_CREATE_MESSAGE, story.getId(), board.getName(), team.getName());
    }
}

