package seedu.edulog.testutil;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.edulog.model.calendar.EdulogCalendar;
import seedu.edulog.model.calendar.Lesson;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalLessons {

    public static final Lesson SEC_4_MATH = new LessonBuilder().withDescription("Sec 4 Math")
            .withDayOfWeek(DayOfWeek.FRIDAY)
            .withStartTime(LocalTime.of(19, 30))
            .withEndTime(LocalTime.of(20, 30)).build();

    public static final Lesson SEC_3_MATH = new LessonBuilder().withDescription("Sec 3 Math")
            .withDayOfWeek(DayOfWeek.TUESDAY)
            .withStartTime(LocalTime.of(0, 30))
            .withEndTime(LocalTime.of(3, 30)).build();

    public static final Lesson SEC_2_MATH = new LessonBuilder().withDescription("Sec 2 Math")
            .withDayOfWeek(DayOfWeek.WEDNESDAY)
            .withStartTime(LocalTime.of(19, 30))
            .withEndTime(LocalTime.of(20, 30)).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalLessons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical lessons.
     */
    public static EdulogCalendar getTypicalEdulogCalendar() {
        EdulogCalendar ec = new EdulogCalendar();
        for (Lesson lesson : getTypicalLessons()) {
            ec.addLesson(lesson);
        }
        return ec;
    }

    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(SEC_4_MATH, SEC_3_MATH, SEC_2_MATH));
    }
}
