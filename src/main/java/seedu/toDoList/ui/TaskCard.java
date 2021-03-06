package seedu.toDoList.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import seedu.toDoList.model.task.ReadOnlyTask;

//@@author A0138717X
/**
 * Loading individual task
 */
public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label done;
    @FXML
    private Label recurring;
    @FXML
    private Label frequency;
    @FXML
    private Label tags;
    @FXML
    private ImageView priority;

    private ReadOnlyTask task;
    private int displayedIndex;
    private Image priorityImage;
    private int priorityLevel;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    //@@author A0138717X
    @FXML
    public void initialize() {
        HBox.setHgrow(id, Priority.ALWAYS);
        id.setText(displayedIndex + ". ");
        id.setWrapText(false);
        id.setPrefWidth(Region.USE_COMPUTED_SIZE);
        name.setText(task.getName().taskName);
        name.setWrapText(true);
        name.setPrefWidth(Region.USE_COMPUTED_SIZE);
        date.setText(task.getDate().getValue());
        date.setWrapText(true);
        if (task.isDone()) {
            cardPane.setStyle("-fx-background-color: #c2c0c0;");
        }
        done.setText(task.isDone() ? "DONE" : "");
        if (!task.tagsString().equals("")) {
            tags.setText("Tags: " + task.tagsString());
        }
        tags.setWrapText(true);
        recurring.setText(task.isRecurring() ? "Recurring: " : "");
        recurring.setWrapText(true);
        frequency.setText(task.isRecurring() ? task.getRecurring().recurringFrequency : "");
        frequency.setWrapText(true);
        priorityLevel = task.getPriorityLevel().priorityLevel;
        if (priorityLevel == 1) {
            priorityImage = new Image("/images/thunderbolt.png");
            priority.setImage(priorityImage);
            priority.setFitWidth(21.0);
            priority.setFitHeight(40.0);
        } else if (priorityLevel == 2) {
            priorityImage = new Image("/images/thunderbolt2.png");
            priority.setImage(priorityImage);
            priority.setFitWidth(36.0);
            priority.setFitHeight(40.0);
        } else if (priorityLevel == 3) {
            priorityImage = new Image("/images/thunderbolt3.png");
            priority.setImage(priorityImage);
            priority.setFitWidth(48.0);
            priority.setFitHeight(40.0);
        }
    }
    //@@author

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
