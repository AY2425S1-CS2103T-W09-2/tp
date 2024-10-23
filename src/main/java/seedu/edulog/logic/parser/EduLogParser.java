package seedu.edulog.logic.parser;

import static seedu.edulog.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edulog.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.edulog.commons.core.LogsCenter;
import seedu.edulog.logic.commands.AddCommand;
import seedu.edulog.logic.commands.AddLessonCommand;
import seedu.edulog.logic.commands.ClearCommand;
import seedu.edulog.logic.commands.Command;
import seedu.edulog.logic.commands.DeleteCommand;
import seedu.edulog.logic.commands.DeleteLessonCommand;
import seedu.edulog.logic.commands.DisplayCalendarCommand;
import seedu.edulog.logic.commands.EditCommand;
import seedu.edulog.logic.commands.ExitCommand;
import seedu.edulog.logic.commands.FindCommand;
import seedu.edulog.logic.commands.HelpCommand;
import seedu.edulog.logic.commands.GiftCommand;
import seedu.edulog.logic.commands.ListCommand;
import seedu.edulog.logic.commands.MarkAllCommand;
import seedu.edulog.logic.commands.MarkCommand;
import seedu.edulog.logic.commands.UnmarkAllCommand;
import seedu.edulog.logic.commands.UnmarkCommand;
import seedu.edulog.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class EduLogParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(EduLogParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case MarkCommand.COMMAND_WORD:
            return new MarkCommandParser().parse(arguments);

        case MarkAllCommand.COMMAND_WORD:
            return new MarkAllCommand();

        case UnmarkCommand.COMMAND_WORD:
            return new UnmarkCommandParser().parse(arguments);

        case UnmarkAllCommand.COMMAND_WORD:
            return new UnmarkAllCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case GiftCommand.COMMAND_WORD:
            return new GiftCommand();

        case AddLessonCommand.COMMAND_WORD:
            return new AddLessonCommandParser().parse(arguments);

        case DeleteLessonCommand.COMMAND_WORD:
            return new DeleteLessonCommandParser().parse(arguments);

        case DisplayCalendarCommand.COMMAND_WORD:
            return new DisplayCalendarCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
