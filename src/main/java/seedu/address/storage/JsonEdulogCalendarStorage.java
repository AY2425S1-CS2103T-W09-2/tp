package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyEdulogCalendar;

/**
 * A class to access EdulogCalendar data stored as a json file on the hard disk.
 */
public class JsonEdulogCalendarStorage implements EdulogCalendarStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonEdulogCalendarStorage.class);

    private Path filePath;

    public JsonEdulogCalendarStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getEdulogCalendarFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEdulogCalendar> readEdulogCalendar() throws DataLoadingException {
        return readEdulogCalendar(filePath);
    }

    /**
     * Similar to {@link #readEdulogCalendar()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyEdulogCalendar> readEdulogCalendar(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableEdulogCalendar> jsonEdulogCalendar = JsonUtil.readJsonFile(
            filePath, JsonSerializableEdulogCalendar.class);
        if (!jsonEdulogCalendar.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonEdulogCalendar.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveEdulogCalendar(ReadOnlyEdulogCalendar edulogCalendar) throws IOException {
        saveEdulogCalendar(edulogCalendar, filePath);
    }

    /**
     * Similar to {@link #saveEdulogCalendar(ReadOnlyEdulogCalendar)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveEdulogCalendar(ReadOnlyEdulogCalendar edulogCalendar, Path filePath) throws IOException {
        requireNonNull(edulogCalendar);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableEdulogCalendar(edulogCalendar), filePath);
    }

}
