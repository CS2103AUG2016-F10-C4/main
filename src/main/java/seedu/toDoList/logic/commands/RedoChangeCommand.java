package seedu.toDoList.logic.commands;

import seedu.toDoList.commons.exceptions.StateLimitException;

//@@author A0146123R
/**
 * Redoes change the storage location of the toDoList.
 */
public class RedoChangeCommand extends Command {

    public static final String COMMAND_WORD = "redochange";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Redo change the default storage location to the new location" 
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_CHANGE_SUCCESS = "Storage location changed.";
    public static final String MESSAGE_REDO_FAILED = "No undo change command to redo.";

    @Override
    public CommandResult execute() {
        try {
            model.redoUpdateTaskManagerPath();
            return new CommandResult(MESSAGE_CHANGE_SUCCESS);
        } catch (StateLimitException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_REDO_FAILED);
        }
    }

}