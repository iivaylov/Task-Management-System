package com.taskmanagementsystem.models.tasks;

import com.taskmanagementsystem.exceptions.InvalidUserInputException;
import com.taskmanagementsystem.models.contracts.Comment;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.models.tasks.contracts.Story;
import com.taskmanagementsystem.models.tasks.enums.Priority;
import com.taskmanagementsystem.models.tasks.enums.StorySize;
import com.taskmanagementsystem.models.tasks.enums.StoryStatus;

public class StoryImpl extends AbstractTaskBase implements Story {

    private static final StoryStatus FINAL_STATUS = StoryStatus.DONE;
    private static final String STATUS_CHANGE_ERROR = "The status cannot be changed! It is already done!";

    private StorySize size;
    private StoryStatus status;


    public StoryImpl(int id, String title, String description,
                     Priority priority, StorySize size, Member assignee) {
        super(id, title, description, priority, assignee);
        setSize(size);
        this.status = StoryStatus.NOT_DONE;
    }

    @Override
    public StorySize getSize() {
        return size;
    }

    private void setSize(StorySize size) {
        this.size = size;
    }

    @Override
    public StoryStatus getStatus() {
        return status;
    }

    private void setStatus(StoryStatus status) {
        this.status = status;
    }

    @Override
    public void changeSize(StorySize size) {
        if (this.size == size) {
            throw new InvalidUserInputException(String.format("The size is already %s!", size));
        }
        this.size = size;
    }

    @Override
    public void changeStoryStatus() {
        if (status != FINAL_STATUS) {
            setStatus(StoryStatus.values()[status.ordinal() + 1]);
        } else {
            throw new InvalidUserInputException(STATUS_CHANGE_ERROR.trim());
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%s", super.toString()))
                .append(System.lineSeparator())
                .append(String.format("Size: %s", getSize()))
                .append(System.lineSeparator())
                .append(String.format("Status: %s", getStatus()))
                .append(System.lineSeparator());
        if (getComments().isEmpty()) {
            stringBuilder.append("Comments: None")
                    .append(System.lineSeparator());
        } else {
            stringBuilder.append("Comments--->").append(System.lineSeparator());
            for (Comment comment : getComments()) {
                stringBuilder.append(comment.toString()).append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }
}
