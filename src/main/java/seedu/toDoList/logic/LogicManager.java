package seedu.toDoList.logic;

import javafx.collections.ObservableList;
import seedu.toDoList.commons.core.ComponentManager;
import seedu.toDoList.commons.core.LogsCenter;
import seedu.toDoList.logic.commands.Command;
import seedu.toDoList.logic.commands.CommandResult;
import seedu.toDoList.logic.parser.Parser;
import seedu.toDoList.model.Model;
import seedu.toDoList.model.task.ReadOnlyTask;
import seedu.toDoList.storage.Storage;

import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new Parser();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
