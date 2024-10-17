package seedu.edulog.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.edulog.commons.exceptions.DataLoadingException;
import seedu.edulog.model.EduLog;
import seedu.edulog.model.ReadOnlyEduLog;

/**
 * Represents a storage for {@link EduLog}.
 */
public interface EduLogStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getEduLogFilePath();

    /**
     * Returns EduLog data as a {@link ReadOnlyEduLog}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyEduLog> readEduLog() throws DataLoadingException;

    /**
     * @see #getEduLogFilePath()
     */
    Optional<ReadOnlyEduLog> readEduLog(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyEduLog} to the storage.
     * @param eduLog cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEduLog(ReadOnlyEduLog eduLog) throws IOException;

    /**
     * @see #saveEduLog(ReadOnlyEduLog)
     */
    void saveEduLog(ReadOnlyEduLog eduLog, Path filePath) throws IOException;

}
