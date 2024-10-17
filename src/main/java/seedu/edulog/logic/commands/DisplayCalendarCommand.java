package seedu.edulog.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edulog.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import seedu.edulog.model.Model;

/**
 * Lists all students in the address book to the user.
 */
public class DisplayCalendarCommand extends Command {

    public static final String COMMAND_WORD = "dc";

    public static final String MESSAGE_SUCCESS = "Displayed all lessons";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
