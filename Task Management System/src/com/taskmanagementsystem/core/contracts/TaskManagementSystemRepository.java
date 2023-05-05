package com.taskmanagementsystem.core.contracts;

import com.taskmanagementsystem.models.contracts.Board;
import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.models.contracts.Team;
import com.taskmanagementsystem.models.tasks.contracts.Bug;
import com.taskmanagementsystem.models.tasks.contracts.Feedback;
import com.taskmanagementsystem.models.tasks.contracts.Story;
import com.taskmanagementsystem.models.tasks.contracts.Task;
import com.taskmanagementsystem.models.tasks.enums.Priority;
import com.taskmanagementsystem.models.tasks.enums.Severity;
import com.taskmanagementsystem.models.tasks.enums.StorySize;

import java.util.List;

public interface TaskManagementSystemRepository {

    List<Team> getTeams();

    List<Board> getBoards();

    List<Task> getTasks();

    List<Member> getMembers();

    List<Bug> getBugs();

    List<Story> getStories();

    List<Feedback> getFeedbacks();


    boolean teamExists(String teamName);

    boolean boardExists(String boardName);

    boolean memberExists(String memberName);


    Team findTeamByName(String name);

    Board findBoardByName(String name);

    Board findBoardInTeam(Team team, String boardName);


    Member findMemberByName(String name);

    Member findMemberInTeam(Team team, String memberName);

    Task findTaskInBoard(Board board, int id);

    Task findTaskById(int id);

    Bug findBugById(int id);

    Story findStoryById(int id);

    Feedback findFeedbackById(int id);

/*


    Bug findBugByTitle(String title);

    Bug findBugByStatus(String status);





    Story findStoryByTitle(String title);

    Story findStoryByStatus(String status);





    Feedback findFeedbackByTitle(String title);

    Feedback findFeedbackByStatus(String status);
*/


    Team createTeam(String name);

    Board createBoard(Team team, String name);

    Member createMember(String name);


    Bug createBug(Team team, Board board, String title, String description, List<String> steps, Priority priority,
                  Severity severity, Member assignee);

    Story createStory(Team team, Board board, String title, String description, Priority priority,
                      StorySize size, Member assignee);

    Feedback createFeedback(Team team, Board board, String title, String description,
                            int rating);


}

