package seedu.toDoList;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.toDoList.commons.core.Config;
import seedu.toDoList.commons.core.EventsCenter;
import seedu.toDoList.commons.core.LogsCenter;
import seedu.toDoList.commons.core.Version;
import seedu.toDoList.commons.events.storage.StoragePathEvent;
import seedu.toDoList.commons.events.ui.ExitAppRequestEvent;
import seedu.toDoList.commons.exceptions.DataConversionException;
import seedu.toDoList.commons.util.ConfigUtil;
import seedu.toDoList.commons.util.StringUtil;
import seedu.toDoList.logic.Logic;
import seedu.toDoList.logic.LogicManager;
import seedu.toDoList.model.*;
import seedu.toDoList.storage.Storage;
import seedu.toDoList.storage.StorageManager;
import seedu.toDoList.ui.Ui;
import seedu.toDoList.ui.UiManager;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final Version VERSION = new Version(1, 0, 0, true);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;

    private String configFilePathUsed;

    public MainApp() {}

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing toDoList ]===========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));
        storage = new StorageManager(config.getTaskManagerFilePath(), config.getUserPrefsFilePath());

        userPrefs = initPrefs(config);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    private String getApplicationParameter(String parameterName){
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyTaskManager> addressBookOptional;
        ReadOnlyTaskManager initialData;
        try {
            addressBookOptional = storage.readTaskManager();
            if(!addressBookOptional.isPresent()){
                logger.info("Data file not found. Will be starting with an empty toDoList");
            }
            initialData = addressBookOptional.orElse(new TaskManager());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty toDoList");
            initialData = new TaskManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. . Will be starting with an empty toDoList");
            initialData = new TaskManager();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    protected Config initConfig(String configFilePath) {
        Config initializedConfig;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if(configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. " +
                    "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    protected UserPrefs initPrefs(Config config) {
        assert config != null;

        String prefsFilePath = config.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. " +
                    "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. . Will be starting with an empty toDoList");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting toDoList " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping toDoList ] =============================");
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.stop();
    }

    //@@author A0146123R
    /**
     * Updates config file when storage file path is changed.
     */
    @Subscribe
    public void handleStoragePathChangedEvent(StoragePathEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        this.config.setTaskManagerFilePath(event.getNewStorageFilePath());
        try {
            ConfigUtil.saveConfig(config, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
    }
    //@@author

    public static void main(String[] args) {
            launch(args);

}
}

