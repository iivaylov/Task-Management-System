package com.taskmanagementsystem.commands.showactivity;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.contracts.Team;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class ShowTeamActivityCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public ShowTeamActivityCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }


    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.findTeamByName(parameters.get(0));
        return showTeamActivity(team);
    }

    private String showTeamActivity(Team team) {
        return team.showTeamActivityHistory();
    }
}
