package com.taskmanagementsystem.models;

import com.taskmanagementsystem.models.contracts.Board;
import com.taskmanagementsystem.models.contracts.History;
import com.taskmanagementsystem.models.contracts.Team;
import com.taskmanagementsystem.models.tasks.contracts.Bug;
import com.taskmanagementsystem.models.tasks.contracts.Feedback;
import com.taskmanagementsystem.models.tasks.contracts.Story;
import com.taskmanagementsystem.models.tasks.contracts.Task;

import java.util.ArrayList;
import java.util.List;

import static com.taskmanagementsystem.utils.ValidationHelpers.validateStringLength;
import static java.lang.String.format;


public class BoardImpl implements Board {
    private static final int BOARD_MIN_LENGTH = 5;
    private static final int BOARD_MAX_LENGTH = 10;
    private static final String BOARD_ERROR_MESSAGE =
            format("Board name length should be between %d and %d symbols!", BOARD_MIN_LENGTH, BOARD_MAX_LENGTH);
    private final List<Bug> bugs;
    private final List<Story> stories;
    private final List<Feedback> feedbacks;
    private final List<History> histories = new ArrayList<>();
    private Team team;
    private String name;


    public BoardImpl(Team team, String name) {
        setTeam(team);
        setName(name);
        bugs = new ArrayList<>();
        stories = new ArrayList<>();
        feedbacks = new ArrayList<>();

        this.logHistory(format("Board %s was created.", this.viewInfo()));
    }


    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Bug> getBugs() {
        return new ArrayList<>(bugs);
    }

    @Override
    public List<Story> getStories() {
        return new ArrayList<>(stories);
    }

    @Override
    public List<Feedback> getFeedback() {
        return new ArrayList<>(feedbacks);
    }

    @Override
    public List<Task> getTasksFromBoard() {
        List<Task> tasks = new ArrayList<>();
        tasks.addAll(bugs);
        tasks.addAll(stories);
        tasks.addAll(feedbacks);
        return tasks;
    }

    @Override
    public void addBug(Bug bug) {
        this.bugs.add(bug);
        this.logHistory(format("Bug %s was added to board %s.", bug.getTitle(), this.viewInfo()));
    }

    @Override
    public void addStory(Story story) {
        this.stories.add(story);
        this.logHistory(format("Story %s was added to board %s.", story.getTitle(), this.viewInfo()));
    }

    @Override
    public void addFeedback(Feedback feedback) {
        this.feedbacks.add(feedback);
        this.logHistory(format("Feedback %s was added to board %s.", feedback.getTitle(), this.viewInfo()));
    }

    @Override
    public String showBoardActivityHistory() {
        StringBuilder sb = new StringBuilder();
        if (histories.isEmpty()) {
            sb.append("No activity history.");
        } else {
            for (History history : histories) {
                sb.append(history.viewInfo()).append(System.lineSeparator());
            }
        }
        return sb.toString().trim();
    }

    @Override
    public void logHistory(String description) {
        this.histories.add(new HistoryImpl(description));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(format("Board name: { %s }", name)).append(System.lineSeparator());

        if (bugs.isEmpty()) {
            sb.append("Bugs: None").append(System.lineSeparator());
        } else {
            sb.append("---BUGS---").append(System.lineSeparator());
            for (Bug bug : bugs) {
                sb.append(bug.toString()).append(System.lineSeparator());
            }
        }

        if (stories.isEmpty()) {
            sb.append("Stories: None").append(System.lineSeparator());
        } else {
            sb.append("---STORIES---").append(System.lineSeparator());
            for (Story story : stories) {
                sb.append(story.toString()).append(System.lineSeparator());
            }
        }

        if (feedbacks.isEmpty()) {
            sb.append("Feedbacks: None").append(System.lineSeparator());
        } else {
            sb.append("---FEEDBACKS---").append(System.lineSeparator());
            for (Feedback feedback : feedbacks) {
                sb.append(feedback.toString()).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    private void setTeam(Team team) {
        this.team = team;
    }

    private void setName(String name) {
        validateStringLength(name, BOARD_MIN_LENGTH, BOARD_MAX_LENGTH, BOARD_ERROR_MESSAGE);
        this.name = name;
    }

    private String viewInfo() {
        return String.format("in team %s with board name %s", team.getName(), name);
    }
}
