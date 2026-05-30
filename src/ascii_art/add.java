package ascii_art;

import image_char_matching.SubImgCharMatcher;

/**
 * Handles adding chars to active charset.
 * Supports adding a single character, 'all', 'space' and range.
 *
 * @author Diana Basil and Meital Lubarski
 */
public class add implements feature {
    private static final String CMD_ALL = "all";
    private static final String CMD_SPACE = "space";

    private static final int MIN_ASCII = 32;
    private static final int MAX_ASCII = 126;
    private static final int SINGLE_CHAR = 1;
    private static final int RANGE = 3;

    private final SubImgCharMatcher charMatcher;

    public add(SubImgCharMatcher subImgCharMatcher) {
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
            throw AsciiExceptions.exAdd();
        }
        String[] tokens = args.trim().split("\\s+"); // split by space
        String target = tokens[0];

        boolean flag = false; // input is invalid until helper return true, fits 2.4.6
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
            throw AsciiExceptions.exAdd();
        }
    }

    /**
     * helper for 2.4.1: adds a single character if it in bounds
     *
     * @return true if added successfully, false if out of bounds.
     */
    private boolean helperSingleChar(char c) {
        if (!isValid(c)) {
            return false;
        }
        charMatcher.addChar(c);
        return true;
    }

    /**
     * helper for 2.4.2: adds all ASCII chars in our bounds
     *
     * @return true if all chars added successfully, false otherwise.
     */
    private boolean helperAll() {
        return helperRange((char) MIN_ASCII, (char) MAX_ASCII);
    }

    /**
     * helper for 2.4.3: adds a whitespace
     *
     * @return always true since space is valid.
     */
    private boolean helperSpace() {
        charMatcher.addChar(' ');
        return true;
    }

    /**
     * helper for 2.4.4: Validates start and end,
     * applies the reverse sorting helper,
     * and populates the range sequentially.
     *
     * @return true if the range is valid and added, false otherwise
     */
    private boolean helperRange(char start, char end) {
        if (!isValid(start) || !isValid(end)) {
            return false;
        }
        char[] ordered = helperOrderRange(start, end);
        char sureStart = ordered[0];
        char sureEnd = ordered[1];

        for (int i = sureStart; i <= sureEnd; i++) {
            charMatcher.addChar((char) i);
        }
        return true;
    }

    /**
     * make sure start is smaller than end in their ascii values
     *
     * @return char array, index 0 is start and index 1 is end.
     */
    private char[] helperOrderRange(char start, char end) {
        char low = (char) Math.min(start, end);
        char high = (char) Math.max(start, end);
        return new char[]{low, high};
    }

    /**
     * validation checking that characters are inside boundaries
     *
     * @return true if inside the valid, false otherwise
     */
    private boolean isValid(char c) {
        return c >= MIN_ASCII && c <= MAX_ASCII;
    }
}

