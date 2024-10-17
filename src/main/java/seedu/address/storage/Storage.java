package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEdulogCalendar;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, EdulogCalendarStorage, UserPrefsStorage {
    //========================== UserPrefs =======================================================================

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    //========================== AddressBook =======================================================================

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    //========================== EdulogCalendar =======================================================================

    @Override
    Path getEdulogCalendarFilePath();

    @Override
    Optional<ReadOnlyEdulogCalendar> readEdulogCalendar() throws DataLoadingException;

    @Override
    void saveEdulogCalendar(ReadOnlyEdulogCalendar edulogCalendar) throws IOException;
}
