package seedu.address.model.calendar;

import java.util.ArrayList;
import java.util.List;

/**
 * Calendar class
 */
public class EdulogCalendar {
    private List<Lesson> lessons;

    public EdulogCalendar() {
        lessons = new ArrayList<>();
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
     * Find a lesson in the calendar based on its description
     */
    public Lesson findLesson(String description) {
        return lessons.stream()
                      .filter(lesson -> lesson.isDescription(description))
                      .findFirst()
                      .orElse(null);
    }

    /**
     * Remove the 1st index in lessons that returns true on Object.equals(lesson, lessons.get(i))
     */
    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }
}
