package com.taskmanagementsystem.commands.creation;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.contracts.Team;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class CreateTeamCommand implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private static final String TEAM_CREATE_MESSAGE = "Team with name %s was created successfully.";
    public static final String TEAM_EXISTS_ERROR = "Team with that name already exists!";

    private final TaskManagementSystemRepository taskManagementSystemRepository;

    private String name;


    public CreateTeamCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);
        return createTeam(name);
    }

    private void parseParameters(List<String> parameters) {
        name = parameters.get(0);
    }

    private String createTeam(String teamName) {
        if (taskManagementSystemRepository.teamExists(teamName)) {
            throw new IllegalArgumentException(TEAM_EXISTS_ERROR);
        }
        Team team = taskManagementSystemRepository.createTeam(teamName);
        return String.format(TEAM_CREATE_MESSAGE, team.getName());
    }

}
