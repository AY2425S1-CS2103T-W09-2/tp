package seedu.edulog.logic.commands;

import seedu.edulog.logic.commands.exceptions.CommandException;
import seedu.edulog.model.Model;

/**
 * Deletes a student identified using it's displayed index from the edulog book.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted Student: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the student identified by the index number or name used in the displayed student list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "OR NAME (case-sensitive)\n"
        + "Example: " + COMMAND_WORD + " 1\n"
        + "OR " + COMMAND_WORD + " John Doe";

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract String toString();
}
