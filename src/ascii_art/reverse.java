package ascii_art;

import image_char_matching.SubImgCharMatcher;

/**
 * Handles reverse feature by changing the charMatcher reverse state
 * @author Diana Basil and and Meital Lubarski
 */
public class reverse implements feature {
    private final SubImgCharMatcher charMatcher;

    /** constructs a reverse feature
     * @param subImgCharMatcher The active character set manager.
     */
    public reverse(SubImgCharMatcher subImgCharMatcher) {
        this.charMatcher = subImgCharMatcher;
    }

    /** executes the reverse feature by calling charMatcher func
     * @param args user args
     */
    @Override
    public void execute(String args) throws AsciiExceptions {
        charMatcher.changeReverse();
    }
}