package com.taskmanagementsystem.models.tasks.contracts;

import com.taskmanagementsystem.models.tasks.enums.StorySize;
import com.taskmanagementsystem.models.tasks.enums.StoryStatus;

public interface Story extends Task, AbstractTask {

    StorySize getSize();

    StoryStatus getStatus();

    void changeSize(StorySize size);

    void changeStoryStatus();

}
