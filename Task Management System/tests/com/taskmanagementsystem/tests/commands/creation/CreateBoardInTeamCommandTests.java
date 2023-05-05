package com.taskmanagementsystem.tests.commands.creation;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.commands.creation.CreateBoardInTeamCommand;
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
 * CreateBoardInTeamCommand arguments: {{Team}} {{BoardName}}
 */
public class CreateBoardInTeamCommandTests {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private Command createBoardInTeamCommand;

    private TaskManagementSystemRepository taskManagementSystemRepository;


    @BeforeEach
    public void before() {
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        createBoardInTeamCommand = new CreateBoardInTeamCommand(taskManagementSystemRepository);
    }

    @Test
    @DisplayName("execute() should throw exception when invalid number of arguments are passed")
    public void execute_ShouldThrowException_WhenInvalidNumberOfArgumentsArePassed() {
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS + 1);

        Assertions.assertThrows(InvalidUserInputException.class, () -> createBoardInTeamCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when team does not exist")
    public void execute_ShouldThrowException_whenTeamDoesNotExist() {
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        parameters.set(0, "TeamTwo");
        parameters.set(1, "BoardOne");

        Assertions.assertThrows(ElementNotFoundException.class, () -> createBoardInTeamCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when board already exists in team")
    public void execute_ShouldThrowException_WhenBoardAlreadyExistsInTeam() {
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);

        parameters.set(0, "TeamOne");
        parameters.set(1, "BoardOne");

        Assertions.assertThrows(InvalidUserInputException.class, () -> createBoardInTeamCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should return correct message when board is created in team")
    public void execute_ShouldReturnCorrectMessage_WhenValidParametersArePassed() {
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        parameters.set(0, "TeamOne");
        parameters.set(1, "BoardOne");

        String actualResult = createBoardInTeamCommand.execute(parameters);
        String expectedResult =
                String.format("Board with name BoardOne was created successfully in team %s.", team.getName());

        Assertions.assertEquals(expectedResult, actualResult);
    }
}
