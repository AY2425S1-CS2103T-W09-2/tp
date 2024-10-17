package seedu.edulog.logic.parser;

import static seedu.edulog.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.edulog.commons.core.index.Index;
import seedu.edulog.logic.commands.DeleteCommand;
import seedu.edulog.logic.commands.DeleteIndexCommand;
import seedu.edulog.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteIndexCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
