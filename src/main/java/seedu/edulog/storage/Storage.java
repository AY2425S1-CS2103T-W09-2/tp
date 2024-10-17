package seedu.edulog.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.edulog.commons.exceptions.DataLoadingException;
import seedu.edulog.model.ReadOnlyEduLog;
import seedu.edulog.model.ReadOnlyUserPrefs;
import seedu.edulog.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends EduLogStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getEduLogFilePath();

    @Override
    Optional<ReadOnlyEduLog> readEduLog() throws DataLoadingException;

    @Override
    void saveEduLog(ReadOnlyEduLog eduLog) throws IOException;

}
