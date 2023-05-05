package com.taskmanagementsystem.models.tasks;

import com.taskmanagementsystem.exceptions.InvalidUserInputException;
import com.taskmanagementsystem.models.contracts.Comment;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.models.tasks.contracts.Feedback;
import com.taskmanagementsystem.models.tasks.enums.FeedbackStatus;

public class FeedbackImpl extends TaskBase implements Feedback {

    private static final FeedbackStatus FINAL_STATUS = FeedbackStatus.DONE;

    private static final String STATUS_CHANGE_ERROR = "The status cannot be changed! It is already done!";
    private int rating;
    private FeedbackStatus status;


    public FeedbackImpl(int id, String title, String description, int rating) {
        super(id, title, description);
        setRating(rating);
        this.status = FeedbackStatus.NEW;
    }

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public FeedbackStatus getStatus() {
        return status;
    }

    @Override
    public void changeFeedbackStatus() {
        if (status != FINAL_STATUS) {
            setFeedbackStatus(FeedbackStatus.values()[status.ordinal() + 1]);
        } else {
            throw new InvalidUserInputException(STATUS_CHANGE_ERROR.trim());
        }
    }

    @Override
    public void changeRating(int rating) {
        if (this.rating == rating) {
            throw new InvalidUserInputException(String.format("The rating is already %d!", rating));
        }
        this.rating = rating;
    }

    @Override
    public void changeAssignee(Member member) throws InvalidUserInputException {
        throw new InvalidUserInputException("Feedback cannot be assigned to a member!");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%s", super.toString()))
                .append(System.lineSeparator())
                .append(String.format("Rating: %d", getRating()))
                .append(System.lineSeparator())
                .append(String.format("Status: %s", getStatus()))
                .append(System.lineSeparator());

        if (getComments().isEmpty()) {
            stringBuilder.append("Comments: None");
        } else {
            stringBuilder.append("Comments--->").append(System.lineSeparator());
            for (Comment comment : getComments()) {
                stringBuilder.append(comment.toString()).append(System.lineSeparator());
            }
        }
        return stringBuilder.toString().trim();
    }

    private void setRating(int rating) {
        this.rating = rating;
    }

    private void setFeedbackStatus(FeedbackStatus status) {
        this.status = status;
    }
}
