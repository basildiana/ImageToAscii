package ascii_art;

/**
 * custom exception class handling  with validation, syntax, and runtime execution
 * errors encountered within the shell class
 *
 * @author Diana Basil and Meital Lubarski
 */
public class AsciiExceptions extends Exception {
    // message constants
    private static final String PREFIX = "Did not ";
    private static final String ADD = "add ";
    private static final String REMOVE = "remove ";
    private static final String EXECUTE = "execute";
    private static final String SUFFIX = "due to incorrect format.";
    private static final String RESOLUTION = "change resolution ";
    private static final String BOUNDARIES = "due to exceeding boundaries.";
    private static final String OUTPUT = "change output method ";
    private static final String CHARSET = ". Charset is too small.";
    private static final String INCORRECT_COMMAND = " due to incorrect command.";

    /**
     * Constructs a new AsciiExceptions instance
     *
     * @param s string
     */
    public AsciiExceptions(String s) {
        super(s);
    }

    /**
     * Factory method to create an exception for an invalid 'add' command
     *
     * @return An AsciiExceptions instance with error message
     */
    public static AsciiExceptions exAdd() {
        return new AsciiExceptions(PREFIX + ADD + SUFFIX);
    }

    /**
     * Factory method to create an exception for an invalid 'remove' command
     *
     * @return An AsciiExceptions instance with  error message
     */
    public static AsciiExceptions exRemove() {
        return new AsciiExceptions(PREFIX + REMOVE + SUFFIX);
    }

    /**
     * Factory method to create an exception for invalid 'res' parameter syntax
     *
     * @return An AsciiExceptions instance with error message
     */
    public static AsciiExceptions exResFormat() {
        return new AsciiExceptions(PREFIX + RESOLUTION + SUFFIX);
    }

    /**
     * Factory method to create an exception for invalid resolution
     * legal bounds of the image
     *
     * @return An AsciiExceptions instance with error message
     */
    public static AsciiExceptions exResBounds() {
        return new AsciiExceptions(PREFIX + RESOLUTION + BOUNDARIES);
    }

    /**
     * Factory method to create an exception for invalid 'output' parameter
     *
     * @return An AsciiExceptions instance with error message
     */
    public static AsciiExceptions exOutputFormat() {
        return new AsciiExceptions(PREFIX + OUTPUT + SUFFIX);
    }

    /**
     * Factory method for an exception for small char set (less than 2 chars)
     *
     * @return An AsciiExceptions instance with error message
     */
    public static AsciiExceptions exSmallCharset() {
        return new AsciiExceptions(PREFIX + EXECUTE + CHARSET);
    }

    /**
     * Factory method to create an exception for invalid command
     *
     * @return An AsciiExceptions instance with error message
     */
    public static AsciiExceptions exInvalidCommand() {
        return new AsciiExceptions(PREFIX + EXECUTE + INCORRECT_COMMAND);
    }

}