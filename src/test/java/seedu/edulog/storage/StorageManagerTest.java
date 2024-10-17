package seedu.edulog.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.edulog.testutil.TypicalStudents.getTypicalEduLog;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.edulog.commons.core.GuiSettings;
import seedu.edulog.model.EduLog;
import seedu.edulog.model.ReadOnlyEduLog;
import seedu.edulog.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonEduLogStorage eduLogStorage = new JsonEduLogStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(eduLogStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void eduLogReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonEduLogStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonEduLogStorageTest} class.
         */
        EduLog original = getTypicalEduLog();
        storageManager.saveEduLog(original);
        ReadOnlyEduLog retrieved = storageManager.readEduLog().get();
        assertEquals(original, new EduLog(retrieved));
    }

    @Test
    public void getEduLogFilePath() {
        assertNotNull(storageManager.getEduLogFilePath());
    }

}
