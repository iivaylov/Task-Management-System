package com.taskmanagementsystem.tests.commands.creation;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.commands.creation.CreateStoryInBoardCommand;
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

public class CreateStoryInBoardCommandTests {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 7;
    public static final String SOTRY_CREATE = "Story with ID 1 was created in board BoardOne of team TeamOne.";

    private Command createStoryInBoardCommand;

    private TaskManagementSystemRepository taskManagementSystemRepository;

    @BeforeEach
    public void before() {
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        createStoryInBoardCommand = new CreateStoryInBoardCommand(taskManagementSystemRepository);
    }

    @Test
    @DisplayName("execute() should throw exception when parameters are less than expected")
    public void execute_ShouldThrowException_WhenInvalidParametersCount() {
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS - 1);

        //Act & Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> createStoryInBoardCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should create story in board")
    public void execute_ShouldCreateStoryInBoard_WhenValidParametersArePassed() {
        //Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        team.addBoard(board);
        Member member = taskManagementSystemRepository.createMember("Mathew");
        team.addMember(member);

        parameters.set(0, "TeamOne");
        parameters.set(1, "BoardOne");
        parameters.set(2, "StoryTitleOne");
        parameters.set(3, "Description");
        parameters.set(4, "High");
        parameters.set(5, "Medium");
        parameters.set(6, "Mathew");

        //Act
        String result = createStoryInBoardCommand.execute(parameters);

        //Assert
        Assertions.assertEquals(SOTRY_CREATE, result);
    }
}
