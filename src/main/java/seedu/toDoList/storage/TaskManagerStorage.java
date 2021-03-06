package seedu.toDoList.storage;

import seedu.toDoList.commons.exceptions.DataConversionException;
import seedu.toDoList.model.ReadOnlyTaskManager;

import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.toDoList.model.TaskManager}.
 */
public interface TaskManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskManagerFilePath();
    
    //@@author A0146123R
    /**
     * Returns the previous file path of the data file.
     */
    FilePath getTaskManagerPreviousFilePath();
    
    /**
     * Returns the next file path of the data file.
     */
    FilePath getTaskManagerNextFilePath();
    
    /**
     * Saves the file path of the data file.
     */
    void saveTaskManagerFilePath(FilePath filePath);
    
    /**
     * Sets the file path of the data file.
     * @throws IOException 
     */
    void setTaskManagerFilePath(FilePath filePath) throws IOException;
    //@@author

    /**
     * Returns TaskManager data as a {@link ReadOnlyTaskManager}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException;

    /**
     * @see #getTaskManagerFilePath()
     */
    Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskManager} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskManager(ReadOnlyTaskManager addressBook) throws IOException;

    /**
     * @see #saveTaskManager(ReadOnlyTaskManager)
     */
    void saveTaskManager(ReadOnlyTaskManager addressBook, String filePath) throws IOException;
    
}
