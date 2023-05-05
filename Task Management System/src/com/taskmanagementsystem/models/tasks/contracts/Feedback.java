package com.taskmanagementsystem.models.tasks.contracts;

import com.taskmanagementsystem.models.tasks.enums.FeedbackStatus;

public interface Feedback extends Task {

    int getRating();

    FeedbackStatus getStatus();

    void changeFeedbackStatus();

    void changeRating(int rating);
}
