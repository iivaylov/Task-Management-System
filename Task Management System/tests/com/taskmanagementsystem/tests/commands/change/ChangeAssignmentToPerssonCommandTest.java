package com.taskmanagementsystem.tests.commands.change;

import com.taskmanagementsystem.commands.change.ChangeAssignmentToPersonCommand;
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
 * ChangeAssignmentToPersonCommand arguments: {{Team}} {{Board}} {{Id}} {{Member}}
 */
public class ChangeAssignmentToPerssonCommandTest {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;

    private Command changeAssignmentToPersonCommand;
    private TaskManagementSystemRepository taskManagementSystemRepository;

    @BeforeEach
    public void before() {
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        changeAssignmentToPersonCommand = new ChangeAssignmentToPersonCommand(taskManagementSystemRepository);
    }

    @Test
    @DisplayName("execute() should throw exception when invalid parameters count")
    public void execute_ShouldThrowException_WhenInvalidParametersCount() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS + 1);

        // Act & Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> changeAssignmentToPersonCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when team does not exist")
    public void execute_ShouldThrowException_WhenTeamDoesNotExist() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        Member member = taskManagementSystemRepository.createMember("Jonny");
        team.addMember(member);
        Story story = taskManagementSystemRepository.createStory(team, board, "VeryLongTitle",
                "Description", Priority.HIGH, StorySize.MEDIUM, team.getMembers().get(0));
        board.addStory(story);

        parameters.set(0, "TeamTwo");
        parameters.set(1, "BoardOne");
        parameters.set(2, "1");
        parameters.set(3, "Jonny");

        // Act & Assert
        Assertions.assertThrows(ElementNotFoundException.class,
                () -> changeAssignmentToPersonCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when board does not exist")
    public void execute_ShouldThrowException_WhenBoardDoesNotExist() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        Member member = taskManagementSystemRepository.createMember("Jonny");
        team.addMember(member);
        Story story = taskManagementSystemRepository.createStory(team, board, "VeryLongTitle",
                "Description", Priority.HIGH, StorySize.MEDIUM, team.getMembers().get(0));
        board.addStory(story);

        parameters.set(0, "TeamOne");
        parameters.set(1, "BoardTwo");
        parameters.set(2, "1");
        parameters.set(3, "Jonny");

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> changeAssignmentToPersonCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when member does not exist")
    public void execute_ShouldThrowException_WhenMemberDoesNotExist() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        Member member = taskManagementSystemRepository.createMember("Jonny");
        team.addMember(member);
        Story story = taskManagementSystemRepository.createStory(team, board, "VeryLongTitle",
                "Description", Priority.HIGH, StorySize.MEDIUM, team.getMembers().get(0));
        board.addStory(story);

        parameters.set(0, "TeamOne");
        parameters.set(1, "BoardOne");
        parameters.set(2, "1");
        parameters.set(3, "Matthew");

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> changeAssignmentToPersonCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should return correct message when story is assigned to member")
    public void execute_ShouldReturnCorrectMessage_WhenValidParameters() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        Member member = taskManagementSystemRepository.createMember("Jonny");
        Member member2 = taskManagementSystemRepository.createMember("Matthew");
        team.addMember(member);
        team.addMember(member2);
        Story story = taskManagementSystemRepository.createStory(team, board, "VeryLongTitle",
                "Description", Priority.HIGH, StorySize.MEDIUM, team.getMembers().get(0));
        board.addStory(story);

        parameters.set(0, "TeamOne");
        parameters.set(1, "BoardOne");
        parameters.set(2, String.valueOf(story.getId()));
        parameters.set(3, "Matthew");

        // Act
        String actualMessage = changeAssignmentToPersonCommand.execute(parameters);

        // Assert
        Assertions.assertEquals(String.format("Task with id %d assigned to member %s",
                story.getId(), member2.getName()), actualMessage);
    }

    @Test
    @DisplayName("execute() should throw exception when assignee is the same as the current assignee")
    public void execute_ShouldThrowException_WhenAssigneeIsTheSame() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        Member member = taskManagementSystemRepository.createMember("Jonny");
        Member member2 = taskManagementSystemRepository.createMember("Matthew");
        team.addMember(member);
        team.addMember(member2);
        Story story = taskManagementSystemRepository.createStory(team, board, "VeryLongTitle",
                "Description", Priority.HIGH, StorySize.MEDIUM, team.getMembers().get(0));
        board.addStory(story);

        parameters.set(0, "TeamOne");
        parameters.set(1, "BoardOne");
        parameters.set(2, String.valueOf(story.getId()));
        parameters.set(3, "Jonny");

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> changeAssignmentToPersonCommand.execute(parameters));
    }

}
