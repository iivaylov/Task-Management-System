package com.taskmanagementsystem.tests.commands.show;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.commands.show.ShowAllTeamMembersCommand;
import com.taskmanagementsystem.core.TaskManagementSystemRepositoryImpl;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.exceptions.ElementNotFoundException;
import com.taskmanagementsystem.exceptions.InvalidUserInputException;
import com.taskmanagementsystem.models.MemberImpl;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.models.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.taskmanagementsystem.tests.TestUtilities.getList;

/**
 * ShowAllTeamMembersCommand arguments: {{TeamName}}
 */

public class ShowAllTeamMembersCommandTests {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private Command showAllTeamMembersCommand;

    private TaskManagementSystemRepository taskManagementSystemRepository;

    @BeforeEach
    public void before() {
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        showAllTeamMembersCommand = new ShowAllTeamMembersCommand(taskManagementSystemRepository);
    }

    @Test
    @DisplayName("execute() should throw exception when invalid number of arguments are passed")
    public void execute_ShouldThrowException_WhenInvalidNumberOfArgumentsArePassed() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS + 1);

        // Act & Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> showAllTeamMembersCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when team does not exist")
    public void execute_ShouldThrowException_WhenTeamDoesNotExist() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        parameters.set(0, "TeamName");

        // Act & Assert
        Assertions.assertThrows(ElementNotFoundException.class, () -> showAllTeamMembersCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should return correct message when no members are in the team")
    public void execute_ShouldReturnCorrectMessage_WhenNoMembersAreInTheTeam() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamName");
        parameters.set(0, team.getName());

        // Act
        String actualResult = showAllTeamMembersCommand.execute(parameters);

        // Assert
        String expectedResult = String.format("---MEMBERS OF TEAM { %s }---", team.getName()) +
                System.lineSeparator() +
                "There are no members in the team." +
                System.lineSeparator() +
                "###################################";

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("execute() should return correct message when members are in the team")
    public void execute_ShouldReturnCorrectMessage_WhenMembersAreInTheTeam() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamName");
        Member member = new MemberImpl("MemberName");
        team.addMember(member);
        Member member2 = new MemberImpl("MemberName2");
        team.addMember(member2);
        parameters.set(0, team.getName());

        // Act
        String actualResult = showAllTeamMembersCommand.execute(parameters);

        // Assert
        String expectedResult = String.format("---MEMBERS OF TEAM { %s }---", team.getName()) +
                System.lineSeparator() +
                "1. MemberName" +
                System.lineSeparator() +
                "2. MemberName2";

        Assertions.assertEquals(expectedResult, actualResult);
    }
}
