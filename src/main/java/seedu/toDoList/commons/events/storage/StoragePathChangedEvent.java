package seedu.toDoList.commons.events.storage;

import seedu.toDoList.commons.events.BaseEvent;

//@@author A0146123R
/**
 * Indicates the storage path is changed to a new path.
 */
public class StoragePathChangedEvent extends BaseEvent {

    private String newStorageFilePath;
    private boolean isToClearOld;

    public StoragePathChangedEvent(String newStorageFilePath, boolean isToClearOld) {
        assert newStorageFilePath != null;

        this.newStorageFilePath = newStorageFilePath;
        this.isToClearOld = isToClearOld;
    }

    public String getNewStorageFilePath() {
        return newStorageFilePath;
    }

    public Boolean isToClearOld() {
        return isToClearOld;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}