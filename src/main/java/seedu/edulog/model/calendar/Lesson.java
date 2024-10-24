package seedu.edulog.model.calendar;

import static seedu.edulog.commons.util.AppUtil.checkArgument;
import static seedu.edulog.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.edulog.commons.util.ToStringBuilder;

/**
 * Lesson class, representing a weekly recurring time slot for a lesson.
 */
public class Lesson {

    // Denotes the valid days of the week, spelt in full.
    public static final ArrayList<String> DAYS_OF_THE_WEEK = new ArrayList<String>(
        List.of("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"));

    public static final String NO_SAME_TIME =
            "Lessons cannot start and end at the same time.";

    public static final String INVALID_DAY_OF_WEEK =
            "Day of the week must be spelt as "
            + "'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', or 'Sunday'"
            + "only (non case-sensitive).";

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

    /**
     * Getter method that attains the DayOfWeek representation of the start time for time-based methods. <br> <br>
     * NOTE: If you want to attain a printable representation of time, please use {@link #getFormattedStartTime()}
     * instead, which ensures that times can be presented and stored in an acceptable 24-hour format.
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Getter method that attains the DayOfWeek representation of the end time for time-based methods. <br> <br>
     * NOTE: If you want to attain a printable representation of time, please use {@link #getFormattedStartTime()}
     * instead, which ensures that times can be presented and stored in an acceptable 24-hour format.
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Getter method that attains the start time in a format acceptable as both user input and storage input.
     * Example: "1100", "2359".
     */
    public String getFormattedStartTime() {
        return startTime.format(FORMAT_24H);
    }

    /**
     * Getter method that attains the end time in a format acceptable as both user input and storage input.
     * Example: "1100", "2359".
     */
    public String getFormattedEndTime() {
        return endTime.format(FORMAT_24H);
    }

    // Validator methods. TODO: OOP-ize =========================================

    /**
     * Checks that lesson times are not ambiguous, i.e. not the same start and end time.
     */
    public static boolean checkValidTimes(LocalTime time1, LocalTime time2) {
        return !time1.equals(time2);
    }

    /**
     * Checks that description is not empty, i.e. not "".
     */
    public static boolean checkEmptyDescription(String description) {
        return description.isEmpty();
    }

    /**
     * Checks if the description fits the character limit.
     */
    public static boolean checkTooLongDescription(String description) {
        return description.length() > MAX_CHARACTER_LIMIT;
    }

    /**
     * Checks if the description meets all description restrictions.
     */
    public static boolean checkValidDescription(String description) {
        return !checkEmptyDescription(description) && !checkTooLongDescription(description);
    }

    /**
     * Checks if the day of week matches any of the 7 days of the week, spelt in full, example "Wednesday".
     * Non-case sensitive.
     */
    public static boolean checkValidDayOfWeek(String dayOfWeek) {
        return DAYS_OF_THE_WEEK.contains(dayOfWeek.toLowerCase());
    }

    /**
     * Checks if a provided String conforms to a 24-hour time format specifier.
     */
    public static boolean checkValidLocalTime(String time) {
        if (time.length() != 4) {
            return false;
        }

        String hour = time.substring(0, 2);
        String minute = time.substring(2);

        // Ensure that hour is between 00 and 23, and minute is between 00 and 59
        return hour.compareTo("23") <= 0 && minute.compareTo("59") <= 0;
    }

    /**
     * Returns if the lesson spans 2 days, e.g. Monday 2000 to 0000, or Tuesday 2200 to 0100.
     */
    public boolean spansTwoDays() {
        return startTime.isBefore(endTime);
    }

    /**
     * Returns true if lesson has the given description
     */
    public boolean isDescription(String description) {
        return this.description.equals(description);
    }

    /**
     * Returns true if both lessons have the same description. A lesson's primary identity is its description and is
     * exclusively checked with each other. Two lessons with the same description will be marked as the same lesson
     * even if their secondary characteristics (like day of the week) vary.
     */
    public boolean isSameLesson(Lesson otherLesson) {
        return otherLesson != null
                && otherLesson.description.equals(description);
    }

    // Processor methods. TODO: OOP-ize =========================================

    /**
     * Generates a DayOfWeek based on a String representation.
     * Callers are advised to use {@link #checkValidDayOfWeek(String)} first and handle abnormal use cases themselves.
     * Otherwise, an error message will be generated that may be visible to the user.
     */
    public static DayOfWeek processDayOfWeek(String dayOfWeek) {
        checkArgument(checkValidDayOfWeek(dayOfWeek), Lesson.INVALID_DAY_OF_WEEK);
        switch (dayOfWeek.toLowerCase()) {
        case "monday":
            return DayOfWeek.MONDAY;
        case "tuesday":
            return DayOfWeek.TUESDAY;
        case "wednesday":
            return DayOfWeek.WEDNESDAY;
        case "thursday":
            return DayOfWeek.THURSDAY;
        case "friday":
            return DayOfWeek.FRIDAY;
        case "saturday":
            return DayOfWeek.SATURDAY;
        case "sunday":
            return DayOfWeek.SUNDAY;
        default:
            // because this is prior to a validity check, this use of an unchecked runtime exception is
            // warranted here as it denotes a gross failure of internal data processing.
            throw new RuntimeException("Something went horribly wrong when parsing a Lesson.");
        }
    }

    /**
     * Generates a LocalTime based on a String representation.
     * Callers are advised to use {@link #checkValidLocalTime(String)} first and handle abnormal use cases themselves.
     * Otherwise, an error message will be generated that may be visible to the user.
     */
    public static LocalTime processLocalTime(String time) {
        checkArgument(checkValidLocalTime(time), Lesson.NOT_24H_FORMAT);

        String hour = time.substring(0, 2);
        String minute = time.substring(2);
        return LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minute));
    }

    // Identity methods ========================================================================================

    /**
     * Returns true if both lessons have the same identity and data fields.
     * This defines a stronger notion of equality between two lessons, where all fields of the Lesson must match.
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
                .add("From", startDay + " " + getFormattedStartTime())
                .add("To", startDay.plus(spansTwoDays() ? 1 : 0) + " " + getFormattedEndTime())
                .toString();
    }
}
