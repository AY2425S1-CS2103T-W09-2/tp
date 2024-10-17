package seedu.edulog.model;

import java.nio.file.Path;

import seedu.edulog.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getEduLogFilePath();

}
