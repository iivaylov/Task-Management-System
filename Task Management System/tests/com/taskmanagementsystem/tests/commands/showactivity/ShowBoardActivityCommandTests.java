package com.taskmanagementsystem.tests.commands.showactivity;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.commands.showactivity.ShowBoardActivityCommand;
import com.taskmanagementsystem.core.TaskManagementSystemRepositoryImpl;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.exceptions.ElementNotFoundException;
import com.taskmanagementsystem.exceptions.InvalidUserInputException;
import com.taskmanagementsystem.models.contracts.Board;
import com.taskmanagementsystem.models.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.taskmanagementsystem.tests.TestUtilities.getList;

/**
 * ShowBoardActivityCommand arguments: {{TeamName}} {{BoardName}}
 */
public class ShowBoardActivityCommandTests {

    public static final int EXPECTED_ARGUMENTS_COUNT = 2;
    private Command showBoardActivityCommand;

    private TaskManagementSystemRepository taskManagementSystemRepository;

    @BeforeEach
    public void before() {
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        showBoardActivityCommand = new ShowBoardActivityCommand(taskManagementSystemRepository);
    }

    @Test
    @DisplayName("execute() should throw exception when arguments count is different than expected")
    public void execute_Should_ThrowException_When_ArgumentCountDifferentThanExpected() {
        List<String> parameters = getList(EXPECTED_ARGUMENTS_COUNT + 1);

        // Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> showBoardActivityCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when team name does not exist")
    public void execute_Should_ThrowException_When_TeamDoesNotExist() {
        List<String> parameters = getList(EXPECTED_ARGUMENTS_COUNT);
        parameters.set(0, "Team");
        parameters.set(1, "BoardOne");

        // Act, Assert
        Assertions.assertThrows(ElementNotFoundException.class, () -> showBoardActivityCommand.execute(parameters));
    }


    @Test
    @DisplayName("execute() should throw exception when board name does not exist")
    public void execute_Should_ThrowException_When_BoardDoesNotExist() {
        List<String> parameters = getList(EXPECTED_ARGUMENTS_COUNT);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        parameters.set(0, team.getName());
        parameters.set(1, "BoardOne");

        // Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> showBoardActivityCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should return correct message when board activity is shown")
    public void execute_Should_ReturnCorrectMessage_When_BoardExists() {
        // Arrange
        List<String> parameters = getList(EXPECTED_ARGUMENTS_COUNT);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        parameters.set(0, team.getName());
        parameters.set(1, "BoardOne");

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> showBoardActivityCommand.execute(parameters));
    }
}
