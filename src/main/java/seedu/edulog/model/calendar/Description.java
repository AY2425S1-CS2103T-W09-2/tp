package seedu.edulog.model.calendar;

/**
 * Denotes the description of a Lesson, which is displayed in the Calendar.
 * The description must be used by the user to tag the Lesson in a uniquely identifiable manner - i.e. the description
 * cannot have exactly matched characters, regardless of case.
 */
public class Description {
    /**
     * Denotes the maximum number of characters that a Lesson can have.
     */
    public static final Integer MAX_CHARACTER_LIMIT = 100;

    /**
     * Constrains description to be non-empty to prevent difficulties with identifying the Description later on.
     */
    public static final String DESCRIPTION_EMPTY =
        "Lesson description cannot be empty.";

    /**
     * A cap is placed on the description to prevent the Lesson description from excessively clogging the UI.
     */
    public static final String DESCRIPTION_TOO_LONG =
        "Lesson description should be at most 100 characters long (whitespace-inclusive).";

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
}
