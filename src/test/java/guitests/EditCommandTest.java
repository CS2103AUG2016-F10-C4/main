package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_NAME;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

//@@author A0138717X
public class EditCommandTest extends TaskManagerGuiTest {

	@Test
    public void edit_by_name() {
		TestTask[] currentList = td.getTypicalTasks();
//		commandBox.runCommand("add n/Lecture");
//		commandBox.runCommand("add n/Assignment");
//		commandBox.runCommand("add n/Study");
//		commandBox.runCommand("add n/Study");
		assertEditSuccess("Project meeting","s/","12.10.2016-10",currentList);
		assertEditSuccess("Read book","d/","20.10.2016",currentList);
		assertEditSuccess("Project meeting","e/","12.10.2016-12",currentList);
		assertEditSuccess("Work","n/","Swimming",currentList);
		assertEditSuccess("Meet old friends","d/","20.10.2016",currentList); //duplicate result
		assertEditSuccess("Meet old friends","d/","20.10.2016", 1,currentList);
	}

	private void assertEditSuccess(String name, String type, String details, TestTask[] currentList) {
		commandBox.runCommand("edit "+ name + " " + type + details);
		String editedCard = null;
		if(type.equals("e/") || type.equals("d/") || type.equals("s/"))
			editedCard = taskListPanel.navigateToTask(name).getDate();
		else if(type.equals("n/"))
			editedCard = taskListPanel.navigateToTask(details).getName();
		else
			assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT));
//        assertMatching(details, editedCard);
//        assertListSize(currentList.length);
//	    assertTrue(taskListPanel.isListMatching(currentList));
	}

//	private void assertMatching(String details, String editedCard) {
//		assertTrue(details.equals(editedCard));
//	}

	private void assertEditSuccess(String name, String type, String details, int index, TestTask[] currentList) {
		commandBox.runCommand("edit "+ name + " " + type + details + " i/" + index);
		String editedCard = null;
		if(type.equals("e/") || type.equals("d/") || type.equals("s/"))
			editedCard = taskListPanel.getTask(index).getDate().toString();
		else if(type.equals("n/"))
			editedCard = taskListPanel.getTask(index).getName().taskName;
		else
			assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT));
//        assertMatching(details, editedCard);
//        assertListSize(currentList.length);
//	    assertTrue(taskListPanel.isListMatching(currentList));
    }
}
