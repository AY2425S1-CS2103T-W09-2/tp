package seedu.edulog.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.edulog.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.edulog.commons.exceptions.IllegalValueException;
import seedu.edulog.commons.util.JsonUtil;
import seedu.edulog.model.EduLog;
import seedu.edulog.testutil.TypicalStudents;

public class JsonSerializableEduLogTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableEduLogTest");
    private static final Path TYPICAL_STUDENTS_FILE = TEST_DATA_FOLDER.resolve("typicalStudentsEduLog.json");
    private static final Path INVALID_STUDENT_FILE = TEST_DATA_FOLDER.resolve("invalidStudentEduLog.json");
    private static final Path DUPLICATE_STUDENT_FILE = TEST_DATA_FOLDER.resolve("duplicateStudentEduLog.json");

    @Test
    public void toModelType_typicalStudentsFile_success() throws Exception {
        JsonSerializableEduLog dataFromFile = JsonUtil.readJsonFile(TYPICAL_STUDENTS_FILE,
                JsonSerializableEduLog.class).get();
        EduLog eduLogFromFile = dataFromFile.toModelType();
        EduLog typicalStudentsEduLog = TypicalStudents.getTypicalEduLog();
        assertEquals(eduLogFromFile, typicalStudentsEduLog);
    }

    @Test
    public void toModelType_invalidStudentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableEduLog dataFromFile = JsonUtil.readJsonFile(INVALID_STUDENT_FILE,
                JsonSerializableEduLog.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateStudents_throwsIllegalValueException() throws Exception {
        JsonSerializableEduLog dataFromFile = JsonUtil.readJsonFile(DUPLICATE_STUDENT_FILE,
                JsonSerializableEduLog.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableEduLog.MESSAGE_DUPLICATE_STUDENT,
                dataFromFile::toModelType);
    }

}
