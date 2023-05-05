package com.taskmanagementsystem.tests.commands.change.story;

import com.taskmanagementsystem.commands.change.story.ChangeSizeOfStoryCommand;
import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.TaskManagementSystemRepositoryImpl;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.exceptions.ElementNotFoundException;
import com.taskmanagementsystem.exceptions.InvalidUserInputException;
import com.taskmanagementsystem.models.contracts.Board;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.models.contracts.Team;
import com.taskmanagementsystem.models.tasks.contracts.Story;
import com.taskmanagementsystem.models.tasks.enums.Priority;
import com.taskmanagementsystem.models.tasks.enums.StorySize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.taskmanagementsystem.tests.TestUtilities.getList;

/**
 * ChangeSizeOfStoryCommand arguments: {{Id}} {{Size}}
 */
public class ChangeSizeOfStoryCommandTest {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private Command changeSizeOfStoryCommand;

    private TaskManagementSystemRepository taskManagementSystemRepository;

    @BeforeEach
    public void before() {
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        changeSizeOfStoryCommand = new ChangeSizeOfStoryCommand(taskManagementSystemRepository);
    }

    @Test
    @DisplayName("execute() should throw exception when invalid parameters count")
    public void execute_ShouldThrowException_WhenInvalidParametersCount() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS - 1);

        // Act & Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> changeSizeOfStoryCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when story does not exist")
    public void execute_ShouldThrowException_WhenStoryDoesNotExist() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        Member member = taskManagementSystemRepository.createMember("Jonny");
        Story story = taskManagementSystemRepository.createStory(team, board, "VeryLongTitle",
                "Description", Priority.HIGH, StorySize.MEDIUM, member);
        team.addMember(member);
        board.addStory(story);

        parameters.set(0, String.valueOf(story.getId() + 1));
        parameters.set(1, "Large");
        // Act & Assert
        Assertions.assertThrows(ElementNotFoundException.class,
                () -> changeSizeOfStoryCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when story size is invalid")
    public void execute_ShouldThrowException_WhenStorySizeIsInvalid() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        Member member = taskManagementSystemRepository.createMember("Jonny");
        Story story = taskManagementSystemRepository.createStory(team, board, "VeryLongTitle",
                "Description", Priority.HIGH, StorySize.MEDIUM, member);
        team.addMember(member);
        parameters.set(0, String.valueOf(story.getId()));
        parameters.set(1, "InvalidStorySize");

        // Act & Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> changeSizeOfStoryCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should change story size when valid parameters are passed")
    public void execute_ShouldChangeStorySize_WhenValidParametersArePassed() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        Member member = taskManagementSystemRepository.createMember("Jonny");
        Story story = taskManagementSystemRepository.createStory(team, board, "VeryLongTitle",
                "Description", Priority.HIGH, StorySize.MEDIUM, member);
        team.addMember(member);
        parameters.set(0, String.valueOf(story.getId()));
        parameters.set(1, "Large");

        // Act
        changeSizeOfStoryCommand.execute(parameters);

        // Assert
        Assertions.assertEquals(StorySize.LARGE, story.getSize());
    }
}
