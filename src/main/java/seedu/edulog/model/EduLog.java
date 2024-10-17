package seedu.edulog.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.edulog.commons.util.ToStringBuilder;
import seedu.edulog.model.student.Student;
import seedu.edulog.model.student.UniqueStudentList;

/**
 * Wraps all data at the edulog-book level
 * Duplicates are not allowed (by .isSameStudent comparison)
 */
public class EduLog implements ReadOnlyEduLog {

    private final UniqueStudentList students;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        students = new UniqueStudentList();
    }

    public EduLog() {}

    /**
     * Creates an EduLog using the Students in the {@code toBeCopied}
     */
    public EduLog(ReadOnlyEduLog toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the student list with {@code students}.
     * {@code students} must not contain duplicate students.
     */
    public void setStudents(List<Student> students) {
        this.students.setStudents(students);
    }

    /**
     * Resets the existing data of this {@code EduLog} with {@code newData}.
     */
    public void resetData(ReadOnlyEduLog newData) {
        requireNonNull(newData);

        setStudents(newData.getStudentList());
    }

    //// student-level operations

    /**
     * Returns true if a student with the same identity as {@code student} exists in the edulog book.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Adds a student to the edulog book.
     * The student must not already exist in the edulog book.
     */
    public void addStudent(Student p) {
        students.add(p);
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the edulog book.
     * The student identity of {@code editedStudent} must not be the same as another existing student in EduLog.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        students.setStudent(target, editedStudent);
    }

    /**
     * Removes {@code key} from this {@code EduLog}.
     * {@code key} must exist in the edulog book.
     */
    public void removeStudent(Student key) {
        students.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("students", students)
                .toString();
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EduLog)) {
            return false;
        }

        EduLog otherEduLog = (EduLog) other;
        return students.equals(otherEduLog.students);
    }

    @Override
    public int hashCode() {
        return students.hashCode();
    }
}
