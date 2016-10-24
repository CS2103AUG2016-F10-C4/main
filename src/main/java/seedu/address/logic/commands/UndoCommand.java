package seedu.address.logic.commands;

import seedu.address.commons.exceptions.StateLimitException;

/** 
 * Undoes the most recent action (up to 5 times).  
 */
public class UndoCommand extends Command{
    
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_SUCCESS = "Undid the most receent action.";
    public static final String MESSAGE_UNDO_FAILED = "No command to undo.";

    public UndoCommand() {}

    @Override
    public CommandResult execute() {
        try {
            String message = model.getPreviousState();
            return new CommandResult(MESSAGE_UNDO_SUCCESS);
        } catch (StateLimitException e){
            return new CommandResult(MESSAGE_UNDO_FAILED);
        }
    }

}