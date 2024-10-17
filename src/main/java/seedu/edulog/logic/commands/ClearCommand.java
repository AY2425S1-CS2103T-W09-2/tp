package seedu.edulog.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.edulog.model.EduLog;
import seedu.edulog.model.Model;

/**
 * Clears the edulog book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "EduLog has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setEduLog(new EduLog());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
