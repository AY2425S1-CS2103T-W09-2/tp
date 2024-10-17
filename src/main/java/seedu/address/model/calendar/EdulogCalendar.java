package seedu.address.model.calendar;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.ReadOnlyEdulogCalendar;
import seedu.address.model.lesson.Lesson;

/**
 * Calendar class
 */
public class EdulogCalendar implements ReadOnlyEdulogCalendar {
    public static final int MAX_IDENTICAL_TIMING = 2;
    private List<Lesson> lessons;

    public EdulogCalendar() {
        lessons = new ArrayList<>();
    }


    /**
     * Creates an EdulogCalendar using Lessons in the {@code toBeCopied}
     */
    public EdulogCalendar(ReadOnlyEdulogCalendar toBeCopied) {
        this.lessons = toBeCopied.getLessonList();
    }

    /**
     * Modifies an existing EdulogCalendar using Lessons in the {@code toBeCopied}
     */
    public void setEdulogCalendar(ReadOnlyEdulogCalendar toBeCopied) {
        this.lessons = toBeCopied.getLessonList();
    }

    /**
     * Add a new lesson to Calendar
     */
    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    /**
     * Checks if a provided lesson is equal (based on description) to any other lesson in the calendar.
     */
    public boolean hasLesson(Lesson lesson) {
        return lessons.stream().anyMatch(l -> l.isSameLesson(lesson));
    }

    /**
     * Finds a lesson in the calendar based on its description
     */
    public Lesson findLesson(String description) {
        return lessons.stream()
            .filter(lesson -> lesson.isDescription(description))
            .findFirst()
            .orElse(null);
    }

    /**
     * Returns true if a lesson with a given timeslot can be added in the calendar without exceeding the count limit.
     */
    public boolean checkTimeslot(Lesson lesson) {
        return lessons.stream()
            .filter(l -> l.getStartDay().equals(lesson.getStartDay()))
            .filter(l -> l.getStartTime().isBefore(lesson.getEndTime())
                && lesson.getStartTime().isBefore(l.getEndTime()))
            .count() < MAX_IDENTICAL_TIMING;
    }

    /**
     * Remove the 1st index in lessons that returns true on Object.equals(lesson, lessons.get(i))
     */
    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }

    @Override
    public ObservableList<Lesson> getLessonList() {
        return FXCollections.observableArrayList(lessons);
    }
}
