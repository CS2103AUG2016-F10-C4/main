package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.util.Callback;

/**
 * Represents an event or a task (with or without deadline) in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private boolean isEvent;
    private Name name;
    private Date date;
    private boolean isDone;
    private BooleanProperty done; // Use Observable so that listeners can know when the task's done status is updated

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Date date, UniqueTagList tags) {
        this(name, date, tags, false);
    }
    
    public Task(Name name, Date date, UniqueTagList tags, boolean isDone) {
        assert !CollectionUtil.isAnyNull(name, date, tags);
        this.name = name;
        this.date = date;
        if (date instanceof EventDate) {
            isEvent = true;
        } else {
            isEvent = false;
        }
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.isDone = isDone;
        this.done = new SimpleBooleanProperty(isDone);
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDate(), source.getTags(), source.isDone());
    }
    
    public Task(Name name) {
    	this.name=name;
	}

	@Override
    public Name getName() {
        return name;
    }

    @Override
    public Date getDate() {
        return date;
    }
    
    @Override
    public boolean isEvent() {
        return isEvent;
    }
    
    @Override
    public boolean isDone(){
    	return isDone;
    }
    
    /**
     * Returns the Observable wrapper of the done status
     */
    public BooleanProperty getDone() {
        return done;
    }
    
    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, date, tags, isDone);
    }

    @Override
    public String toString() {
        return getAsText();
    }

	@Override
	public void markAsDone() {
		isDone=true;
		done.set(true);
	}
	
	/*
	 * Makes Task observable by its done status
	 */
	public static Callback<Task, Observable[]> extractor() {
	    return (Task task) -> new Observable[]{task.getDone()};
	}

}
