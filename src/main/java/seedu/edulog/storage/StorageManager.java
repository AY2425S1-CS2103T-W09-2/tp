package seedu.edulog.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.edulog.commons.core.LogsCenter;
import seedu.edulog.commons.exceptions.DataLoadingException;
import seedu.edulog.model.ReadOnlyEduLog;
import seedu.edulog.model.ReadOnlyUserPrefs;
import seedu.edulog.model.UserPrefs;

/**
 * Manages storage of EduLog data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private EduLogStorage eduLogStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code EduLogStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(EduLogStorage eduLogStorage, UserPrefsStorage userPrefsStorage) {
        this.eduLogStorage = eduLogStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ EduLog methods ==============================

    @Override
    public Path getEduLogFilePath() {
        return eduLogStorage.getEduLogFilePath();
    }

    @Override
    public Optional<ReadOnlyEduLog> readEduLog() throws DataLoadingException {
        return readEduLog(eduLogStorage.getEduLogFilePath());
    }

    @Override
    public Optional<ReadOnlyEduLog> readEduLog(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return eduLogStorage.readEduLog(filePath);
    }

    @Override
    public void saveEduLog(ReadOnlyEduLog eduLog) throws IOException {
        saveEduLog(eduLog, eduLogStorage.getEduLogFilePath());
    }

    @Override
    public void saveEduLog(ReadOnlyEduLog eduLog, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        eduLogStorage.saveEduLog(eduLog, filePath);
    }

}
