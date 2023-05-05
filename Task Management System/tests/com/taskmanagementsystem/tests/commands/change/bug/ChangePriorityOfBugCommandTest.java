package com.taskmanagementsystem.tests.commands.change.bug;

import com.taskmanagementsystem.commands.change.bug.ChangePriorityOfBugCommand;
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
 * ChangePriorityOfBugCommand arguments: {{Id}} {{Priority}}
 */
public class ChangePriorityOfBugCommandTest {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    private Command changePriorityOfBugCommand;

    private TaskManagementSystemRepository taskManagementSystemRepository;


    @BeforeEach
    public void before() {
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        changePriorityOfBugCommand = new ChangePriorityOfBugCommand(taskManagementSystemRepository);
    }

    @Test
    @DisplayName("execute() should throw exception when invalid parameters count")
    public void execute_ShouldThrowException_WhenInvalidParametersCount() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS - 1);

        // Act & Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changePriorityOfBugCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when bug does not exist")
    public void execute_ShouldThrowException_WhenBugDoesNotExist() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        Member member = taskManagementSystemRepository.createMember("Jonny");
        team.addMember(member);
        Bug bug = taskManagementSystemRepository.createBug(team, board, "VeryLongTitle", "Description",
                List.of("One;Two;Three"), Priority.HIGH, Severity.CRITICAL, member);


        parameters.set(0, String.valueOf(bug.getId() + 1));
        parameters.set(1, "High");

        // Act & Assert
        Assertions.assertThrows(ElementNotFoundException.class, () -> changePriorityOfBugCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when priority is invalid")
    public void execute_ShouldThrowException_WhenPriorityIsInvalid() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        Member member = taskManagementSystemRepository.createMember("Jonny");
        team.addMember(member);
        Bug bug = taskManagementSystemRepository.createBug(team, board, "VeryLongTitle", "Description",
                List.of("One;Two;Three"), Priority.HIGH, Severity.CRITICAL, member);
        board.addBug(bug);
        parameters.set(0, String.valueOf(bug.getId()));
        parameters.set(1, "InvalidPriority");

        // Act & Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changePriorityOfBugCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should not change priority when priority is the same")
    public void execute_ShouldNotChangePriority_WhenPriorityIsSame() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        Member member = taskManagementSystemRepository.createMember("Jonny");
        team.addMember(member);
        Bug bug = taskManagementSystemRepository.createBug(team, board, "VeryLongTitle", "Description",
                List.of("One;Two;Three"), Priority.HIGH, Severity.CRITICAL, member);
        board.addBug(bug);

        parameters.set(0, "1");
        parameters.set(1, "High");

        // Act

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> changePriorityOfBugCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should change priority when priority is different")
    public void execute_ShouldChangePriority_WhenPriorityIsDifferent() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        Member member = taskManagementSystemRepository.createMember("Jonny");
        team.addMember(member);
        Bug bug = taskManagementSystemRepository.createBug(team, board, "VeryLongTitle", "Description",
                List.of("One;Two;Three"), Priority.HIGH, Severity.CRITICAL, member);
        board.addBug(bug);
        parameters.set(0, "1");
        parameters.set(1, "Low");

        // Act
        changePriorityOfBugCommand.execute(parameters);

        // Assert
        Assertions.assertEquals(Priority.LOW, bug.getPriority());
    }
}
