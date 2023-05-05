package com.taskmanagementsystem.commands.change;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.contracts.Board;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.models.contracts.Team;
import com.taskmanagementsystem.models.tasks.contracts.Task;
import com.taskmanagementsystem.utils.ParsingHelpers;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class ChangeAssignmentToPersonCommand implements Command {
    public static final String TASK_ASSIGNED = "Task with id %d assigned to member %s";
    public static final String INVALID_TASK_ID = "Invalid task ID!";
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;
    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public ChangeAssignmentToPersonCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.findTeamByName(parameters.get(0));
        Board board = taskManagementSystemRepository.findBoardInTeam(team, parameters.get(1));
        int taskIndex = ParsingHelpers.tryParseInteger(parameters.get(2), INVALID_TASK_ID);
        int id = taskManagementSystemRepository.findTaskInBoard(board, taskIndex).getId();
        Member member = taskManagementSystemRepository.findMemberInTeam(team, parameters.get(3));
        return changeAssignmentToPerson(id, member);
    }

    private String changeAssignmentToPerson(int id, Member member) {
        Task task = taskManagementSystemRepository.findTaskById(id);
        task.changeAssignee(member);
        return String.format(TASK_ASSIGNED, id, member.getName());
    }
}
