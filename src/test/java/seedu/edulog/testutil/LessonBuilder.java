package seedu.edulog.testutil;

import java.time.DayOfWeek;
import java.time.LocalTime;

import seedu.edulog.model.calendar.Lesson;

/**
 * A utility class to help with building Student objects.
 */
public class LessonBuilder {

    public static final String DEFAULT_DESCRIPTION = "Sec 4 Math";
    public static final DayOfWeek DEFAULT_START_DAY = DayOfWeek.MONDAY;
    public static final LocalTime DEFAULT_START_TIME = LocalTime.of(9, 30);
    public static final LocalTime DEFAULT_END_TIME = LocalTime.of(11, 30);
    private String description; //TODO: change to type Description
    private DayOfWeek startDay; //TODO: change to type StartDay
    private LocalTime startTime; //TODO: change to type StartTime
    private LocalTime endTime; //TODO: change to type endTime

    /**
     * Creates a {@code StudentBuilder} with the default details.
     * once the todos above are done we can rewrite this as
     *         description = new Description(DEFAULT_DESCRIPTION);
     *         startDay = new StartDay(DEFAULT_START_DAY);
     *         startTime = new StartTime(DEFAULT_START_TIME);
     *         endTime = new EndTime(DEFAULT_END_TIME);
     */
    public LessonBuilder() {
        description = DEFAULT_DESCRIPTION;
        startDay = DEFAULT_START_DAY;
        startTime = DEFAULT_START_TIME;
        endTime = DEFAULT_END_TIME;
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public LessonBuilder(Lesson lessonToCopy) {
        description = lessonToCopy.getDescription();
        startDay = lessonToCopy.getStartDay();
        startTime = lessonToCopy.getStartTime();
        endTime = lessonToCopy.getEndTime();
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public LessonBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the {@code DayOfWeek} of the {@code Student} that we are building.
     */
    public LessonBuilder withDayOfWeek(DayOfWeek startDay) {
        this.startDay = startDay;
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Student} that we are building.
     */
    public LessonBuilder withStartTime(LocalTime startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Student} that we are building.
     */
    public LessonBuilder withEndTime(LocalTime endTime) {
        this.endTime = endTime;
        return this;
    }


    public Lesson build() {
        return new Lesson(description, startDay, startTime, endTime);
    }

}
