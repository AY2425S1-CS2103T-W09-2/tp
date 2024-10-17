package seedu.edulog.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.edulog.commons.util.ToStringBuilder;
import seedu.edulog.logic.Messages;
import seedu.edulog.logic.commands.exceptions.CommandException;
import seedu.edulog.model.Model;
import seedu.edulog.model.student.Name;
import seedu.edulog.model.student.Student;

/**
 * Deletes a student identified using it's displayed index from the edulog book.
 */
public class DeleteNameCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted Student: %1$s";

    private final Name targetName;

    public DeleteNameCommand(Name targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();
        List<Name> studentNames = lastShownList.stream().map(Student::getName).toList();

        if (!studentNames.contains(targetName)) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_NAME);
        }

        /*
         * Since the list of names is mapped from original list
         * the index of the name in the names list should be equal to
         * the index of the student in the original list.
         * Hence, we can directly access the student using studentId.
         */
        int studentId = studentNames.indexOf(targetName);
        Student studentToDelete = lastShownList.get(studentId);
        model.deleteStudent(studentToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_STUDENT_SUCCESS, Messages.format(studentToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteNameCommand)) {
            return false;
        }

        DeleteNameCommand otherDeleteCommand = (DeleteNameCommand) other;
        return targetName.equals(otherDeleteCommand.targetName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetName", targetName)
            .toString();
    }
}
