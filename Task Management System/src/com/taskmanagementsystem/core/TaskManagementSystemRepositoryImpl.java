package com.taskmanagementsystem.core;

import com.taskmanagementsystem.core.contracts.TaskManagementSystemRepository;
import com.taskmanagementsystem.exceptions.ElementNotFoundException;
import com.taskmanagementsystem.models.BoardImpl;
import com.taskmanagementsystem.models.MemberImpl;
import com.taskmanagementsystem.models.TeamImpl;
import com.taskmanagementsystem.models.contracts.Board;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.models.contracts.Team;
import com.taskmanagementsystem.models.tasks.BugImpl;
import com.taskmanagementsystem.models.tasks.FeedbackImpl;
import com.taskmanagementsystem.models.tasks.StoryImpl;
import com.taskmanagementsystem.models.tasks.contracts.*;
import com.taskmanagementsystem.models.tasks.enums.Priority;
import com.taskmanagementsystem.models.tasks.enums.Severity;
import com.taskmanagementsystem.models.tasks.enums.StorySize;

import java.util.ArrayList;
import java.util.List;

public class TaskManagementSystemRepositoryImpl implements TaskManagementSystemRepository {
    // TODO: 17.12.2022 г. Implement all methods
    private static final String NO_SUCK_TEAM_NAME = "There is no team with name %s!";
    private static final String NO_SUCH_BOARD_NAME = "There is no board with name %s!";
    private static final String NO_MEMBER_MESSAGE = "There is no member with name %s!";
    public static final String BOARD_ERROR = "There is no board with name %s in team %s!";
    public static final String TEAM_MEMBER_ERROR = "There is no member with name %s in team %s!";
    public static final String TASKS_IN_BOARD_ERROR = "There is no task with id %d in board %s!";
    public static final String NO_ID_RECORD = "No record with ID %d";
    //    public static final String NO_TASK_ID_MESSAGE = "There is no task with id %d!";
    private int nextId;
    private final List<Team> teams = new ArrayList<>();
    private final List<Board> boards = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();
    private final List<Bug> bugs = new ArrayList<>();
    private final List<Story> stories = new ArrayList<>();
    private final List<Feedback> feedbacks = new ArrayList<>();

    public TaskManagementSystemRepositoryImpl() {
        nextId = 0;
    }

    @Override
    public List<Team> getTeams() {
        return new ArrayList<>(teams);
    }

    @Override
    public List<Board> getBoards() {
        return new ArrayList<>(boards);
    }

    @Override
    public List<Member> getMembers() {
        return new ArrayList<>(members);
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
    public List<Feedback> getFeedbacks() {
        return new ArrayList<>(feedbacks);
    }

    @Override
    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.addAll(bugs);
        tasks.addAll(stories);
        tasks.addAll(feedbacks);
        return tasks;
    }

    @Override
    public boolean teamExists(String teamName) {
        boolean teamExists = false;

        for (Team team : teams) {
            if (team.getName().equalsIgnoreCase(teamName)) {
                teamExists = true;
                break;
            }
        }
        return teamExists;
    }

    @Override
    public boolean boardExists(String boardName) {
        boolean boardExists = false;

        for (Board board : boards) {
            if (board.getName().equalsIgnoreCase(boardName)) {
                boardExists = true;
                break;
            }
        }
        return boardExists;
    }

    @Override
    public boolean memberExists(String memberName) {
        boolean memberExists = false;

        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(memberName)) {
                memberExists = true;
                break;
            }
        }
        return memberExists;
    }

    @Override
    public Team findTeamByName(String teamName) {
        return teams.stream()
                .filter(team -> team.getName().equals(teamName))
                .findFirst()
                .orElseThrow(() -> new ElementNotFoundException(String.format(NO_SUCK_TEAM_NAME, teamName)));
    }

    @Override
    public Board findBoardByName(String boardName) {
        return boards.stream()
                .filter(board -> board.getName().equals(boardName))
                .findFirst()
                .orElseThrow(() -> new ElementNotFoundException(String.format(NO_SUCH_BOARD_NAME, boardName)));
    }

    @Override
    public Member findMemberByName(String name) {
        return members.stream()
                .filter(member -> member.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new ElementNotFoundException(String.format(NO_MEMBER_MESSAGE, name)));
    }

    @Override
    public Task findTaskById(int id) {
        return findElementById(getTasks(), id);
    }

    @Override
    public Bug findBugById(int id) {
        return findElementById(bugs, id);
    }

    @Override
    public Story findStoryById(int id) {
        return findElementById(stories, id);
    }

    @Override
    public Feedback findFeedbackById(int id) {
        return findElementById(feedbacks, id);
    }

    @Override
    public Team createTeam(String name) {
        Team team = new TeamImpl(name);
        teams.add(team);
        return team;
    }

    @Override
    public Board createBoard(Team team, String name) {
        Board board = new BoardImpl(team, name);
        boards.add(board);
        return board;
    }

    @Override
    public Member createMember(String name) {
        Member member = new MemberImpl(name);
        members.add(member);
        return member;
    }

    @Override
    public Bug createBug(Team team, Board board, String title, String description, List<String> steps,
                         Priority priority, Severity severity, Member assignee) {
        Bug bug = new BugImpl(++nextId, title, description, steps, priority, severity, assignee);
        /*   tasks.add(bug);*/
        bugs.add(bug);
        return bug;
    }

    @Override
    public Story createStory(Team team, Board board, String title, String description, Priority priority,
                             StorySize size, Member assignee) {
        Story story = new StoryImpl(++nextId, title, description, priority, size, assignee);
        /* tasks.add(story);*/
        stories.add(story);
        return story;
    }

    @Override
    public Feedback createFeedback(Team team, Board board, String title, String description, int rating) {
        Feedback feedback = new FeedbackImpl(++nextId, title, description, rating);
        /*   tasks.add(feedback);*/
        feedbacks.add(feedback);
        return feedback;
    }


    @Override
    public Board findBoardInTeam(Team team, String boardName) {
        return team.getBoards().stream()
                .filter(board -> board.getName().equals(boardName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException
                        (String.format(BOARD_ERROR, boardName, team.getName())));
    }

    @Override
    public Member findMemberInTeam(Team team, String memberName) {
        return team.getMembers().stream()
                .filter(member -> member.getName().equals(memberName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException
                        (String.format(TEAM_MEMBER_ERROR, memberName, team.getName())));
    }

    @Override
    public Task findTaskInBoard(Board board, int id) {
        return board.getTasksFromBoard().stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException
                        (String.format(TASKS_IN_BOARD_ERROR, id, board.getName())));
    }

    private <T extends Identifiable> T findElementById(List<T> elements, int id) {
        for (T element : elements) {
            if (element.getId() == id) {
                return element;
            }
        }
        throw new ElementNotFoundException(String.format(NO_ID_RECORD, id));
    }
}
