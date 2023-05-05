package com.taskmanagementsystem.models.tasks;

import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.models.tasks.contracts.AbstractTask;
import com.taskmanagementsystem.models.tasks.enums.Priority;

public abstract class AbstractTaskBase extends TaskBase implements AbstractTask {

    private Priority priority;
    private Member assignee;

    public AbstractTaskBase(int id, String title, String description, Priority priority, Member assignee) {
        super(id, title, description);
        setPriority(priority);
        setAssignee(assignee);
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public Member getAssignee() {
        return assignee;
    }

    @Override
    public void changePriority(Priority priority) {
        if (this.priority.toString().equals(priority.toString())) {
            throw new IllegalArgumentException(String.format("Priority is already %s", priority));
        }
        this.priority = priority;
    }

    @Override
    public void changeAssignee(Member assignee) {
        if (this.assignee.getName().equalsIgnoreCase(assignee.getName())) {
            throw new IllegalArgumentException(String.format("Assignee is already %s", assignee.getName()));
        }
        this.assignee = assignee;
    }

    @Override
    public String toString() {
        String sb = String.format("%s", super.toString()) +
                System.lineSeparator() +
                String.format("Priority: %s", getPriority()) +
                System.lineSeparator() +
                String.format("Assignee: %s", getAssignee().getName());
        return sb;
    }

    private void setPriority(Priority priority) {
        this.priority = priority;
    }

    private void setAssignee(Member assignee) {
        this.assignee = assignee;
    }
}
