package seedu.edulog.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edulog.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.edulog.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.edulog.testutil.Assert.assertThrows;
import static seedu.edulog.testutil.TypicalStudents.ALICE;
import static seedu.edulog.testutil.TypicalStudents.getTypicalEduLog;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.edulog.model.student.Student;
import seedu.edulog.model.student.exceptions.DuplicateStudentException;
import seedu.edulog.testutil.StudentBuilder;

public class EduLogTest {

    private final EduLog eduLog = new EduLog();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), eduLog.getStudentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eduLog.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyEduLog_replacesData() {
        EduLog newData = getTypicalEduLog();
        eduLog.resetData(newData);
        assertEquals(newData, eduLog);
    }

    @Test
    public void resetData_withDuplicateStudents_throwsDuplicateStudentException() {
        // Two students with the same identity fields
        Student editedAlice = new StudentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Student> newStudents = Arrays.asList(ALICE, editedAlice);
        EduLogStub newData = new EduLogStub(newStudents);

        assertThrows(DuplicateStudentException.class, () -> eduLog.resetData(newData));
    }

    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eduLog.hasStudent(null));
    }

    @Test
    public void hasStudent_studentNotInEduLog_returnsFalse() {
        assertFalse(eduLog.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentInEduLog_returnsTrue() {
        eduLog.addStudent(ALICE);
        assertTrue(eduLog.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentWithSameIdentityFieldsInEduLog_returnsTrue() {
        eduLog.addStudent(ALICE);
        Student editedAlice = new StudentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(eduLog.hasStudent(editedAlice));
    }

    @Test
    public void getStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> eduLog.getStudentList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = EduLog.class.getCanonicalName() + "{students=" + eduLog.getStudentList() + "}";
        assertEquals(expected, eduLog.toString());
    }

    /**
     * A stub ReadOnlyEduLog whose students list can violate interface constraints.
     */
    private static class EduLogStub implements ReadOnlyEduLog {
        private final ObservableList<Student> students = FXCollections.observableArrayList();

        EduLogStub(Collection<Student> students) {
            this.students.setAll(students);
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }
    }

}
