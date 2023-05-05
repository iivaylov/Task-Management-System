package com.taskmanagementsystem.models;

import com.taskmanagementsystem.models.contracts.Board;
import com.taskmanagementsystem.models.contracts.History;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.models.contracts.Team;

import java.util.ArrayList;
import java.util.List;

import static com.taskmanagementsystem.utils.ValidationHelpers.validateStringLength;
import static java.lang.String.format;

public class TeamImpl implements Team {
    private static final int TEAM_MIN_LENGTH = 5;
    private static final int TEAM_MAX_LENGTH = 15;
    private static final String TEAM_ERROR_MESSAGE = String.format("Team name should be between %d and %d symbols.",
            TEAM_MIN_LENGTH, TEAM_MAX_LENGTH);
    private String name;
    private final List<Member> members;
    private final List<Board> boards;
    private final List<History> histories = new ArrayList<>();

    public TeamImpl(String name) {
        setName(name);
        members = new ArrayList<>();
        boards = new ArrayList<>();

        this.logHistory(format("Team %s was created.", this.viewInfo()));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }

    @Override
    public List<Board> getBoards() {
        return new ArrayList<>(boards);
    }

    @Override
    public void addBoard(Board board) {
        boards.add(board);

        this.logHistory(format("Board %s was created in team %s.", board.getName(), this.viewInfo()));
    }

    @Override
    public void addMember(Member member) {
        if (members.contains(member)) {
            throw new IllegalArgumentException(format("Member %s already exists in team %s.",
                    member.getName(), this.viewInfo()));
        }
        members.add(member);

        member.logHistory(String.format("Person with name %s was added to team %s", member.viewInfo(), this.getName()));

        this.logHistory(format("Person %s was added to team %s.", member.getName(), this.viewInfo()));
    }

    @Override
    public Board findBoardByName(String boardName) {
        return boards.stream()
                .filter(board -> board.getName().equals(boardName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("No such board name %s", boardName)));
    }

    @Override
    public String showTeamActivityHistory() {
        StringBuilder sb = new StringBuilder();
        sb.append(format("Team %s activity history:", this.viewInfo())).append(System.lineSeparator());
        for (History history : histories) {
            sb.append(history.viewInfo()).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    private void setName(String name) {
        validateStringLength(name, TEAM_MIN_LENGTH, TEAM_MAX_LENGTH, TEAM_ERROR_MESSAGE);
        this.name = name;
    }

    private void logHistory(String description) {
        this.histories.add(new HistoryImpl(description));
    }

    private String viewInfo() {
        return String.format("%s", this.name);
    }
}
