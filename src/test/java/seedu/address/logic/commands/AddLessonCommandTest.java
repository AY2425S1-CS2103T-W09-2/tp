package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.calendar.EdulogCalendar;
import seedu.address.model.lesson.Lesson;

public class AddLessonCommandTest {

    private Model model;
    private Lesson validLesson;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        validLesson = new Lesson("Math", DayOfWeek.MONDAY, LocalTime.of(12, 0), LocalTime.of(13, 0));
    }

    @Test
    public void execute_uniqueValidLesson_success() throws CommandException {
        AddLessonCommand command = new AddLessonCommand(validLesson);
        CommandResult result = command.execute(model);
        assertEquals(result.getFeedbackToUser(),
            String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson));
        assertTrue(model.hasLesson(validLesson));
    }

    @Test
    public void execute_duplicateValidLesson_throwsCommandException() throws CommandException {
        model.addLesson(validLesson);

        // note that the lesson only has identical descriptions as the validLesson
        Lesson duplicateLesson = new Lesson("Math", DayOfWeek.TUESDAY, LocalTime.of(13, 0), LocalTime.of(14, 0));
        AddLessonCommand command = new AddLessonCommand(duplicateLesson);

        assertCommandFailure(command, model, AddLessonCommand.MESSAGE_DUPLICATE_LESSON);
    }

    @Test
    public void execute_overloadIdenticalTimingLesson_throwsCommandException() throws CommandException {
        DayOfWeek day = DayOfWeek.MONDAY;

        for (int i = 0; i < EdulogCalendar.MAX_IDENTICAL_TIMING; i++) {
            LocalTime startTime = LocalTime.of(12 - i, 0);
            LocalTime endTime = LocalTime.of(13 + i, 0);

            Lesson lesson = new Lesson("Math" + i, day, startTime, endTime);
            AddLessonCommand command = new AddLessonCommand(lesson);
            CommandResult result = command.execute(model);
            assertEquals(result.getFeedbackToUser(),
                String.format(AddLessonCommand.MESSAGE_SUCCESS, lesson));
        }

        LocalTime endTime = LocalTime.of(13 + EdulogCalendar.MAX_IDENTICAL_TIMING, 0);
        LocalTime startTime = LocalTime.of(12 - EdulogCalendar.MAX_IDENTICAL_TIMING, 0);
        Lesson lesson = new Lesson("Math" + EdulogCalendar.MAX_IDENTICAL_TIMING, day, startTime, endTime);
        AddLessonCommand command = new AddLessonCommand(lesson);
        assertCommandFailure(command, model, AddLessonCommand.OVERLOAD_IDENTICAL_TIMING);
    }
}
