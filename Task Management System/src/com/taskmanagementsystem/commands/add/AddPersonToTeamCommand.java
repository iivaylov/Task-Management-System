package com.taskmanagementsystem.commands.add;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.models.contracts.Team;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class AddPersonToTeamCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private static final String PERSON_ADDED_IN_TEAM = "Person %s was added to team %s successfully.";
    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public AddPersonToTeamCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }


    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.findTeamByName(parameters.get(0));
        Member member = taskManagementSystemRepository.findMemberByName(parameters.get(1));
        return addPersonToTeam(team, member);
    }

    private String addPersonToTeam(Team team, Member member) {
        team.addMember(member);
        return String.format(PERSON_ADDED_IN_TEAM, member.getName(), team.getName());
    }
}