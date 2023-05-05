package com.taskmanagementsystem.models.tasks.contracts;

import com.taskmanagementsystem.models.contracts.Member;
import com.taskmanagementsystem.models.tasks.enums.Priority;

public interface AbstractTask {

    Priority getPriority();

    Member getAssignee();

    void changePriority(Priority priority);

}
