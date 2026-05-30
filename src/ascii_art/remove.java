package ascii_art;

import image_char_matching.SubImgCharMatcher;

/**
 * handles removing chars from  active charset
 * supports removing a single character, 'all', 'space', and range
 *
 * @author Diana Basil and Meital Lubarski
 */
public class remove implements feature {
    private static final String CMD_ALL = "all";
    private static final String CMD_SPACE = "space";

    private static final int MIN_ASCII = 32;
    private static final int MAX_ASCII = 126;
    private static final int SINGLE_CHAR = 1;
    private static final int RANGE = 3;

    private final SubImgCharMatcher charMatcher;

    /**
     * constructs a remove feature instance.
     *
     * @param subImgCharMatcher the active character set manager.
     */
    public remove(SubImgCharMatcher subImgCharMatcher) {
        this.charMatcher = subImgCharMatcher;
    }

    /**
     * executes the feature logic
     *
     * @param args user args after the type of feature
     */
    @Override
    public void execute(String args) throws AsciiExceptions {
        // arg is empty or whitespace
        if (args == null || args.trim().isEmpty()) {
            throw AsciiExceptions.exRemove();
        }

        String[] tokens = args.trim().split("\\s+"); // split by space
        String target = tokens[0];

        boolean flag = false; // input is invalid until helper returns true
        if (target.length() == SINGLE_CHAR) {
            flag = helperSingleChar(target.charAt(0));
        } else if (target.equals(CMD_ALL)) {
            flag = helperAll();
        } else if (target.equals(CMD_SPACE)) {
            flag = helperSpace();
        } else if (target.length() == RANGE && target.charAt(1) == '-') {
            flag = helperRange(target.charAt(0), target.charAt(2));
        }

        if (!flag) {
            throw AsciiExceptions.exRemove(); // remove exception
        }
    }

    /**
     * helper for removing a single character if it is in bounds.
     *
     * @return true if removed successfully, false if out of bounds.
     */
    private boolean helperSingleChar(char c) {
        if (!isValid(c)) {
            return false;
        }
        charMatcher.removeChar(c);
        return true;
    }

    /**
     * helper for removing all ASCII chars in our bounds.
     *
     * @return true if all chars removed successfully, false otherwise.
     */
    private boolean helperAll() {
        return helperRange((char) MIN_ASCII, (char) MAX_ASCII);
    }

    /**
     * helper for removing a whitespace character.
     *
     * @return always true since space is valid.
     */
    private boolean helperSpace() {
        charMatcher.removeChar(' ');
        return true;
    }

    /**
     * helper for validating start and end, sorting the range,
     * and removing characters one after other
     *
     * @return true if the range is valid and removed, false otherwise.
     */
    private boolean helperRange(char start, char end) {
        if (!isValid(start) || !isValid(end)) {
            return false;
        }
        char[] ordered = helperOrderRange(start, end);
        char sureStart = ordered[0];
        char sureEnd = ordered[1];

        for (int i = sureStart; i <= sureEnd; i++) {
            charMatcher.removeChar((char) i);
        }
        return true;
    }

    /**
     * makes sure start is smaller than end in their ASCII values.
     *
     * @return char array where index 0 is start and index 1 is end.
     */
    private char[] helperOrderRange(char start, char end) {
        char low = (char) Math.min(start, end);
        char high = (char) Math.max(start, end);
        return new char[]{low, high};
    }

    /**
     * validation checking that characters are inside boundaries.
     *
     * @return true if inside the valid boundaries, false otherwise.
     */
    private boolean isValid(char c) {
        return c >= MIN_ASCII && c <= MAX_ASCII;
    }
}