package com.taskmanagementsystem.models.contracts;

import com.taskmanagementsystem.models.tasks.contracts.Bug;
import com.taskmanagementsystem.models.tasks.contracts.Feedback;
import com.taskmanagementsystem.models.tasks.contracts.Story;
import com.taskmanagementsystem.models.tasks.contracts.Task;

import java.util.List;

public interface Board {

    Team getTeam();

    String getName();

    List<Bug> getBugs();

    List<Story> getStories();

    List<Feedback> getFeedback();

    List<Task> getTasksFromBoard();

    String showBoardActivityHistory();

    void addBug(Bug bug);

    void addStory(Story story);

    void addFeedback(Feedback feedback);

//    int totalTasks();


    //TEST
    void logHistory(String message);
}
