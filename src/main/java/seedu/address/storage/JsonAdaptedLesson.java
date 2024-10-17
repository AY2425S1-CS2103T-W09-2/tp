package seedu.address.storage;

import java.time.DayOfWeek;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lesson.Lesson;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
class JsonAdaptedLesson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Lesson's %s field is missing!";

    private String description;
    private String startDay;
    private String startTime;
    private String endTime;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given lesson details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("description") String description, @JsonProperty("startDay") String startDay,
                             @JsonProperty("startTime") String startTime, @JsonProperty("endTime") String endTime) {
        this.description = description;
        this.startDay = startDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts a given {@code Lesson} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        description = source.getDescription();
        startDay = source.getStartDay().toString();
        startTime = source.getStartTime().toString();
        endTime = source.getEndTime().toString();
    }

    /**
     * Converts this Jackson-friendly adapted lesson object into the model's {@code Lesson} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted lesson.
     */
    public Lesson toModelType() throws IllegalValueException {
        // Description

        if (description == null) {
            // TODO: Change all exceptions thrown below to "XXX.class.getSimpleName()" once fields are OOP-ized.
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "description"));
        }

        if (Lesson.checkEmptyDescription(description)) {
            throw new IllegalValueException(Lesson.DESCRIPTION_EMPTY);
        } else if (Lesson.checkTooLongDescription(description)) {
            throw new IllegalValueException(Lesson.DESCRIPTION_TOO_LONG);
        }

        String modelDescription = description;

        // Start day

        if (startDay == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, ""));
        }
        if (!Lesson.checkValidDayOfWeek(startDay)) {
            throw new IllegalValueException(Lesson.INVALID_DAY_OF_WEEK);
        }

        final DayOfWeek modelStartDay = Lesson.processDayOfWeek(startDay);

        // Start time
        if (startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "start time"));
        }
        if (!Lesson.checkValidLocalTime(startTime)) {
            throw new IllegalValueException(Lesson.NOT_24H_FORMAT);
        }

        final LocalTime modelStartTime = Lesson.processLocalTime(startTime);

        // End time
        if (endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "end time"));
        }
        if (!Lesson.checkValidLocalTime(endTime)) {
            throw new IllegalValueException(Lesson.NOT_24H_FORMAT);
        }

        final LocalTime modelEndTime = Lesson.processLocalTime(endTime);

        // Start time-end time interactions
        if (!Lesson.checkValidTimes(modelStartTime, modelEndTime)) {
            throw new IllegalValueException(Lesson.NO_SAME_TIME);
        }

        return new Lesson(modelDescription, modelStartDay, modelStartTime, modelEndTime);
    }

}
