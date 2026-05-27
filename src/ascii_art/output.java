package ascii_art;
import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;


/**
 * Handles changing the output method of the ASCII art result.
 *
 * @author Diana Basil and Meital Lubarski
 */
public class output implements feature{
    private static final String CONSOLE = "console";
    private static final String HTML = "html";
    private static final String SPACE_REGEX = "\\s+";
    private static final String HTML_FILE_NAME = "out.html";
    private static final String HTML_FONT = "Courier New";

    private AsciiOutput currentOutput;

    /**
     * Constructs an output feature with console output as the default method.
     */
    public output(){
        this.currentOutput = new ConsoleAsciiOutput();
    }

    /**
     * Changes the active output method.
     *
     * @param args user args after the output command.
     * @throws AsciiExceptions if the output format is invalid.
     */
    @Override
    public void execute(String args) throws AsciiExceptions {
        if(args == null || args.trim().isEmpty()){
            throw AsciiExceptions.exOutputFormat();
        }
        String outputType = args.trim().split(SPACE_REGEX)[0];
        if(outputType.equals(CONSOLE)){
            currentOutput = new ConsoleAsciiOutput();
        }
        else if(outputType.equals(HTML)){
            currentOutput = new HtmlAsciiOutput(HTML_FILE_NAME, HTML_FONT);
        }
        else{
            throw AsciiExceptions.exOutputFormat();
        }
    }

    /**
     * Gets the current output method.
     *
     * @return the active AsciiOutput object.
     */
    public AsciiOutput getCurrentOutput(){
        return currentOutput;
    }
}
