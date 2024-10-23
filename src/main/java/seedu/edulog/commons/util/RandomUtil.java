package seedu.edulog.commons.util;

import java.util.Random;

public class RandomUtil {
    private static final Random random = new Random();

    public static int getRandomNumber(int max) {
        return random.nextInt(max);
    }
}
