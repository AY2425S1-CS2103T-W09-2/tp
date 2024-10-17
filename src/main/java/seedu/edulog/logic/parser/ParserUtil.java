package seedu.edulog.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.edulog.commons.core.index.Index;
import seedu.edulog.commons.util.StringUtil;
import seedu.edulog.logic.parser.exceptions.ParseException;
import seedu.edulog.model.calendar.Lesson;
import seedu.edulog.model.student.Address;
import seedu.edulog.model.student.Email;
import seedu.edulog.model.student.Name;
import seedu.edulog.model.student.Phone;
import seedu.edulog.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Regular Expression to check if the given string contains only numbers.
     * {@code ^} represents beginning of line.
     * {@code \d} represents numbers.
     * {@code +} matches one or more characters.
     * {@code $} represents end of line.
     */
    public static final String ONLY_NUMBER_REGEX = "^\\d+$";

    /**
     * Checks whether the given string represents a number. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @return {@code true} if the string represents a number, false otherwise.
     */
    public static boolean isNumeric(String number) {
        String trimmedNumber = number.trim();
        return trimmedNumber.matches(ONLY_NUMBER_REGEX);
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String edulog} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code edulog} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String description}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the description is not within 1 and 100 characters long.
     */
    public static String parseDescription(String description) throws ParseException {
        requireNonNull(description);

        String trimmed = description.trim();
        if (trimmed.isEmpty()) {
            throw new ParseException(Lesson.DESCRIPTION_EMPTY);
        } else if (trimmed.length() > 100) {
            throw new ParseException(Lesson.DESCRIPTION_TOO_LONG);
        }

        return trimmed;
    }

    /**
     * Parses a provided String into a {@code DayOfWeek}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if not spelt exactly as the full day of the week, like "Monday", "Wednesday", etc.
     */
    public static DayOfWeek parseDayOfWeek(String day) throws ParseException {
        requireNonNull(day);
        switch (day.toLowerCase()) {
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
            throw new ParseException(Lesson.INVALID_DAY_OF_WEEK);
        }
    }

    /**
     * Parses a String representing a 24-hour time format to a {@code LocalTime}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if time provided is not a 24-hour time format like "1200" or "2359" without space
     */
    public static LocalTime parseLocalTime(String time) throws ParseException {
        String trimmed = time.trim();

        // Does not fit the length of a 24-hour time
        if (trimmed.length() != 4) {
            throw new ParseException(Lesson.NOT_24H_FORMAT);
        }

        String hour = trimmed.substring(0, 2);
        String minute = trimmed.substring(2);

        // Hour is not between 00 and 23, or minute is not between 00 and 59
        if (hour.compareTo("23") > 0 || minute.compareTo("59") > 0) {
            throw new ParseException(Lesson.NOT_24H_FORMAT);
        }

        return LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minute), 0);
    }
}
