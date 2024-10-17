package seedu.edulog.testutil;

import seedu.edulog.model.EduLog;
import seedu.edulog.model.student.Student;

/**
 * A utility class to help with building EduLog objects.
 * Example usage: <br>
 *     {@code EduLog ab = new EduLogBuilder().withStudent("John", "Doe").build();}
 */
public class EduLogBuilder {

    private EduLog eduLog;

    public EduLogBuilder() {
        eduLog = new EduLog();
    }

    public EduLogBuilder(EduLog eduLog) {
        this.eduLog = eduLog;
    }

    /**
     * Adds a new {@code Student} to the {@code EduLog} that we are building.
     */
    public EduLogBuilder withStudent(Student student) {
        eduLog.addStudent(student);
        return this;
    }

    public EduLog build() {
        return eduLog;
    }
}
