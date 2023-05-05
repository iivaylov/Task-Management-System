package com.taskmanagementsystem.models.contracts;

import java.util.List;

public interface Team {

    String getName();

    List<Member> getMembers();

    List<Board> getBoards();

    void addBoard(Board board);

    void addMember(Member member);

    Board findBoardByName(String name);

    String showTeamActivityHistory();
}
