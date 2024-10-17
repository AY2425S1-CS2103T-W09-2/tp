package seedu.edulog.testutil;

import seedu.edulog.model.calendar.EdulogCalendar;
import seedu.edulog.model.calendar.Lesson;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withStudent("John", "Doe").build();}
 */
public class EdulogCalendarBuilder {

    private EdulogCalendar edulogCalendar;

    public EdulogCalendarBuilder() {
        edulogCalendar = new EdulogCalendar();
    }

    public EdulogCalendarBuilder(EdulogCalendar edulogCalendar) {
        this.edulogCalendar = edulogCalendar;
    }

    /**
     * Adds a new {@code Student} to the {@code AddressBook} that we are building.
     */
    public EdulogCalendarBuilder withStudent(Lesson lesson) {
        edulogCalendar.addLesson(lesson);
        return this;
    }

    public EdulogCalendar build() {
        return edulogCalendar;
    }
}
