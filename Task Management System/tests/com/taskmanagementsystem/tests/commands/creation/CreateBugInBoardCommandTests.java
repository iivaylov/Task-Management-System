package com.taskmanagementsystem.tests.commands.creation;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.commands.creation.CreateBugInBoardCommand;
import com.taskmanagementsystem.core.TaskManagementSystemRepositoryImpl;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.exceptions.InvalidUserInputException;
import com.taskmanagementsystem.models.contracts.Board;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.models.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.taskmanagementsystem.tests.TestUtilities.getList;

public class CreateBugInBoardCommandTests {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 8;
    private static final String BUG_CREATE = "Bug with ID 1 was created in board %s of team %s.";

    private Command createBugInBoardCommand;

    private TaskManagementSystemRepository taskManagementSystemRepository;

    @BeforeEach
    public void before() {
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        createBugInBoardCommand = new CreateBugInBoardCommand(taskManagementSystemRepository);
    }

    @Test
    @DisplayName("execute() should throw exception when invalid number of arguments are passed")
    public void execute_ShouldThrowException_WhenInvalidNumberOfArgumentsArePassed() {
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS + 1);

        Assertions.assertThrows(InvalidUserInputException.class, () -> createBugInBoardCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should create bug in board")
    public void execute_Should_CreateBug_When_ValidParametersArePassed() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        Member member = taskManagementSystemRepository.createMember("Mathew");
        team.addMember(member);
        parameters.set(0, "TeamOne");
        parameters.set(1, "BoardOne");
        parameters.set(2, "BugTitleOne");
        parameters.set(3, "DescriptionOne");
        parameters.set(4, "One;Two;Three");
        parameters.set(5, "High");
        parameters.set(6, "Minor");
        parameters.set(7, "Mathew");

        // Act
        String result = createBugInBoardCommand.execute(parameters);
        String expected = String.format(BUG_CREATE, board.getName(), team.getName());

        // Assert
        Assertions.assertEquals(expected, result);
    }
}
