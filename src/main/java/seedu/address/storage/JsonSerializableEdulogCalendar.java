package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyEdulogCalendar;
import seedu.address.model.calendar.EdulogCalendar;
import seedu.address.model.lesson.Lesson;

/**
 * An Immutable EdulogCalendar that is serializable to JSON format.
 */
@JsonRootName(value = "edulogcalendar")
class JsonSerializableEdulogCalendar {

    public static final String MESSAGE_DUPLICATE_LESSON = "Lessons list contains duplicate lesson(s).";

    private final List<JsonAdaptedLesson> lessons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableEdulogCalendar} with the given lessons.
     */
    @JsonCreator
    public JsonSerializableEdulogCalendar(@JsonProperty("lessons") List<JsonAdaptedLesson> lessons) {
        this.lessons.addAll(lessons);
    }

    /**
     * Converts a given {@code ReadOnlyEdulogCalendar} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableEdulogCalendar}.
     */
    public JsonSerializableEdulogCalendar(ReadOnlyEdulogCalendar source) {
        lessons.addAll(source.getLessonList().stream().map(JsonAdaptedLesson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this edulog calendar into the model's {@code EdulogCalendar} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EdulogCalendar toModelType() throws IllegalValueException {
        EdulogCalendar edulogCalendar = new EdulogCalendar();
        for (JsonAdaptedLesson jsonAdaptedLesson : lessons) {
            Lesson lesson = jsonAdaptedLesson.toModelType();
            if (edulogCalendar.hasLesson(lesson)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LESSON);
            }
            edulogCalendar.addLesson(lesson);
        }
        return edulogCalendar;
    }

}
