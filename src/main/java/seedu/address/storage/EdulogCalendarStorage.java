package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyEdulogCalendar;
import seedu.address.model.calendar.EdulogCalendar;

/**
 * Represents a storage for {@link EdulogCalendar}.
 */
public interface EdulogCalendarStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getEdulogCalendarFilePath();

    /**
     * Returns EdulogCalendar data as a {@link ReadOnlyEdulogCalendar}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyEdulogCalendar> readEdulogCalendar() throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyEdulogCalendar} to the storage.
     *
     * @param edulogCalendar cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEdulogCalendar(ReadOnlyEdulogCalendar edulogCalendar) throws IOException;

}
