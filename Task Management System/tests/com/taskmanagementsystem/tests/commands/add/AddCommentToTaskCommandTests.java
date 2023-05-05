package com.taskmanagementsystem.tests.commands.add;


import com.taskmanagementsystem.commands.add.AddCommentToTaskCommand;
import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.TaskManagementSystemRepositoryImpl;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.exceptions.ElementNotFoundException;
import com.taskmanagementsystem.exceptions.InvalidUserInputException;
import com.taskmanagementsystem.models.contracts.Board;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.models.contracts.Team;
import com.taskmanagementsystem.models.tasks.contracts.Bug;
import com.taskmanagementsystem.models.tasks.enums.Priority;
import com.taskmanagementsystem.models.tasks.enums.Severity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.taskmanagementsystem.tests.TestUtilities.getList;


/**
 * AddCommentToTaskCommand arguments: {{Id}} {{Message}} {{Author}}
 */
public class AddCommentToTaskCommandTests {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;

    private Command addCommentToTaskCommand;
    private TaskManagementSystemRepository taskManagementSystemRepository;


    @BeforeEach
    public void before() {
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        addCommentToTaskCommand = new AddCommentToTaskCommand(taskManagementSystemRepository);
    }

    @Test
    @DisplayName("execute() should throw exception when invalid parameters count")
    public void execute_ShouldThrowException_WhenInvalidParametersCount() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS - 1);

        // Act & Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> addCommentToTaskCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when ID is not found")
    public void execute_ShouldThrowException_WhenIdDoesNotExist() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        parameters.set(0, "1");

        // Act & Assert
        Assertions.assertThrows(ElementNotFoundException.class, () -> addCommentToTaskCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when Author is not found")
    public void execute_ShouldThrowException_WhenAuthorDoesNotExist() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        parameters.set(0, "1");
        parameters.set(2, "Jonny");

        // Act & Assert
        Assertions.assertThrows(ElementNotFoundException.class, () -> addCommentToTaskCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should add comment to task")
    public void execute_ShouldReturnCorrectMessage_WhenValidParametersPassed() {
        // Arrange
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Member member = taskManagementSystemRepository.createMember("Jonny");
        team.addMember(member);
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        Bug bug = taskManagementSystemRepository.createBug(team, board, "VeryLongTitle", "Description",
                List.of("One;Two;Three"), Priority.HIGH, Severity.CRITICAL, member);

        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        parameters.set(0, "1");
        parameters.set(1, "message");
        parameters.set(2, member.getName());

        // Act
        String actualResult = addCommentToTaskCommand.execute(parameters);
        String expectedResult = String.format("Comment added to task with ID %d", bug.getId());

        // Assert
        Assertions.assertEquals(expectedResult, actualResult);
    }
}

