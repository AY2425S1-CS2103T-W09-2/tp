package seedu.address.logic.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calendar.Lesson;

/**
 * Deletes a student identified using it's displayed index from the address book.
 */
public class DeleteLessonCommand extends Command {

    public static final String COMMAND_WORD = "deletec";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the student identified by the description used in the displayed calendar.\n"
            + "Parameters: STRING\n"
            + "Example: " + COMMAND_WORD + " Secondary 4 Chemistry Class";

    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted Class: %1$s";
    public static final String MESSAGE_NONEXISTENT_LESSON = "This lesson does not exist in the calendar";

    private final String description;

    public DeleteLessonCommand(String description) {
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (isNull(model.findLesson(description))) {
            throw new CommandException(MESSAGE_NONEXISTENT_LESSON);
        }

        Lesson lessonToDelete = model.findLesson(description);
        model.removeLesson(lessonToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_STUDENT_SUCCESS, lessonToDelete));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteLessonCommand)) {
            return false;
        }

        DeleteLessonCommand otherDeleteLessonCommand = (DeleteLessonCommand) other;
        return description.equals(otherDeleteLessonCommand.description);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toDelete", description)
                .toString();
    }
}
