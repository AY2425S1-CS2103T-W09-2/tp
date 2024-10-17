package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.lesson.Lesson;

public class DeleteLessonCommandTest {

    @Test
    public void deleteValidLesson() throws CommandException {
        Lesson lesson = new Lesson("Math", DayOfWeek.MONDAY, LocalTime.of(12, 0), LocalTime.of(13, 0));
        Model model = new ModelManager();
        model.addLesson(lesson);

        DeleteLessonCommand command = new DeleteLessonCommand("Math");
        CommandResult result = command.execute(model);
        assertEquals(result.getFeedbackToUser(),
            String.format(DeleteLessonCommand.MESSAGE_DELETE_STUDENT_SUCCESS, lesson));
    }

    @Test
    public void deleteNonExistentLesson_throwsException() {
        Lesson lesson = new Lesson("Math", DayOfWeek.MONDAY, LocalTime.of(12, 0), LocalTime.of(13, 0));
        Model model = new ModelManager();
        model.addLesson(lesson);

        DeleteLessonCommand command = new DeleteLessonCommand("Physics");
        assertCommandFailure(command, model, DeleteLessonCommand.MESSAGE_NONEXISTENT_LESSON);
    }

    @Test
    public void testEqual() {
        DeleteLessonCommand command1 = new DeleteLessonCommand("Bio");
        DeleteLessonCommand command2 = new DeleteLessonCommand("Bio");
        assertTrue(command1.equals(command2));
    }

    @Test
    public void testNotEqual() {
        DeleteLessonCommand command1 = new DeleteLessonCommand("Bio");
        DeleteLessonCommand command2 = new DeleteLessonCommand("Chem");
        assertFalse(command1.equals(command2));
    }
}
