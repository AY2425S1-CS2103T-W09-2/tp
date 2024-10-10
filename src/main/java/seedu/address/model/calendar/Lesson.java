package seedu.address.model.calendar;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Lesson class, representing a weekly recurring time slot for a lesson.
 */
public class Lesson {
    public static final String NO_SAME_TIME =
            "Lessons cannot start and end at the same time.";

    public static final String INVALID_DAY_OF_WEEK =
            "Day of the week must be spelt as "
            + "'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', or 'Sunday'"
            + "only (non case-sensitive).";

    public static final String DESCRIPTION_EMPTY =
            "Lesson description cannot be empty.";

    public static final String DESCRIPTION_TOO_LONG =
            "Lesson description should be at most 100 characters long (whitespace-inclusive).";

    public static final String NOT_24H_FORMAT =
            "Times provided must be in 24-hour time format. Examples: 0000, 1027, 1830, 2215, 2359.";


    public static final DateTimeFormatter FORMAT_24H = DateTimeFormatter.ofPattern("HHmm");

    private String description;
    private DayOfWeek startDay;
    private LocalTime startTime;
    private LocalTime endTime;

    /**
     * Every field must be present and not null.
     */
    public Lesson(String description, DayOfWeek startDay, LocalTime startTime, LocalTime endTime) {
        requireAllNonNull(description, startDay, startTime, endTime);

        checkArgument(checkValidTimes(startTime, endTime), NO_SAME_TIME);

        this.description = description;
        this.startDay = startDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public DayOfWeek getStartDay() {
        return startDay;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Checks that lesson times are not ambiguous, i.e. not the same start and end time.
     */
    private static boolean checkValidTimes(LocalTime time1, LocalTime time2) {
        return !time1.equals(time2);
    }

    /**
     * Returns if the lesson spans 2 days, e.g. Monday 2000 to 0000, or Tuesday 2200 to 0100.
     */
    public boolean spansTwoDays() {
        return startTime.isBefore(endTime);
    }


    /**
     * Returns the duration of the lesson in minutes, ranging from 1 to 1439.
     */
    public int getDuration() {
        return (int) startTime.until(endTime, ChronoUnit.MINUTES);
    }

    /**
     * Returns true if lesson has the given description
     */
    public boolean isDescription(String description) {
        return this.description.equals(description);
    }

    /**
     * Returns true if both lessons have the same description
     */
    public boolean isSameLesson(Lesson otherLesson) {
        return otherLesson != null
                && otherLesson.description.equals(description);
    }

    /**
     * Returns true if both lessons have the same identity and data fields.
     * This defines a stronger notion of equality between two lessons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;
        return description.equals(otherLesson.description)
                && startDay.equals(otherLesson.startDay)
                && startTime.equals(otherLesson.startTime)
                && endTime.equals(otherLesson.endTime);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, startDay, startTime, endTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("description", description)
                .add("From: ", startDay + " " + startTime.format(FORMAT_24H))
                .add("To: ", startDay.plus(spansTwoDays() ? 1 : 0) + " " + endTime.format(FORMAT_24H))
                .toString();
    }
}
