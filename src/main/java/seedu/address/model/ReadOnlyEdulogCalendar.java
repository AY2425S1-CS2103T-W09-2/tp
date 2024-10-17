package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.lesson.Lesson;

/**
 * Unmodifiable view of a calendar
 */
public interface ReadOnlyEdulogCalendar {

    /**
     * Returns an unmodifiable view of the lessons list in a calendar.
     * This list will not contain any lessons with the same name.
     */
    ObservableList<Lesson> getLessonList();

}
