package com.taskmanagementsystem.tests.commands.change.bug;

import com.taskmanagementsystem.commands.change.bug.ChangeSeverityOfBugCommand;
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
 * ChangeSeverityOfBugCommand arguments: {{Id}} {{Severity}}
 */
public class ChangeSeverityOfBugCommandTest {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private Command changeSeverityOfBugCommand;
    private TaskManagementSystemRepository taskManagementSystemRepository;

    @BeforeEach
    public void before() {
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        changeSeverityOfBugCommand = new ChangeSeverityOfBugCommand(taskManagementSystemRepository);
    }

    @Test
    @DisplayName("execute() should throw exception when invalid parameters count")
    public void execute_ShouldThrowException_WhenInvalidParameterCount() {
        //Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS - 1);

        //Act & Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeSeverityOfBugCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when severity is invalid")
    public void execute_ShouldThrowException_WhenSeverityIsInvalid() {
        //Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        Member member = taskManagementSystemRepository.createMember("Jonny");
        team.addMember(member);
        Bug bug = taskManagementSystemRepository.createBug(team, board, "VeryLongTitle", "Description",
                List.of("One;Two;Three"), Priority.HIGH, Severity.CRITICAL, member);

        parameters.set(0, String.valueOf(bug.getId()));
        parameters.set(1, "InvalidSeverity");

        //Act & Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> changeSeverityOfBugCommand.execute(parameters));
    }


    @Test
    @DisplayName("execute() should throw exception when bug is not found")
    public void execute_ShouldThrowException_WhenBugDoesNotExist() {
        //Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        Member member = taskManagementSystemRepository.createMember("Jonny");
        team.addMember(member);
        Bug bug = taskManagementSystemRepository.createBug(team, board, "VeryLongTitle", "Description",
                List.of("One;Two;Three"), Priority.HIGH, Severity.CRITICAL, member);

        parameters.set(0, String.valueOf(bug.getId() + 1));
        parameters.set(1, "Critical");

        //Act & Assert
        Assertions.assertThrows(ElementNotFoundException.class, () -> changeSeverityOfBugCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should change severity when severity is different")
    public void execute_ShouldChangeSeverity_WhenSeverityIsDifferent() {
        //Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Board board = taskManagementSystemRepository.createBoard(team, "BoardOne");
        Member member = taskManagementSystemRepository.createMember("Jonny");
        team.addMember(member);
        Bug bug = taskManagementSystemRepository.createBug(team, board, "VeryLongTitle", "Description",
                List.of("One;Two;Three"), Priority.HIGH, Severity.CRITICAL, member);

        parameters.set(0, String.valueOf(bug.getId()));
        parameters.set(1, "Major");

        //Act
        changeSeverityOfBugCommand.execute(parameters);

        //Assert
        Assertions.assertEquals(Severity.MAJOR, bug.getSeverity());
    }

}

