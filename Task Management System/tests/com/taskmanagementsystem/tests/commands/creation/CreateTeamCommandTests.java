package com.taskmanagementsystem.tests.commands.creation;


import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.commands.creation.CreateTeamCommand;
import com.taskmanagementsystem.core.TaskManagementSystemRepositoryImpl;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.exceptions.InvalidUserInputException;
import com.taskmanagementsystem.models.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.taskmanagementsystem.tests.TestUtilities.getList;

/**
 * CreateTeamCommand arguments: {{TeamName}}
 */
public class CreateTeamCommandTests {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private Command createTeamCommand;

    private TaskManagementSystemRepository taskManagementSystemRepository;

    @BeforeEach
    public void before() {
        taskManagementSystemRepository = new TaskManagementSystemRepositoryImpl();
        createTeamCommand = new CreateTeamCommand(taskManagementSystemRepository);
    }

    @Test
    @DisplayName("execute() should throw exception when invalid number of arguments are passed")
    public void execute_ShouldThrowException_WhenInvalidNumberOfParametersArePassed() {
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS + 1);

        Assertions.assertThrows(InvalidUserInputException.class, () -> createTeamCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should throw exception when team with same name already exists")
    public void execute_ShouldThrowException_WhenTeamAlreadyExists() {
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.createTeam("TeamOne");

        parameters.set(0, team.getName());

        Assertions.assertThrows(IllegalArgumentException.class, () -> createTeamCommand.execute(parameters));
    }

    @Test
    @DisplayName("execute() should create team")
    public void execute_ShouldCreateTeam_WhenValidParametersArePassed() {
        List<String> parameters = getList(EXPECTED_NUMBER_OF_ARGUMENTS);
        parameters.set(0, "TeamOne");

        String result = createTeamCommand.execute(parameters);

        Assertions.assertEquals("Team with name TeamOne was created successfully.", result);
    }

}
