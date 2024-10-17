package seedu.edulog.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.edulog.testutil.Assert.assertThrows;
import static seedu.edulog.testutil.TypicalStudents.ALICE;
import static seedu.edulog.testutil.TypicalStudents.HOON;
import static seedu.edulog.testutil.TypicalStudents.IDA;
import static seedu.edulog.testutil.TypicalStudents.getTypicalEduLog;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.edulog.commons.exceptions.DataLoadingException;
import seedu.edulog.model.EduLog;
import seedu.edulog.model.ReadOnlyEduLog;

public class JsonEduLogStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonEduLogStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readEduLog_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readEduLog(null));
    }

    private java.util.Optional<ReadOnlyEduLog> readEduLog(String filePath) throws Exception {
        return new JsonEduLogStorage(Paths.get(filePath)).readEduLog(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readEduLog("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readEduLog("notJsonFormatEduLog.json"));
    }

    @Test
    public void readEduLog_invalidStudentEduLog_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readEduLog("invalidStudentEduLog.json"));
    }

    @Test
    public void readEduLog_invalidAndValidStudentEduLog_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readEduLog("invalidAndValidStudentEduLog.json"));
    }

    @Test
    public void readAndSaveEduLog_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempEduLog.json");
        EduLog original = getTypicalEduLog();
        JsonEduLogStorage jsonEduLogStorage = new JsonEduLogStorage(filePath);

        // Save in new file and read back
        jsonEduLogStorage.saveEduLog(original, filePath);
        ReadOnlyEduLog readBack = jsonEduLogStorage.readEduLog(filePath).get();
        assertEquals(original, new EduLog(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addStudent(HOON);
        original.removeStudent(ALICE);
        jsonEduLogStorage.saveEduLog(original, filePath);
        readBack = jsonEduLogStorage.readEduLog(filePath).get();
        assertEquals(original, new EduLog(readBack));

        // Save and read without specifying file path
        original.addStudent(IDA);
        jsonEduLogStorage.saveEduLog(original); // file path not specified
        readBack = jsonEduLogStorage.readEduLog().get(); // file path not specified
        assertEquals(original, new EduLog(readBack));

    }

    @Test
    public void saveEduLog_nullEduLog_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveEduLog(null, "SomeFile.json"));
    }

    /**
     * Saves {@code eduLog} at the specified {@code filePath}.
     */
    private void saveEduLog(ReadOnlyEduLog eduLog, String filePath) {
        try {
            new JsonEduLogStorage(Paths.get(filePath))
                    .saveEduLog(eduLog, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveEduLog_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveEduLog(new EduLog(), null));
    }
}
