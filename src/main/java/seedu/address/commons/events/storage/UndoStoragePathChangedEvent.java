package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

//@@author A0146123R
/**
 * Indicates that the path for the storage file should be changed back.
 */
public class UndoStoragePathChangedEvent extends BaseEvent {

    private boolean isToClearNew;

    public UndoStoragePathChangedEvent(boolean isToClearNew) {
        this.isToClearNew = isToClearNew;
    }

    public boolean isToClearNew() {
        return isToClearNew;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}