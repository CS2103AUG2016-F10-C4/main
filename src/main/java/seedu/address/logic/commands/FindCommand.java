package seedu.address.logic.commands;

import java.util.Set;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks with names containing any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " horror night";

    private final Set<Set<String>> keywordsGroups;
    private final boolean isExactSearch;

    public FindCommand(Set<Set<String>> keywordsGroups, boolean isExactSearch) {
        this.keywordsGroups = keywordsGroups;
        this.isExactSearch = isExactSearch;
    }

    @Override
    public CommandResult execute() {
        if (isExactSearch) {
            model.updateFilteredTaskListWithKeywords(keywordsGroups);
        } else {
            model.updateFilteredTaskListWithStemmedKeywords(keywordsGroups);
        }
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
