package com.taskmanagementsystem.tests.commands.change.story;

import com.taskmanagementsystem.commands.change.story.ChangeStatusOfStoryCommand;
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
 * ChangeStatusOfStoryCommand arguments: {{Id}}
 */
public class ChangeStatusOfStoryCommandTest {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private Command changeStatusOfStoryCommand;

    private TaskManagementSystemRepository taskManagementSystemRepository;


    @BeforeEach
    public void before() {
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        changeStatusOfStoryCommand = new ChangeStatusOfStoryCommand(taskManagementSystemRepository);
    }

    @Test
    @DisplayName("execute() should throw exception when invalid parameters count")
    public void execute_ShouldThrowException_WhenInvalidParameterCount() {
        //Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS + 1);

        //Act & Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeStatusOfStoryCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when story is not found")
    public void execute_ShouldThrowException_WhenStoryDoesNotExist() {
        //Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        Member member = taskManagementSystemRepository.createMember("Jonny");
        team.addMember(member);
        Story story = taskManagementSystemRepository.createStory(team,
                board, "TitleStoryLong", "DescriptionStory", Priority.HIGH, StorySize.MEDIUM, member);
        board.addStory(story);

        parameters.set(0, String.valueOf(story.getId() + 1));

        //Act & Assert
        Assertions.assertThrows(ElementNotFoundException.class, () -> changeStatusOfStoryCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should change status of story")
    public void execute_ShouldChangeStatusOfStory_WhenValidParameters() {
        //Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        Member member = taskManagementSystemRepository.createMember("Jonny");
        team.addMember(member);
        Story story = taskManagementSystemRepository.createStory(team,
                board, "TitleStoryLong", "DescriptionStory", Priority.HIGH, StorySize.MEDIUM, member);
        board.addStory(story);

        parameters.set(0, String.valueOf(story.getId()));

        //Act
        String result = changeStatusOfStoryCommand.execute(parameters);

        //Assert
        Assertions.assertEquals(String.format("Status of story with ID %d was changed to %s.",
                story.getId(), story.getStatus()), result);
    }
}
