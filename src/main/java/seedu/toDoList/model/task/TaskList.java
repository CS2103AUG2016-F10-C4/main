package seedu.toDoList.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.toDoList.commons.exceptions.DuplicateDataException;
import seedu.toDoList.commons.exceptions.IllegalValueException;
import seedu.toDoList.commons.util.CollectionUtil;

import java.util.*;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class TaskList implements Iterable<Task> {


    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching person in the list.
     */
    public static class TaskNotFoundException extends Exception {}

    private final ObservableList<Task> internalList = FXCollections.observableArrayList(Task.extractor());

    /**
     * Constructs empty PersonList.
     */
    public TaskList() {}

    /**
     * Adds a task to the list.
     */
    public void add(Task toAdd)  {
        assert toAdd != null;

        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

	public void edit(ReadOnlyTask toEdit, String type, String details) throws IllegalValueException {
		assert toEdit != null;
		for(Task t : internalList) {
        	if(t.equals(toEdit)) {
        		t.editDetail(type, details);
       	}
       }
	}

    public ObservableList<Task> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskList // instanceof handles nulls
                && this.internalList.equals(
                ((TaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
