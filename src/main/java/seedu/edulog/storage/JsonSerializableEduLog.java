package seedu.edulog.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.edulog.commons.exceptions.IllegalValueException;
import seedu.edulog.model.EduLog;
import seedu.edulog.model.ReadOnlyEduLog;
import seedu.edulog.model.student.Student;

/**
 * An Immutable EduLog that is serializable to JSON format.
 */
@JsonRootName(value = "edulog")
class JsonSerializableEduLog {

    public static final String MESSAGE_DUPLICATE_STUDENT = "Students list contains duplicate student(s).";

    private final List<JsonAdaptedStudent> students = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableEduLog} with the given students.
     */
    @JsonCreator
    public JsonSerializableEduLog(@JsonProperty("students") List<JsonAdaptedStudent> students) {
        this.students.addAll(students);
    }

    /**
     * Converts a given {@code ReadOnlyEduLog} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableEduLog}.
     */
    public JsonSerializableEduLog(ReadOnlyEduLog source) {
        students.addAll(source.getStudentList().stream().map(JsonAdaptedStudent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this edulog book into the model's {@code EduLog} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EduLog toModelType() throws IllegalValueException {
        EduLog eduLog = new EduLog();
        for (JsonAdaptedStudent jsonAdaptedStudent : students) {
            Student student = jsonAdaptedStudent.toModelType();
            if (eduLog.hasStudent(student)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STUDENT);
            }
            eduLog.addStudent(student);
        }
        return eduLog;
    }

}
