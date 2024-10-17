package seedu.edulog.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.edulog.commons.core.LogsCenter;
import seedu.edulog.commons.exceptions.DataLoadingException;
import seedu.edulog.commons.exceptions.IllegalValueException;
import seedu.edulog.commons.util.FileUtil;
import seedu.edulog.commons.util.JsonUtil;
import seedu.edulog.model.ReadOnlyEduLog;

/**
 * A class to access EduLog data stored as a json file on the hard disk.
 */
public class JsonEduLogStorage implements EduLogStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonEduLogStorage.class);

    private Path filePath;

    public JsonEduLogStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getEduLogFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEduLog> readEduLog() throws DataLoadingException {
        return readEduLog(filePath);
    }

    /**
     * Similar to {@link #readEduLog()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyEduLog> readEduLog(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableEduLog> jsonEduLog = JsonUtil.readJsonFile(
                filePath, JsonSerializableEduLog.class);
        if (!jsonEduLog.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonEduLog.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveEduLog(ReadOnlyEduLog eduLog) throws IOException {
        saveEduLog(eduLog, filePath);
    }

    /**
     * Similar to {@link #saveEduLog(ReadOnlyEduLog)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveEduLog(ReadOnlyEduLog eduLog, Path filePath) throws IOException {
        requireNonNull(eduLog);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableEduLog(eduLog), filePath);
    }

}
