package com.taskmanagementsystem.tests.commands.add;

import com.taskmanagementsystem.commands.add.AddPersonToTeamCommand;
import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.TaskManagementSystemRepositoryImpl;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.exceptions.ElementNotFoundException;
import com.taskmanagementsystem.exceptions.InvalidUserInputException;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.models.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.taskmanagementsystem.tests.TestUtilities.getList;

/**
 * AddPersonToTeam arguments: {{Team}} {{Person}}
 */
public class AddPersonToTeamCommandTest {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    private Command addPersonToTeamCommand;
    private TaskManagementSystemRepository taskManagementSystemRepository;

    @BeforeEach
    public void before() {
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        addPersonToTeamCommand = new AddPersonToTeamCommand(taskManagementSystemRepository);
    }

    @Test
    @DisplayName("execute() should throw exception when invalid parameters count")
    public void execute_ShouldThrowException_WhenInvalidParametersCount() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS - 1);

        // Act & Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> addPersonToTeamCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when team does not exist")
    public void execute_ShouldThrowException_WhenTeamDoesNotExist() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Member member = taskManagementSystemRepository.createMember("Jonny");
        parameters.set(0, "Team");
        parameters.set(1, member.getName());

        // Act & Assert
        Assertions.assertThrows(ElementNotFoundException.class, () -> addPersonToTeamCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when person does not exist")
    public void execute_ShouldThrowException_When_MemberDoesNotExist() {
        // Arrange
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        parameters.set(0, team.getName());
        parameters.set(1, "Person");

        // Act & Assert
        Assertions.assertThrows(ElementNotFoundException.class, () -> addPersonToTeamCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should add person to team")
    public void execute_ShouldAddPersonToTeam_WhenParametersAreValid() {
        Team team = taskManagementSystemRepository.createTeam("TeamOne");
        Member member = taskManagementSystemRepository.createMember("Jonny");

        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        parameters.set(0, team.getName());
        parameters.set(1, member.getName());

        String result = addPersonToTeamCommand.execute(parameters);
        Assertions.assertEquals(String.format("Person %s was added to team %s successfully.",
                member.getName(), team.getName()), result);
    }
}
