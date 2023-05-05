package com.taskmanagementsystem.tests.commands.creation;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.commands.creation.CreateFeedbackInBoardCommand;
import com.taskmanagementsystem.core.TaskManagementSystemRepositoryImpl;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.exceptions.InvalidUserInputException;
import com.taskmanagementsystem.models.contracts.Board;
import com.taskmanagementsystem.models.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.taskmanagementsystem.tests.TestUtilities.getList;

public class CreateFeedbackInBoardCommandTests {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 5;
    public static final String FEEDBACK_CREATE = "Feedback with ID %d was created in board %s of team %s.";
    private Command createFeedbackInBoardCommand;

    private TaskManagementSystemRepository taskManagementSystemRepository;

    @BeforeEach
    public void before() {
        this.taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        this.createFeedbackInBoardCommand = new CreateFeedbackInBoardCommand(taskManagementSystemRepository);
    }

    @Test
    @DisplayName("execute() should throw exception when parameters are less than expected")
    public void execute_ShouldThrowException_WhenInvalidParametersCount() {
        //Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS - 1);

        //Act & Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createFeedbackInBoardCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should create feedback in board")
    public void execute_ShouldCreateFeedbackInBoard_WhenValidParametersArePassed() {
        //Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);

        parameters.set(0, "TeamOne");
        parameters.set(1, "BoardOne");
        parameters.set(2, "FeedbackTitleOne");
        parameters.set(3, "Description");
        parameters.set(4, "5");

        //Act
        String result = createFeedbackInBoardCommand.execute(parameters);
        String expected = String.format(FEEDBACK_CREATE, 1, board.getName(), team.getName());

        //Assert
        Assertions.assertEquals(expected, result);
    }
}
