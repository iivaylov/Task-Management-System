package com.taskmanagementsystem.commands.creation;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.contracts.Board;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.models.contracts.Team;
import com.taskmanagementsystem.models.tasks.contracts.Bug;
import com.taskmanagementsystem.models.tasks.enums.Priority;
import com.taskmanagementsystem.models.tasks.enums.Severity;
import com.taskmanagementsystem.utils.ParsingHelpers;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.Arrays;
import java.util.List;

public class CreateBugInBoardCommand implements Command {
    public static final String INVALID_PRIORITY = "Invalid priority!";
    public static final String INVALID_SEVERITY = "Invalid severity!";
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 8;
    private static final String BUG_CREATE_MESSAGE = "Bug with ID %d was created in board %s of team %s.";
    private final TaskManagementSystemRepository taskManagementSystemRepository;


    public CreateBugInBoardCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Team team = taskManagementSystemRepository.findTeamByName(parameters.get(0));
        Board board = taskManagementSystemRepository.findBoardInTeam(team, parameters.get(1));
        String title = parameters.get(2);
        String description = parameters.get(3);
        String step = parameters.get(4);
        List<String> steps = Arrays.asList(step.split(";"));
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(5), Priority.class, INVALID_PRIORITY);
        Severity severity = ParsingHelpers.tryParseEnum(parameters.get(6), Severity.class, INVALID_SEVERITY);
        Member assignee = taskManagementSystemRepository.findMemberInTeam(team, parameters.get(7));
        return createBug(team, board, title, description, steps, priority, severity, assignee);
    }

    private String createBug(Team team, Board board, String title, String description, List<String> steps,
                             Priority priority, Severity severity, Member assignee) {
        Bug bug = taskManagementSystemRepository.createBug(team, board, title, description, steps,
                priority, severity, assignee);
        taskManagementSystemRepository.findBoardByName(board.getName()).addBug(bug);
        assignee.addTask(bug);
        return String.format(BUG_CREATE_MESSAGE, bug.getId(), board.getName(), team.getName());
    }
}
