package seedu.edulog.model;

import javafx.collections.ObservableList;
import seedu.edulog.model.student.Student;

/**
 * Unmodifiable view of an edulog book
 */
public interface ReadOnlyEduLog {

    /**
     * Returns an unmodifiable view of the students list.
     * This list will not contain any duplicate students.
     */
    ObservableList<Student> getStudentList();

}
