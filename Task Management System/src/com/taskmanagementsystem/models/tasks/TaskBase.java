package com.taskmanagementsystem.models.tasks;

import com.taskmanagementsystem.models.contracts.Comment;
import com.taskmanagementsystem.models.contracts.History;
import com.taskmanagementsystem.models.tasks.contracts.Task;

import java.util.ArrayList;
import java.util.List;

import static com.taskmanagementsystem.utils.ValidationHelpers.validateStringLength;


public abstract class TaskBase implements Task {

    public static final int TITLE_MIN_LENGTH = 10;
    public static final int TITLE_MAX_LENGTH = 50;
    public static final String TITLE_ERROR_MESSAGE = String.format
            ("Title length should be between %d and %d symbols!", TITLE_MIN_LENGTH, TITLE_MAX_LENGTH);
    public static final int DESCRIPTION_MIN_LENGTH = 10;
    public static final int DESCRIPTION_MAX_LENGTH = 500;
    public static final String DESCRIPTION_ERROR_MESSAGE = String.format
            ("Description length should be between %d and %d symbols!",
                    DESCRIPTION_MIN_LENGTH, DESCRIPTION_MAX_LENGTH);
    private int id;
    private String title;
    private String description;
    private final List<Comment> comments;
    private final List<History> changesHistory;


    public TaskBase(int id, String title, String description) {
        setId(id);
        setTitle(title);
        setDescription(description);
        comments = new ArrayList<>();
        changesHistory = new ArrayList<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public List<History> getChangesHistory() {
        return new ArrayList<>(changesHistory);
    }

    @Override
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    @Override
    public void addHistory(History history) {
        changesHistory.add(history);
    }


    @Override
    public String toString() {
        String sb = String.format("ID: %d", getId()) +
                System.lineSeparator() +
                String.format("Title: %s", getTitle()) +
                System.lineSeparator() +
                String.format("Description: %s", getDescription());
        return sb;
    }

    private void setId(int id) {
        this.id = id;
    }

    private void setTitle(String title) {
        validateStringLength(title, TITLE_MIN_LENGTH, TITLE_MAX_LENGTH, TITLE_ERROR_MESSAGE);
        this.title = title;
    }

    private void setDescription(String description) {
        validateStringLength(description, DESCRIPTION_MIN_LENGTH, DESCRIPTION_MAX_LENGTH, DESCRIPTION_ERROR_MESSAGE);
        this.description = description;
    }
}
