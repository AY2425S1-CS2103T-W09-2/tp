package seedu.edulog.logic.commands;

import seedu.edulog.commons.core.LogsCenter;
import seedu.edulog.commons.util.RandomUtil;
import seedu.edulog.logic.commands.exceptions.CommandException;
import seedu.edulog.model.Model;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

/**
 * Recommends a random gift idea to the teacher out of a predefined list inside a textfile
 */
public class GiftCommand extends Command {
    private static final Logger logger = LogsCenter.getLogger(GiftCommand.class);

    public static final String COMMAND_WORD = "gift";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gives you a random gift idea";

    private final Path pathToGiftsTextfile = Paths.get("gifts.txt");


    /**
     * Returns a random gift defined in a text file
     * @return random gift
     */
    private String getRandomGift() {
        List<String> gifts = fetchGiftListFromFile(pathToGiftsTextfile);
        int randomNumber = RandomUtil.getRandomNumber(gifts.size());
        return gifts.get(randomNumber);
    }

    private List<String> fetchGiftListFromFile(Path filePath) {
        try {
            return Files.readAllLines(filePath, Charset.defaultCharset());
        } catch (IOException e) {
            logger.finer(e.getMessage());
            return List.of();
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("The random gift is: " + getRandomGift());
    }
}
