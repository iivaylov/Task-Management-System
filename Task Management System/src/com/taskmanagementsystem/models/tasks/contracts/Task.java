package com.taskmanagementsystem.models.tasks.contracts;

import com.taskmanagementsystem.models.contracts.Comment;
import com.taskmanagementsystem.models.contracts.History;
import com.taskmanagementsystem.models.contracts.Member;

import java.util.List;

public interface Task extends Identifiable {

    String getTitle();

    String getDescription();

    List<Comment> getComments();

    List<History> getChangesHistory();

    void addComment(Comment comment);

    void addHistory(History history);

    void changeAssignee(Member member);

}
