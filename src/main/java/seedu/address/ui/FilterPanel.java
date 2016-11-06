package seedu.address.ui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.FilterPanelChangedEvent;
import seedu.address.commons.events.ui.JumpToFilterPanelEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.commons.util.Types;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.EventDate;
import seedu.address.model.task.Recurring;

public class FilterPanel extends UiPart {

    public static final String SUCCESS_FILTER = "Filter the todoList";
    public static final String INVALID_FILTER = "Invalid filter: ";

    private static final Logger logger = LogsCenter.getLogger(FilterPanel.class);
    private static final String FXML = "FilterPanel.fxml";

    private static final String EMPTY = "";
    private static final String NIL = "nil";
    private static final String SPACE = "\\s+";
    private static final String ONE = "1";
    private static final String TWO = "2";
    private static final String THREE = "3";

    private GridPane mainPane;
    private AnchorPane placeHolder;
    private ResultDisplay resultDisplay;

    @FXML
    private ToggleButton eventsToggleButton;

    @FXML
    private ToggleButton tasksToggleButton;

    @FXML
    private ToggleButton doneToggleButton;

    @FXML
    private ToggleButton undoneToggleButton;

    @FXML
    private TextField deadlineTextField;

    @FXML
    private TextField recurringTextField;

    @FXML
    private TextField startDateTextField;

    @FXML
    private TextField endDateTextField;

    @FXML
    private TextField tagsTextField;

    @FXML
    private ChoiceBox<String> priorityChoiceBox;

    public static FilterPanel load(Stage stage, AnchorPane placeHolder, ResultDisplay resultDisplay) {
        FilterPanel filterPanel = UiPartLoader.loadUiPart(stage, placeHolder, new FilterPanel());
        filterPanel.configure(resultDisplay);
        return filterPanel;
    }

    public void configure(ResultDisplay resultDisplay) {
        this.resultDisplay = resultDisplay;
        addMainPane();
        initialPriority();
        registerAsAnEventHandler(this);
    }

    private void addMainPane() {
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(mainPane);
    }

    private void initialPriority() {
        priorityChoiceBox.setItems(FXCollections.observableArrayList(EMPTY, ONE, TWO, THREE));
    }

    @Override
    public void setNode(Node node) {
        mainPane = (GridPane) node;
    }

    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        this.placeHolder = placeholder;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Subscribe
    private void handleJumpFilterPanelEvent(JumpToFilterPanelEvent event) {
        Types qualification = event.getQualification();
        switch (qualification) {
        case DEADLINE:
            deadlineTextField.requestFocus();
            return;
        case START_DATE:
            startDateTextField.requestFocus();
            return;
        case END_DATE:
            endDateTextField.requestFocus();
            return;
        case RECURRING:
            recurringTextField.requestFocus();
            return;
        case PRIORITY_LEVEL:
            priorityChoiceBox.requestFocus();
            priorityChoiceBox.show();
            return;
        case TAG:
            tagsTextField.requestFocus();
            return;
        default:
            break;
        }
    }

    @FXML
    private void handleFilterChanged() {
        Set<Types> types = handleTypesChanged();
        Map<Types, String> qualifications;
        try {
            qualifications = handleQualificationsChanged();
        } catch (IllegalValueException e) {
            logger.info(INVALID_FILTER + e.getMessage());
            resultDisplay.setStyleToIndicateIncorrectCommand();
            resultDisplay.postMessage(INVALID_FILTER + e.getMessage());
            return;
        }
        Set<String> tagSet = handleTagsChanged();
        logger.info("Input in filter panel changed");
        resultDisplay.setStyleToIndicateCorrectCommand();
        resultDisplay.postMessage(SUCCESS_FILTER);
        raise(new FilterPanelChangedEvent(types, qualifications, tagSet));
    }

    private Set<Types> handleTypesChanged() {
        Set<Types> types = new HashSet<>();
        if (eventsToggleButton.isSelected()) {
            types.add(Types.EVENTS);
        }
        if (tasksToggleButton.isSelected()) {
            types.add(Types.TASKS);
        }
        if (doneToggleButton.isSelected()) {
            types.add(Types.DONE);
        }
        if (undoneToggleButton.isSelected()) {
            types.add(Types.UNDONE);
        }
        return types;
    }

    private Map<Types, String> handleQualificationsChanged() throws IllegalValueException {
        HashMap<Types, String> qualifications = new HashMap<>();
        String deadline = deadlineTextField.getText().trim();
        if (!deadline.equals(EMPTY)) {
            if (deadline.equals(NIL)) {
                qualifications.put(Types.DEADLINE, EMPTY);
            } else {
                deadlineTextField.requestFocus();
                deadline = Deadline.getValidDate(deadline);
                qualifications.put(Types.DEADLINE, deadline);
            }
        }
        String startDate = startDateTextField.getText().trim();
        if (!startDate.equals(EMPTY)) {
            startDateTextField.requestFocus();
            startDate = EventDate.getValidDate(startDate);
            qualifications.put(Types.START_DATE, startDate);
        }
        String endDate = endDateTextField.getText().trim();
        if (!endDate.equals(EMPTY)) {
            endDateTextField.requestFocus();
            endDate = EventDate.getValidDate(endDate);
            qualifications.put(Types.END_DATE, endDate);
        }
        String recurring = recurringTextField.getText().trim();
        if (!recurring.equals(EMPTY)) {
            if (Recurring.isValidFrequency(recurring)) {
                qualifications.put(Types.RECURRING, recurring);
            } else {
                recurringTextField.requestFocus();
                throw new IllegalValueException(Recurring.MESSAGE_RECURRING_CONSTRAINTS);
            }
        }
        String priority = priorityChoiceBox.getSelectionModel().getSelectedItem().toString();
        if (!priority.equals(EMPTY)) {
            qualifications.put(Types.PRIORITY_LEVEL, priority);
        }
        return qualifications;
    }

    private Set<String> handleTagsChanged() {
        String tags = tagsTextField.getText().trim();
        Set<String> tagSet;
        if (tags.equals(EMPTY)) {
            tagSet = new HashSet<>();
        } else {
            tagSet = new HashSet<>(Arrays.asList(tags.split(SPACE)));
        }
        return tagSet;
    }

}
