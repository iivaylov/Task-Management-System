package com.taskmanagementsystem.tests.commands.show;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.commands.show.ShowAllTeamBoardsCommand;
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
 * ShowAllTeamBoardsCommand arguments: {{TeamName}}
 */
public class ShowAllTeamBoardsCommandTests {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private Command showAllTeamBoardsCommand;

    private TaskManagementSystemRepository taskManagementSystemRepository;

    @BeforeEach
    public void before() {
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        showAllTeamBoardsCommand = new ShowAllTeamBoardsCommand(taskManagementSystemRepository);
    }

    @Test
    @DisplayName("execute() should throw exception when invalid number of arguments are passed")
    public void execute_ShouldThrowException_WhenInvalidNumberOfArgumentsArePassed() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS + 1);

        // Act & Assert
        Assertions.assertThrows(InvalidUserInputException.class,
                () -> showAllTeamBoardsCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when team does not exist")
    public void execute_ShouldThrowException_WhenTeamDoesNotExist() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        parameters.set(0, "TeamName");

        // Act & Assert
        Assertions.assertThrows(ElementNotFoundException.class,
                () -> showAllTeamBoardsCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should return correct message when no boards are in the team")
    public void execute_ShouldReturnMessage_WhenTeamHasNoBoards() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamName");
        parameters.set(0, team.getName());

        // Act
        String actualResult = showAllTeamBoardsCommand.execute(parameters);

        String expectedResult = String.format("---BOARDS OF TEAM { %s }---", team.getName()) +
                System.lineSeparator() +
                "There are no boards in the team." + System.lineSeparator() +
                "###################################";

        // Assert
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("execute() should return correct message when team has boards")
    public void execute_ShouldReturnCorrectMessage_WhenTeamHasBoards() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamName");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardName");
        team.addBoard(board);
        parameters.set(0, team.getName());

        // Act
        String actualResult = showAllTeamBoardsCommand.execute(parameters);

        String expectedResult = String.format("---BOARDS OF TEAM { %s }---", team.getName()) +
                System.lineSeparator() +
                "1. BoardName";

        // Assert
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
