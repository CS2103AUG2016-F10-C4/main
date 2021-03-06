package seedu.toDoList.model;

import seedu.toDoList.model.tag.Tag;
import seedu.toDoList.model.tag.UniqueTagList;
import seedu.toDoList.model.task.ReadOnlyTask;
import seedu.toDoList.model.task.TaskList;

import java.util.List;

/**
 * Unmodifiable view of an task manager
 */
public interface ReadOnlyTaskManager {

    UniqueTagList getUniqueTagList();

    TaskList getUniqueTaskList();

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
