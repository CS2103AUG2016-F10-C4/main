package seedu.toDoList.storage;

import seedu.toDoList.commons.events.model.TaskManagerChangedEvent;
import seedu.toDoList.commons.events.storage.DataSavingExceptionEvent;
import seedu.toDoList.commons.events.storage.RedoStoragePathChangedEvent;
import seedu.toDoList.commons.events.storage.StoragePathChangedEvent;
import seedu.toDoList.commons.events.storage.UndoStoragePathChangedEvent;
import seedu.toDoList.commons.exceptions.DataConversionException;
import seedu.toDoList.model.ReadOnlyTaskManager;
import seedu.toDoList.model.UserPrefs;

import java.io.IOException;
import java.util.Optional;

/**
 * API of the Storage component
 */
public interface Storage extends TaskManagerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException;

    @Override
    void saveTaskManager(ReadOnlyTaskManager addressBook) throws IOException;

    /**
     * Saves the current version of the Task Manager to the hard disk. Creates
     * the data file if it is missing. Raises {@link DataSavingExceptionEvent}
     * if there was an error during saving.
     */
    void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce);

    // @@author A0146123R
    /**
     * Saves the current version of the Task Manager to a new file in hard disk.
     * Delete the old data file if it is specified. Raises
     * {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleStoragePathChangedEvent(StoragePathChangedEvent event);

    /**
     * Saves the current version of the Task Manager to the previous file in
     * hard disk. Delete the new data file if it is specified. Raises
     * {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleUndoStoragePathChangedEvent(UndoStoragePathChangedEvent event);

    /**
     * Redo saves the current version of the Task Manager to the new file in
     * hard disk. Delete the new data file if it was previously specified. Raises
     * {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleRedoStoragePathChangedEvent(RedoStoragePathChangedEvent event);
}
