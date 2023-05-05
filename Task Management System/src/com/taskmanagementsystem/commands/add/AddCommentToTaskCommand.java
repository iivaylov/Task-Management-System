package com.taskmanagementsystem.commands.add;

import com.taskmanagementsystem.commands.contracts.Command;
import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.models.CommentImpl;
import com.taskmanagementsystem.models.contracts.Comment;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.models.tasks.contracts.Task;
import com.taskmanagementsystem.utils.ParsingHelpers;
import com.taskmanagementsystem.utils.ValidationHelpers;

import java.util.List;

public class AddCommentToTaskCommand implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;
    private static final String COMMENT_ADD_MESSAGE = "Comment added to task with ID %d";
    private static final String WRONG_ID = "Invalid task ID.";

    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public AddCommentToTaskCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }


    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        int taskId = ParsingHelpers.tryParseInteger(parameters.get(0), WRONG_ID);
        int id = taskManagementSystemRepository.findTaskById(taskId).getId();
        String message = parameters.get(1);
        String author = parameters.get(2);
        Member member = taskManagementSystemRepository.findMemberByName(author);
        return addCommentToTask(id, message, member);
    }

    private String addCommentToTask(int id, String message, Member member) {
        Task task = taskManagementSystemRepository.findTaskById(id);
        Comment comment = new CommentImpl(message, member.getName());
        task.addComment(comment);
        return String.format(COMMENT_ADD_MESSAGE, id);
    }
}
