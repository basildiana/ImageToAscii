package ascii_art;

/**
 * interface for a single feature the user chose
 *
 * @author Diana Basil and Meital Lubarski
 *
 */
public interface feature {
    /**
     * executes the feature logic
     *
     * @param args user args after the type of feature
     */
    void execute(String args) throws AsciiExceptions; //checked exceptions
}
